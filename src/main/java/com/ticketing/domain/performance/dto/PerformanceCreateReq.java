package com.ticketing.domain.performance.dto;

import com.ticketing.domain.member.admin.entity.Admin;
import com.ticketing.domain.performance.entity.Performance;
import com.ticketing.domain.performance.entity.Schedule;
import com.ticketing.domain.venue.entity.Venue;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record PerformanceCreateReq(
    @NotBlank
    String title,
    @NotBlank
    String thumbnail,
    @NotNull
    Schedule schedule,
    @Min(0)
    int viewingAge,
    @Min(0)
    int seatPrice,
    @NotNull
    @Positive
    Long venueId,
    @Min(0)
    int capacity
) {

  public Performance toEntity(Venue venue, Admin admin) {
    return new Performance(
        title, thumbnail, schedule, viewingAge, seatPrice, capacity, venue, admin
    );
  }
}
