package main.java.models;

import java.io.Serializable;

public abstract class Person implements Serializable {

  private String name;
  private String birthCity;
  private String gender;

  public Person() {}

  public Person(String name, String birthCity, String gender) {

    this.name = name;
    this.birthCity = birthCity;
    this.gender = gender;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getBirthCity() {
    return birthCity;
  }

  public void setBirthCity(String birthCity) {
    this.birthCity = birthCity;
  }

  public String getGender() {
    return gender;
  }

  public void setGender(String gender) {
    this.gender = gender;
  }
}
