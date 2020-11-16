package com.gfa.greenbay.user;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserLoginDto {

  private String username;
  private String password;
}
