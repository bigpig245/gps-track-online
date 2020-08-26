package com.example.demo.exception;

import com.example.demo.util.Message;

public class GPXException extends RuntimeException {

  private Message message;

  public GPXException(Message message) {
    this.message = message;
  }

  public Message getRawMessage() {
    return message;
  }

  @Override
  public String getMessage() {
    return message.getMessage();
  }

}
