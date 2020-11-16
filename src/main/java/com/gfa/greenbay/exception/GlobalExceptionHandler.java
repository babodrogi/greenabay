package com.gfa.greenbay.exception;

import com.gfa.greenbay.response.ErrorResponse;
import io.jsonwebtoken.SignatureException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

  @ExceptionHandler(NoSuchUserException.class)
  public ResponseEntity<?> handleNoSuchUserException() {
    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ErrorResponse("User doesn't exist!"));
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
    return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(
        new ErrorResponse("Prices should be positive whole numbers!"));
  }

  @ExceptionHandler(InvalidUrlException.class)
  public ResponseEntity<?> handleInvalidUrlException() {
    return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(
        new ErrorResponse("Invalid Url Provided!"));
  }

  @ExceptionHandler(InvalidPageNumberException.class)
  public ResponseEntity<?> handleInvalidPageNumberException() {
    return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(
        new ErrorResponse("Item listing page number should be a positive whole number!"));
  }

  @ExceptionHandler(NoSuchItemException.class)
  public ResponseEntity<?> handleNoSuchItemException() {
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
        new ErrorResponse("Item does not exist!"));
  }

  @ExceptionHandler(TokenExpiredException.class)
  public ResponseEntity<?> handleTokenExpiredException() {
    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
        new ErrorResponse("Token Expired"));
  }

  @ExceptionHandler(SignatureException.class)
  public ResponseEntity<?> handleSignatureException() {
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
        new ErrorResponse("Shame on YOU naughty hacker :("));
  }

  @ExceptionHandler(ItemNotSellableException.class)
  public ResponseEntity<?> handleItemNotSellableException() {
    return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(
        new ErrorResponse("Item sold!"));
  }

  @ExceptionHandler(InsufficientFundsException.class)
  public ResponseEntity<?> handleInsufficientFundsException() {
    return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(
        new ErrorResponse("You're Poor"));
  }

  @ExceptionHandler(InSufficientBidAmountException.class)
  public ResponseEntity<?> handleInSufficientBidAmountException() {
    return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(
        new ErrorResponse("Bid amount is too low"));
  }

}
