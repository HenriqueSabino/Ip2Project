package main.java.models.dao;

import java.util.List;
import main.java.models.Product;

public interface ProductDao {

  Product insert(Product product);

  Product update(Product product);

  void deleteById(int id);

  Product findById(int id);

  List<Product> findAll();
}
