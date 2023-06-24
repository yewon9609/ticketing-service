package com.ticketing.global.exception;

import org.springframework.http.HttpStatus;

public enum ErrorCode {

  NOT_FOUND_MEMBER(HttpStatus.NOT_FOUND, "해당 회원을 찾을 수 없습니다"),
  NOT_FOUND_VENUE(HttpStatus.NOT_FOUND, "해당 공연장을 찾을 수 없습니다"),
  NOT_FOUND_PERFORMANCE(HttpStatus.NOT_FOUND, "해당 공연을 찾을 수 없습니다"),

  NO_MORE_SEATS(HttpStatus.BAD_REQUEST, "현재 예매 가능한 좌석의 갯수를 초과하였습니다"),
  NOT_AVAILABLE_AGE(HttpStatus.BAD_REQUEST, "관람 불가능한 연령입니다"),

  NOT_FOUND_RESERVATION(HttpStatus.NOT_FOUND, "예약내역을 찾을 수 없습니다"),

  DUPLICATED_EMAIL(HttpStatus.CONFLICT, "중복된 이메일입니다"),
  JSON_NOT_PARSING(HttpStatus.BAD_REQUEST,"토큰을 파싱할 수 없습니다"),
  TOKEN_EXCEPTION(HttpStatus.UNAUTHORIZED, "유효하지 않은 토큰입니다."),
  AUTHENTICATION_FAILED(HttpStatus.UNAUTHORIZED, "권한이 없습니다"),
  ACCESS_FAILED(HttpStatus.FORBIDDEN, "권한이 맞지 않습니다");

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
