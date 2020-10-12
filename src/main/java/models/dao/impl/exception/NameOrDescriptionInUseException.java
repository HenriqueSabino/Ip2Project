package main.java.models.dao.impl.exception;

public class NameOrDescriptionInUseException extends RuntimeException {

  public NameOrDescriptionInUseException(String message) {
    super(message);
  }
}
