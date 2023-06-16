package com.ticketing.domain.venue.dto;

import com.ticketing.domain.venue.entity.Venue;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record VenueCreateReq(
    @NotBlank
    String name,

    @NotBlank
    String address,

    @NotBlank
    @Pattern(regexp ="^[0-9]+$")
    String phoneNumber,

    @NotNull
    @Min(0)
    int totalSeats
) {

  public Venue toEntity() {
    return new Venue(
        name, address, phoneNumber, totalSeats
    );
  }

}
