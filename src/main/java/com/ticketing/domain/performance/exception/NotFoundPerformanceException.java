package com.ticketing.domain.performance.exception;

import com.ticketing.global.exception.BusinessException;
import com.ticketing.global.exception.ErrorCode;

public class NotFoundPerformanceException extends BusinessException {

  public NotFoundPerformanceException(ErrorCode errorCode) {
    super(errorCode);
  }
}
