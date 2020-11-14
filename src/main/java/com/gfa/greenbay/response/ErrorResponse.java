package com.gfa.greenbay.response;

import lombok.Getter;

@Getter
public class ErrorResponse {
  private String status;
  private String message;

  public ErrorResponse(String message) {
    status = "error";
    this.message = message;
  }
}
