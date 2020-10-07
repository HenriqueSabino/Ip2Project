package main.java.models;

import java.io.Serializable;

public abstract class User extends Person implements Serializable {

  private int registerId;
  private String username;
  private String password;
  private String email;

  public User() {}

  public User(
      String name,
      String birthCity,
      String gender,
      String username,
      String password,
      String email) {

    super(name, birthCity, gender);
    this.username = username;
    this.password = password;
    this.email = email;
  }

  public User(
      int registerId,
      String name,
      String birthCity,
      String gender,
      String username,
      String password,
      String email) {

    super(name, birthCity, gender);
    this.registerId = registerId;
    this.username = username;
    this.password = password;
    this.email = email;
  }

  public int getRegisterId() {
    return registerId;
  }

  public void setRegisterId(int registerId) {
    this.registerId = registerId;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }
}
