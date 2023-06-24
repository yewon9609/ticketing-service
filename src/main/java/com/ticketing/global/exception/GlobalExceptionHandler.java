package com.ticketing.global.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

  private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

  @ExceptionHandler(BusinessException.class)
  public ResponseEntity<ErrorResponse> handleBusinessException(BusinessException e) {
    log.info("BusinessException: ", e);
    ErrorCode errorCode = e.getErrorCode();
    return ResponseEntity.status(errorCode.getCode())
        .body(new ErrorResponse(errorCode.getMessage()));
  }

  @ExceptionHandler(IllegalArgumentException.class)
  public ResponseEntity<ErrorResponse> handleIllegalArgumentException(IllegalArgumentException e) {
    log.info("IllegalArgumentException: ", e);
    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
        .body(new ErrorResponse(e.getMessage()));
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(
      MethodArgumentNotValidException e) {
    log.info("MethodArgumentNotValidException: ", e);
    StringBuilder builder = new StringBuilder();

    for (FieldError fieldError : e.getBindingResult().getFieldErrors()) {
      builder.append(fieldError.getField());
      builder.append("(은)는 ");
      builder.append(fieldError.getDefaultMessage());
    }

    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
        .body(new ErrorResponse(builder.toString()));
  }

  @ExceptionHandler(RuntimeException.class)
  public ResponseEntity<String> handleRuntimeException(RuntimeException e) {
    log.error("RuntimeException : ", e);
    return ResponseEntity.internalServerError().build();
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<String> handleException(Exception e) {
    log.error("Exception : ", e);
    return ResponseEntity.internalServerError().build();
  }

  @ExceptionHandler(AuthenticationException.class)
  public ResponseEntity<ErrorResponse> handleAuthenticationException(AuthenticationException e) {
    log.error("AuthenticationException : ", e);
    ErrorCode errorCode = ErrorCode.AUTHENTICATION_FAILED;
    return ResponseEntity.status(errorCode.getCode())
        .body(new ErrorResponse(errorCode.getMessage()));
  }

  @ExceptionHandler(AccessDeniedException.class)
  public ResponseEntity<ErrorResponse> accessDeniedExceptionHandler(AccessDeniedException e) {
    log.error("AccessDeniedException : ", e);
    ErrorCode errorCode = ErrorCode.ACCESS_FAILED;
    return ResponseEntity.status(errorCode.getCode())
        .body(new ErrorResponse(errorCode.getMessage()));
  }

}
