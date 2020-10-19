package main.java.models;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class CureReport {

  private LocalDate startDate;
  private LocalDate endDate;
  private Trainer client;
  private User employee;

  public CureReport() {}

  public String generateReport(List<Cure> cures) {

    StringBuilder report = new StringBuilder();

    report.append("Trainer's cure history:").append("\n\n");

    for (Cure cure : cures) {
      report.append("Cure id: ").append(cure.getId()).append("\n");
      report
          .append("Responsible employee's name: ")
          .append(cure.getEmployee().getName())
          .append("\n");
      report.append("Client's name: ").append(cure.getClient().getName()).append("\n");
      report
          .append("Cure date: ")
          .append(cure.getCureDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")))
          .append("\n\n");

      report.append("Pokemon data:").append("\n");
      report.append("Pokemon species: ").append(cure.getPokemon().getSpecies()).append("\n");
      report.append("Pokemon type: ").append(cure.getPokemon().getType()).append("\n\n");

      report.append("Pokemon's status before cure:").append("\n");
      report.append("Pokemon status: ").append(cure.getPreviousPokemonStatus()).append("\n");
      report.append("Pokemon life: ").append(cure.getPreviousPokemonLife()).append("\n\n");

      report.append("Pokemon's status after cure:").append("\n");
      report.append("Pokemon status: NONE").append("\n");
      report.append("Pokemon life: ").append(cure.getPokemon().getMaxLife()).append("\n\n");
    }

    return report.toString();
  }

  public LocalDate getStartDate() {
    return startDate;
  }

  public void setStartDate(LocalDate startDate) {
    this.startDate = startDate;
  }

  public LocalDate getEndDate() {
    return endDate;
  }

  public void setEndDate(LocalDate endDate) {
    this.endDate = endDate;
  }

  public Trainer getClient() {
    return client;
  }

  public void setClient(Trainer client) {
    this.client = client;
  }

  public User getEmployee() {
    return employee;
  }

  public void setEmployee(User employee) {
    this.employee = employee;
  }
}
