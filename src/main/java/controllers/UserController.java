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
  private boolean update;
  private int idForUpdate;

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
    List<Administrator> allAdministrators = new ArrayList<>();

    for (User u : allUsers) {

      if (u instanceof Administrator) {
        allAdministrators.add((Administrator) u);
      }
    }

    return allAdministrators;
  }

  public List<Nurse> getAllNurses() {

    List<User> allUsers = userDao.findAll();
    List<Nurse> allNurses = new ArrayList<>();

    for (User u : allUsers) {

      if (u instanceof Nurse) {
        allNurses.add((Nurse) u);
      }
    }

    return allNurses;
  }

  public List<SalesClerk> getAllSalesClerks() {

    List<User> allUsers = userDao.findAll();
    List<SalesClerk> allSalesClerks = new ArrayList<>();

    for (User u : allUsers) {

      if (u instanceof SalesClerk) {
        allSalesClerks.add((SalesClerk) u);
      }
    }

    return allSalesClerks;
  }

  public List<User> getAllEmployees() {

    List<User> allUsers = userDao.findAll();
    List<User> allEmployees = new ArrayList<>();

    for (User u : allUsers) {

      if (u instanceof Nurse || u instanceof SalesClerk) {
        allEmployees.add(u);
      }
    }
    return allEmployees;
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

  public void insertEmployee(User employee) {

    if (employee == null) {
      throw new IllegalStateException("The passed in argument is null.");
    }

    userDao.insert(employee);
  }

  public boolean isUpdate() {
    return update;
  }

  public void setUpdate(boolean verification) {
    this.update = verification;
  }

  public void updateEmployee(User employee) {

    if (employee.getRegisterId() == 0) {
      userDao.insert(employee);
    } else {
      userDao.update(employee);
    }
  }

  public void deleteEmployeeById(int id) {
    userDao.deleteById(id);
  }

  public int getIdForUpdate() {
    return idForUpdate;
  }

  public void setIdForUpdate(int idForUpdate) {
    this.idForUpdate = idForUpdate;
  }
}
