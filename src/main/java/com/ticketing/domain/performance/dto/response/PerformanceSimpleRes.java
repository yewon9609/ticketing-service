package com.ticketing.domain.performance.dto.response;

import java.time.LocalDate;
import java.time.LocalTime;

public record PerformanceSimpleRes(
    String title,
    String thumbnail,
    LocalDate startDate,
    LocalTime startTime,
    int remainingSeats,
    String venueName
) {

}
