package com.ticketing.global.exception;

import org.springframework.http.HttpStatus;

public enum ErrorCode {

  NOT_PERFORMANCE_MANAGER(HttpStatus.UNAUTHORIZED, "공연 관리자가 아닙니다"),
  NOT_VENUE_MANAGER(HttpStatus.UNAUTHORIZED, "공연장 관리자가 아닙니다"),
  NO_MORE_SEATS(HttpStatus.BAD_REQUEST, "현재 예매 가능한 좌석의 갯수를 초과하였습니다"),
  NOT_AVAILABLE_AGE(HttpStatus.BAD_REQUEST, "관람 불가능한 연령입니다");

  private final HttpStatus code;

  private final String message;

  ErrorCode(HttpStatus status, String message) {
    this.code = status;
    this.message = message;
  }

  public HttpStatus getCode() {
    return code;
  }

  public String getMessage() {
    return message;
  }

}
