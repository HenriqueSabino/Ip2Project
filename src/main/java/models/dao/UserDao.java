package main.java.models.dao;

import java.util.List;
import main.java.models.User;

public interface UserDao {

  User insert(User user);

  User update(User user);

  void deleteById(int id);

  User findById(int id);

  List<User> findAll();
}
