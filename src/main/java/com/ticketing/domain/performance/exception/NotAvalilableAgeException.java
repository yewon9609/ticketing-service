package com.ticketing.domain.performance.exception;

import com.ticketing.global.exception.BusinessException;
import com.ticketing.global.exception.ErrorCode;

public class NotAvalilableAgeException extends BusinessException {

  public NotAvalilableAgeException(ErrorCode errorCode) {
    super(errorCode);
  }

}
