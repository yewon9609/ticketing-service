package com.ticketing.domain.reservation.service;

import static com.ticketing.testFixture.TestFixture.createCustomer;
import static com.ticketing.testFixture.TestFixture.createPerformance;
import static com.ticketing.testFixture.TestFixture.createPerformanceManager;
import static com.ticketing.testFixture.TestFixture.createReservation;
import static com.ticketing.testFixture.TestFixture.createVenue;
import static com.ticketing.testFixture.TestFixture.createVenueManager;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.ticketing.domain.member.admin.entity.Admin;
import com.ticketing.domain.member.customer.entity.Customer;
import com.ticketing.domain.member.customer.service.CustomerService;
import com.ticketing.domain.performance.entity.Performance;
import com.ticketing.domain.performance.service.PerformanceService;
import com.ticketing.domain.reservation.dto.request.ReservationCreateReq;
import com.ticketing.domain.reservation.dto.response.ReservationCreateRes;
import com.ticketing.domain.reservation.entity.Reservation;
import com.ticketing.domain.reservation.repository.ReservationRepository;
import com.ticketing.domain.venue.entity.Venue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
@DisplayName("Reservation Service 클래스")
class ReservationServiceTest {

  @Mock
  private ReservationRepository reservationRepository;
  @Mock
  private PerformanceService performanceService;
  @Mock
  private CustomerService customerService;
  @InjectMocks
  private ReservationService reservationService;

  private Customer customer;
  private Admin venueManager;
  private Admin performanceManager;
  private Venue venue;
  private Performance performance;

  @BeforeEach
  void setUp() {
    customer = createCustomer();
    venueManager = createVenueManager();
    performanceManager = createPerformanceManager();
    venue = createVenue(venueManager);
    performance = createPerformance(venue, performanceManager);
  }

  @DisplayName("고객은 예약을 생성할 수 있다")
  @Test
  void test_create() {

    int ticketCount = 1;
    int totalCost = ticketCount * performance.getSeat().getPrice();

    ReservationCreateReq reservationCreateReq = new ReservationCreateReq(
        performance.getId(),
        ticketCount
    );
    when(customerService.getById(any())).thenReturn(customer);
    when(performanceService.subtractSeats(performance.getId(), customer.getAge(), ticketCount)).thenReturn(performance);
    Reservation reservation = createReservation(
        performance,
        customer,
        ticketCount,
        totalCost
    );
    when(reservationRepository.save(any())).thenReturn(reservation);

    ReservationCreateRes expectedReservation = ReservationCreateRes.from(reservation);
    ReservationCreateRes reservationCreateRes = reservationService.create(reservationCreateReq,
        customer.getId());

    assertThat(reservationCreateRes).isEqualTo(expectedReservation);
  }


}