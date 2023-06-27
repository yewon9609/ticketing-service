package com.ticketing.domain.performance.exception;

import com.ticketing.global.exception.BusinessException;
import com.ticketing.global.exception.ErrorCode;

public class NotAvailableAgeException extends BusinessException {

  public NotAvailableAgeException() {
    super(ErrorCode.NOT_AVAILABLE_AGE);
  }

}
