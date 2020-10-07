package main.java.models.dao;

import main.java.models.dao.impl.UserDaoFiles;

// Utility class used to centralize the DAO's used in the project
public class DaoFactory {

  public static UserDao createUserDao() {
    return new UserDaoFiles();
  }
}
