package com.ticketing.global.exception;

import org.springframework.http.HttpStatus;

public enum ErrorCode {
  NOT_PERFORMANCE_MANAGER(HttpStatus.UNAUTHORIZED, "공연 관리자가 아닙니다");

  private final HttpStatus status;
  private final String message;

  ErrorCode(HttpStatus status, String message) {
    this.status = status;
    this.message = message;
  }

  public HttpStatus getStatus() {
    return status;
  }

  public String getMessage() {
    return message;
  }

}
