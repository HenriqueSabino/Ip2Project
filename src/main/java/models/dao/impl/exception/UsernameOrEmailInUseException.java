package main.java.models.dao.impl.exception;

public class UsernameOrEmailInUseException extends RuntimeException {

  public UsernameOrEmailInUseException(String message) {
    super(message);
  }
}
