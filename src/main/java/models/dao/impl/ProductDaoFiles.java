package main.java.models.dao.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import main.java.models.Product;
import main.java.models.dao.DaoFactory;
import main.java.models.dao.ProductDao;
import main.java.models.dao.impl.exception.LocalDBIOException;
import main.java.models.dao.impl.exception.NameOrDescriptionInProductException;
import main.java.models.dao.impl.exception.ProductNotFoundException;

public class ProductDaoFiles implements ProductDao {

  private final String path = "./localStorage/products.ser";
  private ArrayList<Product> products;
  private ArrayList<Product> backup;

  public ProductDaoFiles() {

    File file = new File(path);

    if (file.exists()) {
      readFile();
    } else {

      products = new ArrayList<>();

      insert(new Product("Potion", "It can be used to restore a Pokémon's HP", 300));
      insert(new Product("Antidote", "An antidote for curing a poisoned Pokémon.", 100));

      saveFile();
    }
  }

  public static void main(String[] args) {

    // Please, backup and delete localStorage\\products.ser before testing
    ProductDao dao = DaoFactory.createProductDao();

    if (dao.findAll().size() == 2) {

      Product burnHeal =
          new Product(
              "Burn Heal", "Medicine for curing a Pokémon that is suffering from burn.", 250);
      Product iceHeal =
          new Product("Ice Heal", "Thaws out a Pokémon that has been frozen solid.", 250);

      System.out.println("Testing inserts:");

      dao.insert(burnHeal);

      try {
        dao.insert(burnHeal);
      } catch (NameOrDescriptionInProductException e) {
        System.out.println(e.getMessage());
      }

      dao.insert(iceHeal);

      for (Product t : dao.findAll()) {

        System.out.println(t.getName());
      }

      System.out.println("------------------");
      System.out.println("Testing deletes:");

      dao.deleteById(iceHeal.getId());

      try {
        dao.deleteById(iceHeal.getId());
      } catch (ProductNotFoundException e) {
        System.out.println(e.getMessage());
      }

      System.out.println("------------------");
      System.out.println("Testing Find All:");

      for (Product t : dao.findAll()) {
        System.out.println(t.getName());
      }

      try {
        dao.findById(iceHeal.getId());
      } catch (ProductNotFoundException e) {
        System.out.println(e.getMessage());
      }

      System.out.println("------------------");
      System.out.println("Testing update:");
      Product a = dao.findById(burnHeal.getId());

      a.setName("Ashe");

      dao.update(a);

      try {
        dao.update(iceHeal);
      } catch (ProductNotFoundException e) {
        System.out.println(e.getMessage());
      }
    }
  }

  @Override
  public Product insert(Product product) {

    // backing up if something goes wrong
    backup = (ArrayList<Product>) products.clone();

    for (Product u : products) {

      if (u.getName().equals(product.getName())
          || u.getDescription().equals(product.getDescription())) {
        throw new NameOrDescriptionInProductException(
            "Name and description must be unique. Insert action was canceled");
      }
    }

    // Using LocalDateTime.now().hashCode() to provide uniqueIds to all products
    product.setRegisterId(LocalDateTime.now().hashCode());
    products.add(product);

    saveFile();

    return product;
  }

  @Override
  public Product update(Product product) {

    // backing up if something goes wrong
    backup = (ArrayList<Product>) products.clone();

    int index = -1;

    for (int i = 0; i < products.size(); i++) {

      if (products.get(i).getId() == product.getId()) {
        index = i;
      } else if (products.get(i).getName().equals(product.getName())
          || products.get(i).getDescription().equals(product.getDescription())) {
        throw new NameOrDescriptionInProductException(
            "Name and description must be unique. Update action was canceled");
      }
    }

    if (index < 0) {
      throw new ProductNotFoundException("Product of Id " + product.getId() + " was not found.");
    }

    saveFile();

    return product;
  }

  @Override
  public void deleteById(int id) {

    // backing up if something goes wrong
    backup = (ArrayList<Product>) products.clone();

    int index = -1;

    for (int i = 0; i < products.size(); i++) {

      if (products.get(i).getId() == id) {
        index = i;
      }
    }

    if (index < 0) {
      throw new ProductNotFoundException(
          "Product of Id " + id + " was not found. Delete action could not complete");
    }

    products.remove(index);
    saveFile();
  }

  @Override
  public Product findById(int id) {

    for (Product u : products) {

      if (u.getId() == id) {
        return u;
      }
    }

    throw new ProductNotFoundException(
        "Product of Id " + id + " was not found. Find action could not complete");
  }

  @Override
  public List<Product> findAll() {
    return products;
  }

  private void saveFile() {

    try {

      File file = new File(path);

      if (!file.exists()) {

        File parentDir = file.getParentFile();

        if (!parentDir.exists()) {

          if (!parentDir.mkdirs()) {
            return;
          }
        }

        if (!file.createNewFile()) {
          return;
        }
      }

      FileOutputStream fos = new FileOutputStream(path);
      ObjectOutputStream oos = new ObjectOutputStream(fos);

      oos.writeObject(this.products);

      oos.close();
      fos.close();
    } catch (Exception e) {

      // rollingback
      products = backup;
      throw new LocalDBIOException(e.getMessage());
    }
  }

  private void readFile() {

    try {

      FileInputStream fis = new FileInputStream(path);
      ObjectInputStream ois = new ObjectInputStream(fis);

      Object obj = ois.readObject();
      products = (ArrayList<Product>) obj;

      ois.close();
      fis.close();
    } catch (Exception e) {
      throw new LocalDBIOException(e.getMessage());
    }
  }
}
