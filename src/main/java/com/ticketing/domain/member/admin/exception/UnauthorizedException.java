package com.ticketing.domain.member.admin.exception;

import com.ticketing.global.exception.BusinessException;
import com.ticketing.global.exception.ErrorCode;

public class UnauthorizedException extends BusinessException {

  public UnauthorizedException(ErrorCode errorCode) {
    super(errorCode);
  }
}
