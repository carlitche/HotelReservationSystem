package com.carlitche.hotelservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ContentNotFoundException extends RuntimeException{

  public ContentNotFoundException() {

  }

  public ContentNotFoundException(String message) {

    super(message);
  }

  public ContentNotFoundException(String message, Throwable cause) {

    super(message, cause);
  }

  public ContentNotFoundException(Throwable cause) {

    super(cause);
  }

}
