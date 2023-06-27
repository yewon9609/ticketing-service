package com.ticketing.domain.venue.exception;

import com.ticketing.global.exception.BusinessException;
import com.ticketing.global.exception.ErrorCode;

public class NotFoundVenueException extends BusinessException {

  public NotFoundVenueException() {
    super(ErrorCode.NOT_FOUND_VENUE);
  }
}
