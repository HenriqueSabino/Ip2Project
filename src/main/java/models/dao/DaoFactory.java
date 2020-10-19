package main.java.models.dao;

import main.java.models.dao.impl.CureDaoFilesImpl;
import main.java.models.dao.impl.OrderDaoFilesImpl;
import main.java.models.dao.impl.ProductDaoFilesImpl;
import main.java.models.dao.impl.UserDaoFilesImpl;

// Utility class used to centralize the DAO's used in the project
public class DaoFactory {

  private static IUserDao userDao;
  private static ICureDao cureDao;
  private static IOrderDao orderDao;
  private static IProductDao productDao;

  public static IUserDao createUserDao() {

    if (userDao == null) {
      userDao = new UserDaoFilesImpl();
    }

    return userDao;
  }

  public static ICureDao createCureDao() {

    if (cureDao == null) {
      cureDao = new CureDaoFilesImpl();
    }

    return cureDao;
  }

  public static IOrderDao createOrderDao() {

    if (orderDao == null) {
      orderDao = new OrderDaoFilesImpl();
    }

    return orderDao;
  }

  public static IProductDao createProductDao() {

    if (productDao == null) {
      productDao = new ProductDaoFilesImpl();
    }

    return productDao;
  }
}
