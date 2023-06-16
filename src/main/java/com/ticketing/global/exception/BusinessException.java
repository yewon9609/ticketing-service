package com.ticketing.global.exception;

public class BusinessException extends RuntimeException{

  private final ErrorCode errorCode;

  public BusinessException(ErrorCode errorCode) {
    this.errorCode = errorCode;
  }

  public BusinessException(String message, ErrorCode errorCode) {
    super(message);
    this.errorCode = errorCode;
  }

  public BusinessException(Throwable cause, ErrorCode errorCode) {
    super(cause);
    this.errorCode = errorCode;
  }

  public ErrorCode getErrorCode() {
    return errorCode;
  }
}
