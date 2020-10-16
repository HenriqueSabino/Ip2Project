package main.java.models;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import main.java.models.dao.CureDao;
import main.java.models.dao.DaoFactory;

public class CureReport {

  private LocalDateTime startDate;
  private LocalDateTime endDate;
  private Trainer client;
  private User employee;

  public CureReport() {}

  public static void main(String[] args) {

    LocalDateTime dateC1 = LocalDateTime.now();
    LocalDateTime dateC2 = LocalDateTime.now().plusDays(4);

    User joy = new Nurse("Joy", "Palete", "Female", "joyzinha", "12345", "emaildajoyzinha");

    User anotherJoy =
        new Nurse("Joy2", "Palete", "Female", "joyzinha2", "1234", "emaildajoyzinha2");

    Trainer ash = new Trainer("Ash", "Palete", "Male", "ashinho", "1234", "email");

    Pokemon eevee = new Pokemon("Eevee", 15, 100, PokemonType.NORMAL, PokemonStatus.BURNT, ash);

    Pokemon pikachu =
        new Pokemon("pikachu", 1, 140, PokemonType.ELECTRIC, PokemonStatus.PARALYZED, ash);

    Cure cure1 = new Cure(dateC1, eevee, eevee.getLife(), eevee.getStatus(), joy, ash);

    Cure cure2 = new Cure(dateC2, pikachu, pikachu.getLife(), pikachu.getStatus(), joy, ash);

    Cure cure3 = new Cure(dateC1, eevee, eevee.getLife(), eevee.getStatus(), anotherJoy, ash);

    Cure cure4 = new Cure(dateC2, pikachu, pikachu.getLife(), pikachu.getStatus(), anotherJoy, ash);

    CureDao cureDao = DaoFactory.createCureDao();
    cureDao.insert(cure1);
    cureDao.insert(cure2);
    cureDao.insert(cure3);
    cureDao.insert(cure4);

    CureReport cureReport1 = new CureReport();
    cureReport1.setStartDate(dateC1);
    cureReport1.setEndDate(dateC1.plusDays(10));
    cureReport1.setClient(ash);
    cureReport1.setEmployee(joy);
    System.out.println(
        "Testing 'start date: "
            + cureReport1.startDate
            + ", end date: "
            + cureReport1.endDate
            + ", trainer: "
            + cureReport1.client
            + ", employee: "
            + cureReport1.employee
            + "' filter...");
    System.out.println("------------------------");
    System.out.println(cureReport1.getReport());

    CureReport cureReport2 = new CureReport();
    cureReport2.setStartDate(dateC1);
    cureReport2.setEndDate(dateC1.plusDays(10));
    cureReport2.setClient(ash);
    System.out.println(
        "Testing 'start date: "
            + cureReport2.startDate.format(DateTimeFormatter.ofPattern("HH:mm dd/MM/yyyy"))
            + ", end date: "
            + cureReport2.endDate.format(DateTimeFormatter.ofPattern("HH:mm dd/MM/yyyy"))
            + ", trainer: "
            + cureReport2.client.getName()
            + "' filter...");
    System.out.println("------------------------");
    System.out.println(cureReport2.getReport());

    CureReport cureReport3 = new CureReport();
    cureReport3.setStartDate(dateC1);
    cureReport3.setEndDate(dateC1.plusDays(10));
    cureReport3.setEmployee(joy);
    System.out.println(
        "Testing 'start date: "
            + cureReport3.startDate.format(DateTimeFormatter.ofPattern("HH:mm dd/MM/yyyy"))
            + ", end date: "
            + cureReport3.endDate.format(DateTimeFormatter.ofPattern("HH:mm dd/MM/yyyy"))
            + ", employee: "
            + cureReport3.employee.getName()
            + "' filter...");
    System.out.println("------------------------");
    System.out.println(cureReport3.getReport());

    CureReport cureReport4 = new CureReport();
    cureReport4.setStartDate(dateC1);
    cureReport4.setEndDate(dateC1.plusDays(10));
    System.out.println(
        "Testing 'start date: "
            + cureReport4.startDate.format(DateTimeFormatter.ofPattern("HH:mm dd/MM/yyyy"))
            + ", end date: "
            + cureReport4.endDate.format(DateTimeFormatter.ofPattern("HH:mm dd/MM/yyyy"))
            + "' filter...");
    System.out.println("------------------------");
    System.out.println(cureReport4.getReport());

    CureReport cureReport5 = new CureReport();
    cureReport5.setStartDate(dateC1);
    System.out.println(
        "Testing 'start date: "
            + cureReport5.startDate.format(DateTimeFormatter.ofPattern("HH:mm dd/MM/yyyy"))
            + "' filter...");
    System.out.println("------------------------");
    System.out.println(cureReport5.getReport());
  }

  public List<Cure> getAllCures() {

    CureDao cureDao = DaoFactory.createCureDao();
    return cureDao.findAll();
  }

  public void setStartDate(LocalDateTime startDate) {
    this.startDate = startDate;
  }

  public void setEndDate(LocalDateTime endDate) {
    this.endDate = endDate;
  }

  public void setClient(Trainer client) {
    this.client = client;
  }

  public void setEmployee(User employee) {
    this.employee = employee;
  }

  public String getReport() {

    List<Cure> allCures = getAllCures();
    List<Cure> cures = new ArrayList<>();

    if (employee != null) {

      for (Cure c : allCures) {

        if (!employee.getUsername().equals(c.getEmployee().getUsername())) {

          cures.add(c);
        }
      }
    }

    if (client != null) {

      for (Cure c : allCures) {

        if (!client.getUsername().equals(c.getClient().getUsername())) {

          cures.add(c);
        }
      }
    }

    if (startDate != null) {

      for (Cure c : allCures) {

        if (!c.getCureDate().isAfter(startDate)) {

          cures.add(c);
        }
      }
    }

    if (endDate != null) {

      for (Cure c : allCures) {

        if (!c.getCureDate().isBefore(endDate)) {
          cures.add(c);
        }
      }
    }

    allCures.removeAll(cures);

    System.out.println("Number of cures: " + allCures.size());
    return generateReport(allCures);
  }

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
      report.append("Pokemon status: ").append(cure.getPokemon().getStatus()).append("\n");
      report.append("Pokemon life: ").append(cure.getPokemon().getLife()).append("\n\n");
    }

    return report.toString();
  }
}
