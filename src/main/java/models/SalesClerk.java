package main.java.models;

public class SalesClerk extends User {

  private int registerID;

  public SalesClerk(
      String name,
      String birthCity,
      String gender,
      String username,
      String password,
      String email,
      int registerID) {

    super(name, birthCity, gender, username, password, email);
    this.registerID = registerID;
  }

  public int getRegisterID() {
    return registerID;
  }
}
