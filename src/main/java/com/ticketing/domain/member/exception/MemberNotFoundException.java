package com.ticketing.domain.member.exception;

import com.ticketing.global.exception.BusinessException;
import com.ticketing.global.exception.ErrorCode;

public class MemberNotFoundException extends BusinessException {

  public MemberNotFoundException() {
    super(ErrorCode.NOT_FOUND_MEMBER);
  }
}
