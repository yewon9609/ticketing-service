package com.ticketing.domain.performance.dto.response;

import com.ticketing.domain.performance.entity.Performance;
import com.ticketing.domain.performance.entity.Schedule;

public record PerformanceDetailRes(
    Long id,

    String title,

    String thumbnail,

    Schedule schedule,

    int viewingAge,

    int seatPrice,

    String venueName,

    String venueAddress,

    String venuePhoneNumber
) {
  public static PerformanceDetailRes from(Performance performance) {
    return new PerformanceDetailRes(
      performance.getId(),
      performance.getTitle(),
      performance.getThumbnail(),
      performance.getSchedule(),
      performance.getViewingAge(),
      performance.getSeat()
          .getPrice(),
      performance.getVenue()
          .getName(),
      performance.getVenue()
          .getAddress(),
      performance.getVenue()
          .getPhoneNumber()
    );
  }
}
