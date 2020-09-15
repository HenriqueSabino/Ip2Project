package main.java.models;

public class Pokemon {

  private String species;
  private int life;
  private int maxLife;
  private PokemonType pokemonType;
  private PokemonStatus pokemonStatus;
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
    this.pokemonType = pokemonType;
    this.pokemonStatus = pokemonStatus;
    this.trainer = trainer;
  }

  public String getSpecies() {
    return species;
  }

  public int getLife() {
    return life;
  }

  public int getMaxLife() {
    return maxLife;
  }

  public PokemonType getType() {
    return pokemonType;
  }

  public PokemonStatus getStatus() {
    return pokemonStatus;
  }

  public Trainer getTrainer() {
    return trainer;
  }

  public void setLife(int life) {
    this.life = life;
  }

  public void setPokemonStatus(PokemonStatus pokemonStatus) {
    this.pokemonStatus = pokemonStatus;
  }
}
