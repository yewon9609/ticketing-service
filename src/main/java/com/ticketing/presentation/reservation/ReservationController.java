package com.ticketing.presentation.reservation;

import static org.springframework.http.HttpStatus.CREATED;

import com.ticketing.domain.reservation.dto.ReservationCreateReq;
import com.ticketing.domain.reservation.dto.ReservationCreateRes;
import com.ticketing.domain.reservation.service.ReservationService;
import com.ticketing.global.config.jwt.CustomUserDetails.CustomerInfo;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/reservations")
public class ReservationController {

  private final ReservationService reservationService;

  public ReservationController(ReservationService reservationService) {
    this.reservationService = reservationService;
  }

  @PostMapping
  public ResponseEntity<ReservationCreateRes> create(
      @RequestBody @Valid ReservationCreateReq createReq,
      @AuthenticationPrincipal CustomerInfo customerInfo
  ) {

    ReservationCreateRes createRes = reservationService.create(createReq, customerInfo.getId());

    return ResponseEntity.status(CREATED)
        .body(createRes);
  }

}
