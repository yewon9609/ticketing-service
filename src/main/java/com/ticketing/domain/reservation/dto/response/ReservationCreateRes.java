package com.ticketing.domain.reservation.dto.response;

import com.ticketing.domain.reservation.entity.Reservation;

public record ReservationCreateRes(
    Long id,
    String customerName,
    String performanceTitle,
    int ticketCount,
    int totalCost
) {

  public static ReservationCreateRes from(Reservation reservation) {
    return new ReservationCreateRes(
        reservation.getId(),
        reservation.getCustomer()
            .getMemberInfo()
            .getName(),
        reservation.getPerformance()
            .getTitle(),
        reservation.getTicketCount(),
        reservation.getTotalCost()
    );
  }

}
