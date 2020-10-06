package main.java.models.dao;

import main.java.models.dao.impl.TrainerDaoFiles;

// Utility class used to centralize the DAO's used in the project
public class DaoFactory {

  public static TrainerDao createTrainerDao() {
    return new TrainerDaoFiles();
  }
}
