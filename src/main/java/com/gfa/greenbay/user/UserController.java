package com.gfa.greenbay.user;

import com.gfa.greenbay.exception.MissingParametersException;
import com.gfa.greenbay.exception.NoSuchUserException;
import com.gfa.greenbay.exception.WrongPasswordException;
import com.gfa.greenbay.response.LoginResponse;
import com.gfa.greenbay.util.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/greenbay")
public class UserController {
  private UserService userService;
  private JwtTokenUtil jwtTokenUtil;

  @Autowired
  public UserController(UserService userService, JwtTokenUtil jwtTokenUtil) {
    this.userService = userService;
    this.jwtTokenUtil = jwtTokenUtil;
  }

  public UserController(UserService userService) {
    this.userService = userService;
  }

  @PostMapping("/login")
  public ResponseEntity<?> login(@RequestBody UserLoginDto userLoginDto) {
    if (userLoginDto.getPassword() == null || userLoginDto.getUsername() == null) {
      throw new MissingParametersException(userLoginDto);
    } else if (!userService.listUsernames().contains(userLoginDto.getUsername())) {
      throw new NoSuchUserException();
    } else if (!userService.findByUsername(userLoginDto.getUsername())
        .getPassword().equals(userLoginDto.getPassword())){
      throw new WrongPasswordException();
    }
    else {
      User user = userService.findByUsername(userLoginDto.getUsername());
      return ResponseEntity.status(HttpStatus.OK)
          .body(new LoginResponse("Successful Login",
              jwtTokenUtil.generateToken(user),
              user.getGreenBayDollars()));
    }
  }
}
