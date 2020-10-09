package main.java.models;

import main.java.models.dao.CureDao;
import main.java.models.dao.DaoFactory;
import main.java.models.dao.OrderDao;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CureReport {

  private LocalDateTime startDate;
  private LocalDateTime endDate;
  private Trainer client;
  private User employee;

  public CureReport(LocalDateTime startDate, LocalDateTime endDate, Trainer client, User employee) {

    this.startDate = startDate;
    this.endDate = endDate;
    this.client = client;
    this.employee = employee;
  }

  public CureReport(LocalDateTime startDate, LocalDateTime endDate, Trainer client) {

    this.startDate = startDate;
    this.endDate = endDate;
    this.client = client;
  }

  public CureReport(LocalDateTime startDate, LocalDateTime endDate) {

    this.startDate = startDate;
    this.endDate = endDate;
  }

  public static void main(String[] args) {

    LocalDateTime dateC1 = LocalDateTime.now();
    LocalDateTime dateC2 = LocalDateTime.now().plusDays(4);

    User joy = new Nurse(
            "Joy",
            "Palete",
            "Female",
            "joyzinha",
            "12345",
            "emaildajoyzinha");

    Trainer ash = new Trainer(
            "Ash",
            "Palete",
            "Male",
            "ashinho",
            "1234",
            "email");

    Pokemon eevee = new Pokemon(
            "Eevee",
            15,
            100,
            PokemonType.NORMAL,
            PokemonStatus.BURNT,
            ash);

    Pokemon pikachu = new Pokemon(
            "pikachu",
            1,
            140,
            PokemonType.ELECTRIC,
            PokemonStatus.PARALYZED,
            ash);

    Cure cure1 = new Cure(
            dateC1,
            eevee,
            eevee.getLife(),
            eevee.getStatus(),
            joy,
            ash);

    Cure cure2 = new Cure(
            dateC2,
            pikachu,
            pikachu.getLife(),
            pikachu.getStatus(),
            joy,
            ash);

    CureDao cureDao = DaoFactory.createCureDao();
    cureDao.insert(cure1);
    cureDao.insert(cure2);

    System.out.println("Testing 'LocalDateTime startDate, LocalDateTime endDate, Trainer client, User employee' filter...");
    System.out.println("------------------------");

    CureReport cureReport1 = new CureReport(dateC2, dateC1.plusDays(10), ash, joy);
    System.out.println(cureReport1.getReport());

    System.out.println("Testing 'LocalDateTime startDate, LocalDateTime endDate, Trainer client' filter...");
    System.out.println("------------------------");

    CureReport cureReport2 = new CureReport(dateC1, dateC1.plusDays(10), ash);
    System.out.println(cureReport2.getReport());

    System.out.println("Testing 'LocalDateTime startDate, LocalDateTime endDate' filter...");
    System.out.println("------------------------");

    CureReport cureReport3 = new CureReport(dateC2, dateC1.plusDays(10));
    System.out.println(cureReport3.getReport());
  }

  public List<Cure> getAllCures() {

    CureDao cureDao = DaoFactory.createCureDao();
    return cureDao.findAll();
  }

  public String getReport() {

    List<Cure> allCures = getAllCures();
    List<Cure> cures = new ArrayList<>();

    if (employee == null && client == null) {

        for (Cure c : allCures) {

          if (c.getCureDate().isAfter(startDate) && c.getCureDate().isBefore(endDate)) {
            cures.add(c);
          }
        }
    } else if (employee == null && client != null) {
      for (Cure c : allCures) {

        if (c.getCureDate().isAfter(startDate) && c.getCureDate().isBefore(endDate)) {
          if (c.getClient().getUsername().equals(client.getUsername())) {

            cures.add(c);
          }
        }

      }
    } else {
      for (Cure c : allCures) {

        if (c.getCureDate().isAfter(startDate) && c.getCureDate().isBefore(endDate)) {
          if (c.getClient().getUsername().equals(client.getUsername()) && c.getEmployee().getUsername().equals(employee.getUsername())) {

            cures.add(c);
          }
        }
      }

    }

    System.out.println("Number of cures: " + cures.size());
    return generateReport(cures);
  }

  public String generateReport(List<Cure> cures) {
    
    StringBuilder report = new StringBuilder();

    report.append("Trainer's cure history:").append("\n\n");

    for (Cure cure : cures)
    {
      report.append("Cure id: ").append(cure.getId()).append("\n");
      report.append("Responsible employee's name: ").append(cure.getEmployee().getName()).append("\n");
      report.append("Client's name: ").append(cure.getClient().getName()).append("\n");
      report.append("Cure date: ").append(cure.getCureDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))).append("\n\n");

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
