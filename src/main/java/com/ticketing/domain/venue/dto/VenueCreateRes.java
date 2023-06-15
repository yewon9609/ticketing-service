package com.ticketing.domain.venue.dto;

import com.ticketing.domain.venue.entity.Venue;

public record VenueCreateRes(
    Long id
) {

  public static VenueCreateRes from(Venue venue) {
    return new VenueCreateRes(
        venue.getId()
    );
  }

}
