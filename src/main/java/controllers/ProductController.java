package main.java.controllers;

import java.util.ArrayList;
import java.util.List;
import main.java.models.Product;
import main.java.models.dao.DaoFactory;
import main.java.models.dao.IProductDao;

public class ProductController {

  private static ProductController instance;

  private IProductDao productDao;

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
    return new ArrayList<>(productDao.findAll());
  }

  public IProductDao getProductDao() {
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
