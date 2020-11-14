package com.gfa.greenbay.exception;

import com.gfa.greenbay.item.NewItemDto;
import com.gfa.greenbay.user.UserLoginDto;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;

@Getter
public class MissingParametersException extends RuntimeException {

  private List<String> missingParams;

  public MissingParametersException(UserLoginDto userLoginDto) {
    missingParams = new ArrayList<>();
    if (userLoginDto.getUsername() == null) {
      missingParams.add("username");
    }
    if (userLoginDto.getPassword() == null) {
      missingParams.add("password");
    }
  }

  public MissingParametersException(NewItemDto item) {
    missingParams = new ArrayList<>();
    if (item.getName() == null) {
      missingParams.add("Name");
    }
    if (item.getDescription() == null) {
      missingParams.add("Description");
    }
    if (item.getPhotoUrl() == null) {
      missingParams.add("Photo URL");
    }
    if (item.getStartingPrice() == null) {
      missingParams.add("Starting Price");
    }
    if (item.getPurchasePrice() == null) {
      missingParams.add("Purchase Price");
    }
  }
}
