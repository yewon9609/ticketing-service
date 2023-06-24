package com.ticketing.domain.reservation.service;

import com.ticketing.domain.aop.annotation.DistributedLock;
import com.ticketing.domain.member.customer.entity.Customer;
import com.ticketing.domain.member.customer.service.CustomerService;
import com.ticketing.domain.performance.entity.Performance;
import com.ticketing.domain.performance.service.PerformanceService;
import com.ticketing.domain.reservation.dto.ReservationCreateReq;
import com.ticketing.domain.reservation.dto.ReservationCreateRes;
import com.ticketing.domain.reservation.entity.Reservation;
import com.ticketing.domain.reservation.repository.ReservationRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class ReservationService {

  private final ReservationRepository reservationRepository;
  private final PerformanceService performanceService;
  private final CustomerService customerService;

  public ReservationService(ReservationRepository reservationRepository,
      PerformanceService performanceService, CustomerService customerService) {
    this.reservationRepository = reservationRepository;
    this.performanceService = performanceService;
    this.customerService = customerService;
  }

  @DistributedLock(key = "#createReq.performanceId")
  public ReservationCreateRes create(ReservationCreateReq createReq, Long customerId) {
    Customer customer = customerService.getBy(customerId);
    Performance performance = checkAvailabilityOfReservations(createReq, customer);

    performance.getSeat()
        .subtractBy(createReq.ticketCount());
    Reservation reservation = reservationRepository.save(
        createReq.toEntity(performance, customer)
    );

    return ReservationCreateRes.from(reservation);
  }

  private Performance checkAvailabilityOfReservations(ReservationCreateReq createReq,
      Customer customer) {
    Performance performance = performanceService.getBy(createReq.performanceId());
    performance.checkPossibleViewingAge(customer.getMemberInfo().getAge());

    return performance;
  }


}
