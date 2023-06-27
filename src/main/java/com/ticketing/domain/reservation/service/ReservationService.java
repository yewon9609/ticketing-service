package com.ticketing.domain.reservation.service;

import com.ticketing.domain.aop.annotation.DistributedLock;
import com.ticketing.domain.member.customer.entity.Customer;
import com.ticketing.domain.member.customer.service.CustomerService;
import com.ticketing.domain.performance.entity.Performance;
import com.ticketing.domain.performance.service.PerformanceService;
import com.ticketing.domain.reservation.dto.request.ReservationCreateReq;
import com.ticketing.domain.reservation.dto.response.ReservationCreateRes;
import com.ticketing.domain.reservation.dto.response.ReservationDetailRes;
import com.ticketing.domain.reservation.dto.response.ReservationSimpleRes;
import com.ticketing.domain.reservation.entity.Reservation;
import com.ticketing.domain.reservation.exception.ReservationNotFoundException;
import com.ticketing.domain.reservation.repository.ReservationRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
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
    Customer customer = customerService.getById(customerId);

    Performance performance = performanceService.subtractSeats(
        createReq.performanceId(),
        customer.getAge(),
        createReq.ticketCount()
    );

    Reservation reservation = reservationRepository.save(
        createReq.toEntity(performance, customer)
    );

    return ReservationCreateRes.from(reservation);
  }

  public ReservationDetailRes getDetails(Long id, Long customerId) {
    return reservationRepository.getDetail(id, customerId)
        .orElseThrow(ReservationNotFoundException::new);
  }

  public Slice<ReservationSimpleRes> getList(Long customerId, Pageable pageable) {
    return reservationRepository.getList(customerId, pageable);
  }

}
