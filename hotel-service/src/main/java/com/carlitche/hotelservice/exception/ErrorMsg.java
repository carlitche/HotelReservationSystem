package com.carlitche.hotelservice.exception;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

public class ErrorMsg {

  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
  private LocalDateTime timestamp;

  private String message;

  private String msgDetails;

  private HttpStatus status;

  public ErrorMsg() {
    timestamp = LocalDateTime.now();
  }

  public ErrorMsg(HttpStatus status) {
    this();
    this.status = status;
  }

  public ErrorMsg(HttpStatus status, Throwable ex) {
    this();
    this.status = status;
    this.msgDetails = ex.getLocalizedMessage();
  }

  public ErrorMsg(HttpStatus status, String message, Throwable ex) {
    this();
    this.status = status;
    this.message = message;
    this.msgDetails = ex.getLocalizedMessage();
  }

  public LocalDateTime getTimestamp() {
    return timestamp;
  }

  public void setTimestamp(LocalDateTime timestamp) {
    this.timestamp = timestamp;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public String getMsgDetails() {
    return msgDetails;
  }

  public void setMsgDetails(String msgDetails) {
    this.msgDetails = msgDetails;
  }

  public HttpStatus getStatus() {
    return status;
  }

  public void setStatus(HttpStatus status) {
    this.status = status;
  }

}
