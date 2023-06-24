package com.ticketing.presentation.venue;

import com.ticketing.domain.venue.dto.request.VenueCreateReq;
import com.ticketing.domain.venue.dto.response.VenueCreateRes;
import com.ticketing.domain.venue.service.VenueService;
import com.ticketing.global.config.jwt.CustomUserDetails.AdminInfo;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/venues")
public class VenueController {

  private final VenueService venueService;

  public VenueController(VenueService venueService) {
    this.venueService = venueService;
  }

  @PostMapping
  public ResponseEntity<VenueCreateRes> create(
      @RequestBody @Valid VenueCreateReq venueCreateReq,
      @AuthenticationPrincipal AdminInfo adminInfo

  ) {
    VenueCreateRes venueCreateRes = venueService.creat(venueCreateReq, adminInfo.getId());
    return ResponseEntity.ok(venueCreateRes);
  }

}
