package main.java.models;

public class SalesClerk extends User {

  private int registerId;

  public SalesClerk(
      String name,
      String birthCity,
      String gender,
      String username,
      String password,
      String email,
      int registerId) {

    super(name, birthCity, gender, username, password, email);
    this.registerId = registerId;
  }

  public int getRegisterId() {
    return registerId;
  }
}
