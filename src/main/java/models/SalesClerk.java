package main.java.models;

public class SalesClerk extends User {

  public SalesClerk(
      int registerId,
      String name,
      String birthCity,
      String gender,
      String username,
      String password,
      String email) {

    super(registerId, name, birthCity, gender, username, password, email);
  }

  public SalesClerk(
      String name,
      String birthCity,
      String gender,
      String username,
      String password,
      String email) {

    super(name, birthCity, gender, username, password, email);
  }
}
