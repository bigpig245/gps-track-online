package com.example.demo.util;

import org.springframework.http.HttpStatus;

public enum Message {
  OK("0", "SUCCESS", HttpStatus.OK),
  GPX_NOT_FOUND("ERR_001", "GPX not found", HttpStatus.NOT_FOUND);

  private String code;
  private String message;
  private HttpStatus httpStatus;

  Message(String code, String message, HttpStatus httpStatus) {
    this.code = code;
    this.message = message;
    this.httpStatus = httpStatus;
  }

  public String getCode() {
    return code;
  }

  public String getMessage() {
    return message;
  }

  public HttpStatus getHttpStatus() {
    return httpStatus;
  }
}
