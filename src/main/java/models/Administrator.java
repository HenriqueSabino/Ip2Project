package main.java.models;

public class Administrator extends User {

  private int registerID;

  public Administrator() {
  }

  public Administrator(String name, String birthCity, String gender, String username, String password, String email, int registerID) {

    super(name, birthCity, gender, username, password, email);
    this.registerID = registerID;
  }

  public int getRegisterID() {
    return registerID;
  }
}
