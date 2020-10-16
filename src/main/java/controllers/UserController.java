package main.java.controllers;

import java.util.ArrayList;
import java.util.List;
import main.java.models.Administrator;
import main.java.models.Nurse;
import main.java.models.SalesClerk;
import main.java.models.Trainer;
import main.java.models.User;
import main.java.models.dao.DaoFactory;
import main.java.models.dao.UserDao;

// Singleton
public class UserController {

  private static UserController instance;

  private UserDao userDao;
  private User loggedUser;

  private UserController() {
    userDao = DaoFactory.createUserDao();
  }

  public static UserController getInstance() {

    if (instance == null) {
      instance = new UserController();
    }

    return instance;
  }

  public boolean Login(String username, String password) {

    List<User> users = userDao.findAll();

    for (User u : users) {

      if (u.getUsername().equals(username)) {

        if (u.getPassword().equals(password)) {

          loggedUser = u;
          return true;
        } else {
          return false;
        }
      }
    }

    return false;
  }

  public void Logout() {

    loggedUser = null;
  }

  public User getLoggedUser() {
    return loggedUser;
  }

  public List<Administrator> getAllAdministrators() {

    List<User> allUsers = userDao.findAll();
    List<Administrator> allTrainers = new ArrayList<>();

    for (User u : allUsers) {

      if (u instanceof Administrator) {
        allTrainers.add((Administrator) u);
      }
    }

    return allTrainers;
  }

  public List<Nurse> getAllNurses() {

    List<User> allUsers = userDao.findAll();
    List<Nurse> allTrainers = new ArrayList<>();

    for (User u : allUsers) {

      if (u instanceof Nurse) {
        allTrainers.add((Nurse) u);
      }
    }

    return allTrainers;
  }

  public List<SalesClerk> getAllSalesClerks() {

    List<User> allUsers = userDao.findAll();
    List<SalesClerk> allTrainers = new ArrayList<>();

    for (User u : allUsers) {

      if (u instanceof SalesClerk) {
        allTrainers.add((SalesClerk) u);
      }
    }

    return allTrainers;
  }

  public List<Trainer> getAllTrainers() {

    List<User> allUsers = userDao.findAll();
    List<Trainer> allTrainers = new ArrayList<>();

    for (User u : allUsers) {

      if (u instanceof Trainer) {
        allTrainers.add((Trainer) u);
      }
    }

    return allTrainers;
  }
}
