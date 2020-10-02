package main.java.models;

import java.time.LocalDateTime;

public class Cure {

    private int id;
    private LocalDateTime cureDate;
    private Pokemon pokemon;
    private int previousPokemonLife;
    private PokemonStatus previousPokemonStatus;
    private User employee;
    private Trainer client;

    public Cure(int id, LocalDateTime cureDate, Pokemon pokemon, int previousPokemonLife, PokemonStatus previousPokemonStatus, Trainer client) {
        this.id = id;
        this.cureDate = cureDate;
        this.pokemon = pokemon;
        this.previousPokemonLife = previousPokemonLife;
        this.previousPokemonStatus = previousPokemonStatus;
        this.client = client;
    }

    public Cure(int id, LocalDateTime cureDate, Pokemon pokemon, int previousPokemonLife, PokemonStatus previousPokemonStatus, User employee, Trainer client) {
        this.id = id;
        this.cureDate = cureDate;
        this.pokemon = pokemon;
        this.previousPokemonLife = previousPokemonLife;
        this.previousPokemonStatus = previousPokemonStatus;
        this.employee = employee;
        this.client = client;
    }

    public int getId() {
        return id;
    }

    public LocalDateTime getCureDate() {
        return cureDate;
    }

    public Pokemon getPokemon() {
        return pokemon;
    }

    public int getPreviousPokemonLife() {
        return previousPokemonLife;
    }

    public PokemonStatus getPreviousPokemonStatus() {
        return previousPokemonStatus;
    }

    public User getEmployee() {
        return employee;
    }

    public Trainer getClient() {
        return client;
    }
}
