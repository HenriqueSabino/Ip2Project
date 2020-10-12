package main.java.models.dao;

import main.java.models.dao.impl.CureDaoFiles;
import main.java.models.dao.impl.OrderDaoFiles;
import main.java.models.dao.impl.ProductDaoFiles;
import main.java.models.dao.impl.UserDaoFiles;

// Utility class used to centralize the DAO's used in the project
public class DaoFactory {

  public static UserDao createUserDao() {
    return new UserDaoFiles();
  }

  public static CureDao createCureDao() {
    return new CureDaoFiles();
  }

  public static OrderDao createOrderDao() {
    return new OrderDaoFiles();
  }

  public static ProductDao createProductDao() {
    return new ProductDaoFiles();
  }
}
