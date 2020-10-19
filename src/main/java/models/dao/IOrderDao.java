package main.java.models.dao;

import java.util.List;
import main.java.models.Order;

public interface IOrderDao {

  Order insert(Order order);

  Order update(Order order);

  void deleteById(int id);

  Order findById(int id);

  List<Order> findAll();
}
