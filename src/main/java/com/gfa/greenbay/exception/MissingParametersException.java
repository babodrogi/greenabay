package com.gfa.greenbay.exception;

import com.gfa.greenbay.user.UserLoginDto;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;

@Getter
public class MissingParametersException extends RuntimeException {

  private List<String> missingParams;

  public MissingParametersException(UserLoginDto userLoginDto){
    missingParams = new ArrayList<>();
    if(userLoginDto.getUsername() == null){
      missingParams.add("username");
    }
    if(userLoginDto.getPassword() == null){
      missingParams.add("password");
    }
  }
}
