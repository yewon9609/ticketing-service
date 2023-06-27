package com.ticketing.domain.performance.exception;

import com.ticketing.global.exception.BusinessException;
import com.ticketing.global.exception.ErrorCode;

public class PerformanceNotFoundException extends BusinessException {

  public PerformanceNotFoundException() {
    super(ErrorCode.NOT_FOUND_PERFORMANCE);
  }
}
