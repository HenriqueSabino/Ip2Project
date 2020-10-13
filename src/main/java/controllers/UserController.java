package main.java.controllers;

import java.util.List;
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

  public User getLoggedUser() {
    return loggedUser;
  }
}
