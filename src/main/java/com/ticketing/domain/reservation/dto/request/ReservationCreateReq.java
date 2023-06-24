package com.ticketing.domain.reservation.dto.request;

import com.ticketing.domain.member.customer.entity.Customer;
import com.ticketing.domain.performance.entity.Performance;
import com.ticketing.domain.reservation.entity.Reservation;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record ReservationCreateReq(

    @NotNull
    Long performanceId,

    @Min(1)
    int ticketCount

) {

  public Reservation toEntity(Performance performance, Customer customer) {

    int totalCost = performance.getSeat().getPrice() * ticketCount;

    return new Reservation(
        performance, customer, ticketCount, totalCost
    );
  }

}
