package main.java.models;

import java.io.Serializable;

public class Pokemon implements Serializable {

  private String species;
  private int life;
  private int maxLife;
  private PokemonType type;
  private PokemonStatus status;
  private Trainer trainer;

  public Pokemon() {}

  public Pokemon(
      String species,
      int life,
      int maxLife,
      PokemonType pokemonType,
      PokemonStatus pokemonStatus,
      Trainer trainer) {

    this.species = species;
    this.life = life;
    this.maxLife = maxLife;
    this.type = pokemonType;
    this.status = pokemonStatus;
    this.trainer = trainer;
  }

  public String getSpecies() {
    return species;
  }

  public void setSpecies(String species) {
    this.species = species;
  }

  public PokemonType getType() {
    return type;
  }

  public void setType(PokemonType type) {
    this.type = type;
  }

  public PokemonStatus getStatus() {
    return status;
  }

  public void setStatus(PokemonStatus status) {
    this.status = status;
  }

  public int getLife() {
    return life;
  }

  public void setLife(int life) {
    this.life = life;
  }

  public int getMaxLife() {
    return maxLife;
  }

  public void setMaxLife(int maxLife) {
    this.maxLife = maxLife;
  }

  public Trainer getTrainer() {
    return trainer;
  }
}
