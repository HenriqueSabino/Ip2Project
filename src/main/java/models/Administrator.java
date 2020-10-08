package main.java.models;

public class Administrator extends User {

  public Administrator(
      int registerId,
      String name,
      String birthCity,
      String gender,
      String username,
      String password,
      String email) {

    super(registerId, name, birthCity, gender, username, password, email);
  }

  public Administrator(
      String name,
      String birthCity,
      String gender,
      String username,
      String password,
      String email) {

    super(name, birthCity, gender, username, password, email);
  }
}
