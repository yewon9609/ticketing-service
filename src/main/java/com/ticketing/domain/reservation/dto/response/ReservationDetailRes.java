package com.ticketing.domain.reservation.dto.response;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public record ReservationDetailRes(
    String customerName,
    LocalDateTime reservedDate,
    int ticketCount,
    int totalCost,
    String performanceTitle,
    LocalDate startDate,
    LocalTime startTime,
    String venueName,
    String venueAddress
) {

}
