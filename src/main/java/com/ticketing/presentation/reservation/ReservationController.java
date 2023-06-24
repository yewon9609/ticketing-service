package com.ticketing.presentation.reservation;

import static org.springframework.http.HttpStatus.CREATED;

import com.ticketing.domain.reservation.dto.request.ReservationCreateReq;
import com.ticketing.domain.reservation.dto.response.ReservationCreateRes;
import com.ticketing.domain.reservation.dto.response.ReservationDetailRes;
import com.ticketing.domain.reservation.dto.response.ReservationSimpleRes;
import com.ticketing.domain.reservation.service.ReservationService;
import com.ticketing.global.config.jwt.CustomUserDetails.CustomerInfo;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/reservations")
public class ReservationController {

  private static final int DEFAULT_SIZE = 5;

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

  @GetMapping("/{id}")
  public ResponseEntity<ReservationDetailRes> get(
      @PathVariable Long id,
      @AuthenticationPrincipal CustomerInfo customerInfo
  ) {
    ReservationDetailRes reservation = reservationService.getDetails(id,
        customerInfo.getId());

    return ResponseEntity.ok(reservation);
  }

  @GetMapping
  public ResponseEntity<Slice<ReservationSimpleRes>> getAll(
      @PageableDefault(size = DEFAULT_SIZE) Pageable pageable,
      @AuthenticationPrincipal CustomerInfo customerInfo
  ) {
    Slice<ReservationSimpleRes> reservations =
        reservationService.getList(customerInfo.getId(), pageable);
    return ResponseEntity.ok(reservations);
  }

}
