package main.java.models;

public class Nurse extends User {

  public Nurse(
      int registerId,
      String name,
      String birthCity,
      String gender,
      String username,
      String password,
      String email) {

    super(registerId, name, birthCity, gender, username, password, email);
  }

  public Nurse(
      String name,
      String birthCity,
      String gender,
      String username,
      String password,
      String email) {

    super(name, birthCity, gender, username, password, email);
  }
}
