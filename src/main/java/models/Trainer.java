package main.java.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Trainer extends User implements Serializable {

  private List<Pokemon> pokemons;
  private int registerId;

  public Trainer(
      String name,
      String birthCity,
      String gender,
      String username,
      String password,
      String email,
      int registerId) {

    super(name, birthCity, gender, username, password, email);
    this.registerId = registerId;
    pokemons = new ArrayList<>();
  }

  public List<Pokemon> getPokemons() {
    return pokemons;
  }

  public int getRegisterId() {
    return registerId;
  }
}
