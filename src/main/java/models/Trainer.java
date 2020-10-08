package main.java.models;

import java.util.ArrayList;
import java.util.List;

public class Trainer extends User {

  private List<Pokemon> pokemons;

  public Trainer(
      int registerId,
      String name,
      String birthCity,
      String gender,
      String username,
      String password,
      String email) {

    super(registerId, name, birthCity, gender, username, password, email);
    pokemons = new ArrayList<>();
  }

  public Trainer(
      String name,
      String birthCity,
      String gender,
      String username,
      String password,
      String email) {

    super(name, birthCity, gender, username, password, email);
    pokemons = new ArrayList<>();
  }

  public List<Pokemon> getPokemons() {
    return pokemons;
  }
}
