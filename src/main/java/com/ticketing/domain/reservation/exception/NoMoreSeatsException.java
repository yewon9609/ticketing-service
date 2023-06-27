package com.ticketing.domain.reservation.exception;

import com.ticketing.global.exception.BusinessException;
import com.ticketing.global.exception.ErrorCode;

public class NoMoreSeatsException extends BusinessException {

  public NoMoreSeatsException() {
    super(ErrorCode.NO_MORE_SEATS);
  }

}
