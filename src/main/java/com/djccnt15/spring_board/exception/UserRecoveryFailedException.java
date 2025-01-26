package com.djccnt15.spring_board.exception;

public class UserRecoveryFailedException extends RuntimeException {
  public UserRecoveryFailedException(String message) {
    super(message);
  }
}
