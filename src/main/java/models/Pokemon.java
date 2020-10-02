package main.java.models;

public class Pokemon {

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

  public PokemonType getType() {
    return type;
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

  public Trainer getTrainer() {
    return trainer;
  }
}
