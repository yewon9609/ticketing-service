package com.ticketing.domain.reservation.dto.response;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public record ReservationSimpleRes(
    LocalDateTime reservedDate,
    String performanceThumbnail,
    String performanceName,
    int totalCost,
    int ticketCount,
    LocalDate startDate,
    LocalTime startTime
) {

}
