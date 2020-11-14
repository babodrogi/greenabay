package com.gfa.greenbay.exception;

import com.gfa.greenbay.response.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

  @ExceptionHandler(NoSuchUserException.class)
  public ResponseEntity<?> handleNoSuchUserException() {
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse("User doesn't exist!"));
  }

  @ExceptionHandler(MissingParametersException.class)
  public ResponseEntity<?> handleMissingParametersException(MissingParametersException exception){
    String response = "Missing Parameter(s): " + String.join(",",exception.getMissingParams());
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse(response));
  }

  @ExceptionHandler(WrongPasswordException.class)
  public ResponseEntity<?> handleWrongPasswordException() {
    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ErrorResponse("Wrong Password"));
  }

  @ExceptionHandler(NoTokenException.class)
  public ResponseEntity<?> handleNoTokenException() {
    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ErrorResponse("Not Logged in"));
  }

  @ExceptionHandler(InvalidPriceException.class)
  public ResponseEntity<?> handleInvalidPriceException() {
    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
        new ErrorResponse("Prices should be positive whole numbers!"));
  }

  @ExceptionHandler(InvalidUrlException.class)
  public ResponseEntity<?> handleInvalidUrlException() {
    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
        new ErrorResponse("Invalid Url Provided!"));
  }

}
