package com.gfa.greenbay.response;

import lombok.Getter;

@Getter
public class LoginResponse {
  private String status;
  private String message;
  private String token;
  private int greenBayDollars;

  public LoginResponse(String message,String token,int greenBayDollars) {
    status = "ok";
    this.message = message;
    this.token = token;
    this.greenBayDollars = greenBayDollars;
  }
}
