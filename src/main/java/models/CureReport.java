package main.java.models;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class CureReport {

  private List<Cure> cures;


  public CureReport(List<Cure> cures) {
    this.cures = cures;
  }

  public String generateReport() {
    
    StringBuilder report = new StringBuilder();
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

    report.append("Trainer's cure history:").append("\n\n");

    for (Cure cure : cures)
    {
      report.append("Cure id: ").append(cure.getId()).append("\n");
      report.append("Responsible employee's name: ").append(cure.getEmployee().getName()).append("\n");
      report.append("Client's name: ").append(cure.getClient().getName()).append("\n");
      report.append("Cure date: ").append(sdf.format(cure.getCureDate())).append("\n\n");

      report.append("Pokemon data:").append("\n");
      report.append("Pokemon species: ").append(cure.getPokemon().getSpecies()).append("\n");
      report.append("Pokemon type: ").append(cure.getPokemon().getType()).append("\n\n");

      report.append("Pokemon's status before cure:").append("\n");
      report.append("Pokemon status: ").append(cure.getPreviousPokemonStatus()).append("\n");
      report.append("Pokemon life: ").append(cure.getPreviousPokemonLife()).append("\n\n");

      report.append("Pokemon's status after cure:").append("\n");
      report.append("Pokemon status: ").append(cure.getPokemon().getStatus()).append("\n");
      report.append("Pokemon life: ").append(cure.getPokemon().getLife()).append("\n\n");
    }

    return report.toString();
  }
}
