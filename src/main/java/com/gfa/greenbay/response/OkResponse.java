package com.gfa.greenbay.response;

import lombok.Getter;

@Getter
public class OkResponse {
  private String status;
  private String message;

  public OkResponse(String message) {
    status = "ok";
    this.message = message;
  }
}
