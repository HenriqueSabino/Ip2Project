package main.java.models;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class CureReport {

  private int ID;
  private Nurse nurse;
  private Trainer client;
  private Date cureReportDate;
  private List<Pokemon> previousPokemons;

  // NOTE: previousPokemons must be a deep copy of client's pokemon list
  private CureReport(int ID, Nurse nurse, Trainer client, List<Pokemon> previousPokemons) {
    this.ID = ID;
    this.nurse = nurse;
    this.client = client;
    this.previousPokemons = previousPokemons;
    this.cureReportDate = new Date();
  }

  public String generateReport() {
    StringBuilder report = new StringBuilder();
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

    // Generating report header
    report.append("Cure report number: ").append(ID).append("\n\n");
    report.append("Responsible nurse's name: ").append(nurse.getName()).append("\n");
    report.append("Client's name: ").append(client.getName()).append("\n");
    report.append("Date: ").append(sdf.format(cureReportDate)).append("\n\n");

    report.append("Client's pokemons before cure service:").append("\n\n");

    for (Pokemon pokemon : previousPokemons) {
      report.append("Pokemon species: ").append(pokemon.getSpecies()).append("\n");
      report.append("Pokemon type: ").append(pokemon.getType()).append("\n");
      report.append("Pokemon status: ").append(pokemon.getStatus()).append("\n");
      report.append("Pokemon life: ").append(pokemon.getLife()).append("\n\n");
    }

    report.append("Client's pokemons after cure service:").append("\n\n");

    for (Pokemon pokemon : client.getPokemons()) {
      report.append("Pokemon species: ").append(pokemon.getSpecies()).append("\n");
      report.append("Pokemon type: ").append(pokemon.getType()).append("\n");
      report.append("Pokemon status: ").append(pokemon.getStatus()).append("\n");
      report.append("Pokemon life: ").append(pokemon.getLife()).append("\n\n");
    }

    return report.toString();
  }

  public int getID() {
    return ID;
  }

  public Nurse getNurse() {
    return nurse;
  }

  public Trainer getClient() {
    return client;
  }

  public Date getCureReportDate() {
    return cureReportDate;
  }
}
