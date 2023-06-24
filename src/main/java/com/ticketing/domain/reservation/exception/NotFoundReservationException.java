package com.ticketing.domain.reservation.exception;

import com.ticketing.global.exception.BusinessException;
import com.ticketing.global.exception.ErrorCode;

public class NotFoundReservationException extends BusinessException {

  public NotFoundReservationException(ErrorCode errorCode) {
    super(errorCode);
  }
}
