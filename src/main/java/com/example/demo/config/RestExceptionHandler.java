package com.example.demo.config;

import com.example.demo.dto.ErrorDto;
import com.example.demo.exception.GPXException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * exception handler for controller
 */
@ControllerAdvice
public class RestExceptionHandler {

  @ExceptionHandler(value = {GPXException.class})
  @ResponseBody
  public ResponseEntity<ErrorDto> handleResourceUnauthorized(GPXException ex) {
    ErrorDto error = new ErrorDto(ex.getRawMessage().getCode(), ex.getRawMessage().getMessage());
    return ResponseEntity.status(ex.getRawMessage().getHttpStatus()).body(error);
  }

  @ExceptionHandler(value = {Exception.class})
  @ResponseBody
  public ResponseEntity<ErrorDto> handleResourceUnauthorized(Exception ex) {
    ErrorDto error = new ErrorDto(ex.getMessage(), ex.getMessage());
    return ResponseEntity.badRequest().body(error);
  }

}
