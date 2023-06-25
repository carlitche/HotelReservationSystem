package com.carlitche.hotelservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class CustomizedResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

  @ExceptionHandler(NoSuchElementFoundException.class)
  public final ResponseEntity<Object> handleNotFoundException(NoSuchElementFoundException ex) {
    ErrorMsg errorMsg = new ErrorMsg(HttpStatus.NOT_FOUND);
    errorMsg.setMessage(ex.getMessage());
    return buildResponseEntity(errorMsg);
  }

  private ResponseEntity<Object> buildResponseEntity(ErrorMsg error) {
    return new ResponseEntity<>(error, error.getStatus());
  }
}
