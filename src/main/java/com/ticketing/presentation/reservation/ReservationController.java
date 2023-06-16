package com.ticketing.presentation.reservation;

import static org.springframework.http.HttpStatus.CREATED;

import com.ticketing.domain.reservation.dto.ReservationCreateReq;
import com.ticketing.domain.reservation.dto.ReservationCreateRes;
import com.ticketing.domain.reservation.service.ReservationService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
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

  @PostMapping("/{customerId}")
  public ResponseEntity<ReservationCreateRes> create(
      @RequestBody @Valid ReservationCreateReq createReq,
      @PathVariable Long customerId
  ) {

    ReservationCreateRes createRes = reservationService.create(createReq, customerId);

    return ResponseEntity.status(CREATED)
        .body(createRes);
  }

}
