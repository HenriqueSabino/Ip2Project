package main.java.controllers;

import java.util.List;
import main.java.models.Product;
import main.java.models.dao.DaoFactory;
import main.java.models.dao.ProductDao;

public class ProductController {

  private static ProductController instance;

  private ProductDao productDao;

  private ProductController() {
    productDao = DaoFactory.createProductDao();
  }

  public static ProductController getInstance() {

    if (instance == null) {
      instance = new ProductController();
    }

    return instance;
  }

  public void insertProduct(Product product) {
    productDao.insert(product);
  }

  public List<Product> getAllProducts() {
    return productDao.findAll();
  }

  public ProductDao getProductDao() {
    return productDao;
  }

  public void deleteProductById(int id) {
    productDao.deleteById(id);
  }

  public void updateProduct(Product product) {

    if (product.getId() == 0) {
      productDao.insert(product);
    } else {
      productDao.update(product);
    }
  }
}
