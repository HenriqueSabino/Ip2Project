package main.java.models;

public class Nurse extends User {

  private int registerId;

  public Nurse(
      String name,
      String birthCity,
      String gender,
      String username,
      String password,
      String email,
      int registerID) {

    super(name, birthCity, gender, username, password, email);
    this.registerId = registerID;
  }

  public int getRegisterId() {
    return registerId;
  }
}
