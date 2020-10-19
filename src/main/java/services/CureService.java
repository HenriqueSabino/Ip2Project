package main.java.services;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import main.java.models.Cure;
import main.java.models.CureReport;
import main.java.models.Pokemon;
import main.java.models.PokemonStatus;
import main.java.models.Trainer;
import main.java.models.User;
import main.java.models.dao.DaoFactory;
import main.java.models.dao.ICureDao;

// Singleton
public class CureService {

  private static CureService instance;

  private List<Cure> cures;
  private ICureDao cureDao;

  private CureService() {
    cures = new ArrayList<>();
    cureDao = DaoFactory.createCureDao();
  }

  public static CureService getInstance() {

    if (instance == null) {
      instance = new CureService();
    }

    return instance;
  }

  public List<Cure> startCures(Trainer client, User employee) {

    for (Pokemon a : client.getPokemons()) {

      Cure cure = new Cure(LocalDateTime.now(), a, a.getLife(), a.getStatus(), employee, client);
      cures.add(cure);

      a.setLife(a.getMaxLife());
      a.setStatus(PokemonStatus.NONE);

      cureDao.insert(cure);
    }
    return cures;
  }

  public void finishCures() {
    cures.clear();
  }

  public List<Cure> getCures() {
    return cures;
  }

  public String getReport(CureReport cureReport) {

    List<Cure> allCures = getAllCures();
    List<Cure> cures = new ArrayList<>();

    if (cureReport.getEmployee() != null) {

      for (Cure c : allCures) {

        if (!cureReport.getEmployee().getUsername().equals(c.getEmployee().getUsername())) {
          cures.add(c);
        }
      }
    }

    if (cureReport.getClient() != null) {

      for (Cure c : allCures) {

        if (!cureReport.getClient().getUsername().equals(c.getClient().getUsername())) {
          cures.add(c);
        }
      }
    }

    if (cureReport.getStartDate() != null) {

      for (Cure c : allCures) {

        if (!c.getCureDate().isAfter(cureReport.getStartDate().atTime(0, 0, 0))
            && !c.getCureDate().isEqual(cureReport.getStartDate().atTime(0, 0, 0))) {
          cures.add(c);
        }
      }
    }

    if (cureReport.getEndDate() != null) {

      for (Cure c : allCures) {

        if (!c.getCureDate().isBefore(cureReport.getEndDate().atTime(0, 0, 0))
            && !c.getCureDate().isEqual(cureReport.getEndDate().atTime(0, 0, 0))) {
          cures.add(c);
        }
      }
    }

    allCures.removeAll(cures);

    System.out.println("Number of cures: " + allCures.size());

    return cureReport.generateReport(allCures);
  }

  public List<Cure> getAllCures() {
    return new ArrayList<>(cureDao.findAll());
  }
}
