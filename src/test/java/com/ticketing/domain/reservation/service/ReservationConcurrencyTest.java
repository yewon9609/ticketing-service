package com.ticketing.domain.reservation.service;

import static com.ticketing.testFixture.TestFixture.createCustomer;
import static com.ticketing.testFixture.TestFixture.createPerformance;
import static com.ticketing.testFixture.TestFixture.createPerformanceManager;
import static com.ticketing.testFixture.TestFixture.createVenue;
import static com.ticketing.testFixture.TestFixture.createVenueManager;
import static org.assertj.core.api.Assertions.assertThat;

import com.ticketing.domain.member.admin.entity.Admin;
import com.ticketing.domain.member.admin.repository.AdminRepository;
import com.ticketing.domain.member.customer.entity.Customer;
import com.ticketing.domain.member.customer.repository.CustomerRepository;
import com.ticketing.domain.performance.entity.Performance;
import com.ticketing.domain.performance.repository.PerformanceRepository;
import com.ticketing.domain.reservation.dto.request.ReservationCreateReq;
import com.ticketing.domain.reservation.repository.ReservationRepository;
import com.ticketing.domain.venue.entity.Venue;
import com.ticketing.domain.venue.repository.VenueRepository;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ReservationConcurrencyTest {


  @Autowired
  private ReservationService reservationService;

  @Autowired
  private AdminRepository adminRepository;

  @Autowired
  private CustomerRepository customerRepository;

  @Autowired
  private PerformanceRepository performanceRepository;

  @Autowired
  private VenueRepository venueRepository;

  @Autowired
  private ReservationRepository reservationRepository;

  private Admin performanceManager;
  private Admin venueManager;
  private Customer customer;
  private Venue venue;
  private Performance performance;

  @BeforeEach
  void setUp() {
    performanceManager = adminRepository.save(createPerformanceManager());
    venueManager = adminRepository.save(createVenueManager());
    customer = customerRepository.save(createCustomer());
    venue = venueRepository.save(createVenue(venueManager));
    performance = performanceRepository.save(createPerformance(performanceManager, venue));
  }

  @Test
  @DisplayName("분산락을 이용한 동시성 테스트")
  void test_create() throws InterruptedException {

    int numberOfThreads = 10;
    int ticketCount = 1;

    ReservationCreateReq reservationCreateReq = new ReservationCreateReq(
        performance.getId(),
        ticketCount
    );
    ExecutorService executorService = Executors.newFixedThreadPool(numberOfThreads);
    CountDownLatch latch = new CountDownLatch(numberOfThreads);

    for (int i = 0; i < numberOfThreads; i++) {
      executorService.submit(() -> {
        try {
          reservationService.create(reservationCreateReq, customer.getId());
        } finally {
          latch.countDown();
        }
      });
    }

    latch.await();

    int totalReservationCount = reservationRepository.findAll().size();

    assertThat(totalReservationCount).isEqualTo(numberOfThreads);

  }
}
