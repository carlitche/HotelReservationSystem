package com.carlitche.hotelservice.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class CustomizedResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

  @ExceptionHandler(ContentNotFoundException.class)
  public final ResponseEntity<ErrorMsgDetail> handleNotFoundException(Exception ex, WebRequest request) throws Exception {
    ErrorMsgDetail msgDetail = new ErrorMsgDetail(LocalDateTime.now(),
                                                  ex.getMessage(),
                                                  request.getDescription(false));

    return new ResponseEntity<ErrorMsgDetail>(msgDetail, HttpStatus.NOT_FOUND);
  }
}
