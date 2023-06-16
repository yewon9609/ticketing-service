package com.ticketing.domain.venue.dto.request;

import com.ticketing.domain.member.admin.entity.Admin;
import com.ticketing.domain.venue.entity.Venue;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import org.hibernate.validator.constraints.Length;

public record VenueCreateReq(

    @NotBlank
    @Length(max = 30)
    String name,

    @NotBlank
    @Length(max = 255)
    String address,

    @NotBlank
    @Pattern(regexp = "^[0-9]+$")
    @Length(max = 15)
    String phoneNumber,

    @NotNull
    @Min(1)
    int totalSeats

) {

  public Venue toEntity(Admin venueManager) {
    return new Venue(
        name, address, phoneNumber, totalSeats, venueManager
    );
  }

}
