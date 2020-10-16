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

  public List<Trainer> getAllTrainers() {

    List<User> allUsers = userDao.findAll();
    List<Trainer> allTrainers = new ArrayList<>();

    for (User u : allUsers) {

      if (u instanceof Trainer) {
        allTrainers.add((Trainer) u);
      }
    }

    if (allTrainers.size() < 1) {

      List<Trainer> trainers = new ArrayList<>();

      trainers.add(new Trainer(1, "Hyan", "Nova Morada", "Male", "hyanbatista42", "12345", "hyan@gmail.com"));
      trainers.add(new Trainer(2, "Nelsu", "Detran", "Male", "nelsucc", "12345", "nelsu@gmail.com"));
      trainers.add(new Trainer(3, "Silas", "Ribeirão", "Male", "silascmm", "12345", "silas@gmail.com"));
      trainers.add(new Trainer(4, "Henrico", "Iputinga", "Male", "henricomg", "12345", "henrico@gmail.com"));
      allTrainers = trainers;
    }

    return allTrainers;
  }

  public void insertTrainer(Trainer trainer) {

    if (trainer == null) {
      throw new IllegalStateException("The passed in argument is null.");
    }

    userDao.insert(trainer);
  }

  public void removeTrainer(Trainer trainer) {

    if (trainer == null) {
      throw new IllegalStateException("The passed in argument is null.");
    }

    userDao.deleteById(trainer.getRegisterId());
  }

  public void saveOrUpdate(Trainer trainer) {

    if (trainer.getRegisterId() == 0) {
      userDao.insert(trainer);
    } else {
      userDao.update(trainer);
    }
  }
}
