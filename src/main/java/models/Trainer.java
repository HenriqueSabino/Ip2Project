package main.java.models;

import java.util.ArrayList;
import java.util.List;

public class Trainer extends User {

  private List<Pokemon> pokemons;
  private int registerID;

  public Trainer(
      String name,
      String birthCity,
      String gender,
      String username,
      String password,
      String email,
      int registerID) {

    super(name, birthCity, gender, username, password, email);
    this.registerID = registerID;
    pokemons = new ArrayList<>();
  }

  public List<Pokemon> getPokemons() {
    return pokemons;
  }

  public int getRegisterID() {
    return registerID;
  }
}