package com.ticketing.domain.reservation.exception;

import com.ticketing.global.exception.BusinessException;
import com.ticketing.global.exception.ErrorCode;

public class ReservationNotFoundException extends BusinessException {

  public ReservationNotFoundException() {
    super(ErrorCode.NOT_FOUND_RESERVATION);
  }
}
