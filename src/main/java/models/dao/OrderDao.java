package main.java.models.dao;

import main.java.models.Order;

import java.util.List;

public interface OrderDao {

  Order insert(Order order);

  Order update(Order order);

  void deleteById(int id);

  Order findById(int id);

  List<Order> findAll();
}
