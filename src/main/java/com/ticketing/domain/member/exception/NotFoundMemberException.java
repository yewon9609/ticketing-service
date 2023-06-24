package com.ticketing.domain.member.exception;

import com.ticketing.global.exception.BusinessException;
import com.ticketing.global.exception.ErrorCode;

public class NotFoundMemberException extends BusinessException {

  public NotFoundMemberException(ErrorCode errorCode) {
    super(errorCode);
  }
}
