package com.ticketing.domain.member.exception;

import com.ticketing.global.exception.BusinessException;
import com.ticketing.global.exception.ErrorCode;

public class DuplicatedEmailException extends BusinessException {

  public DuplicatedEmailException(ErrorCode errorCode) {
    super(errorCode);
  }
}
