package main.java.models.dao.impl;

import main.java.models.*;
import main.java.models.dao.DaoFactory;
import main.java.models.dao.OrderDao;
import main.java.models.dao.impl.exception.*;

import java.io.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class OrderDaoFiles implements OrderDao {

  private final String path = "./localStorage/orders.ser";
  private ArrayList<Order> orders;
  private ArrayList<Order> backup;

  public OrderDaoFiles() {

    File file = new File(path);

    if (file.exists()) {
      readFile();
    } else {

      orders = new ArrayList<>();

      saveFile();
    }
  }

  public static void main(String[] args) {

    OrderDao dao = DaoFactory.createOrderDao();

    if (dao.findAll().size() == 0) {

      Trainer ash = new Trainer("Ash", "Pallet", "male", "ash", "password", "email");

      ash.getPokemons()
          .add(new Pokemon("Pikachu", 60, 60, PokemonType.ELECTRIC, PokemonStatus.NONE, ash));

      SalesClerk joy = new SalesClerk(
              "Joy",
              "Ribeirão",
              "Female",
              "joyzinha",
              "1234",
              "emaildajoyzinha");

      ShoppingCart shoppingCart = new ShoppingCart();

      Product pokeball =
              new Product(
                      "Pokebola",
                      "Item used to catch pokemons around the world.",
                      5
                      );

      Product backpack =
              new Product(
                      "backpack",
                      "One can use such an item to carry other items around.",
                      50
              );

      Product toothBrush =
              new Product(
                      "toothBrush",
                      "Don't forget of brushing your teeth. Adventures can get tooth cavity too!",
                      5
              );

      Product underwear =
              new Product(
                      "Underwear",
                      "Don't be like Ash and keep always a fresh underwear in your backpack.",
                      5
              );

      shoppingCart.addItem(
              new CartItem(pokeball, 10, shoppingCart));

      shoppingCart.addItem(
              new CartItem(backpack, 1, shoppingCart));

      shoppingCart.addItem(
              new CartItem(toothBrush, 1, shoppingCart));

      shoppingCart.addItem(
              new CartItem(underwear, 7, shoppingCart));

      Order order = new Order(
              shoppingCart,
              joy,
              ash);

      Order order2 = new Order(
              shoppingCart,
              joy,
              ash);

      System.out.println("Testing inserts...");

      dao.insert(order);
      dao.insert(order2);

      for (Order d : dao.findAll()) {
        System.out.println(d.getId());
      }

      System.out.println("------------------");
      System.out.println("Testing deletes:");

      dao.deleteById(order.getId());

      try {
        dao.deleteById(order.getId());
      } catch (OrderNotFoundException e) {
        System.out.println(e.getMessage());
      }

      for (Order o : dao.findAll()) {

        System.out.println("Order items: " + o.getShoppingCart());
      }

      try {
        dao.findById(order.getId());
      } catch (OrderNotFoundException e) {
        System.out.println(e.getMessage());
      }

      System.out.println("------------------");
      System.out.println("Testing updates...");
      System.out.println();

      dao.insert(order);

      Order anOrder = dao.findById(order.getId());

      Nurse anotherJoy = new Nurse(
              "Megumin",
              "Camelote",
              "Female",
              "meguiminha",
              "12345",
              "emaildameguminha");

      System.out.println("Before update:");
      System.out.println(anOrder.toString());

      anOrder.setEmployee(anotherJoy);

      dao.update(anOrder);

      try {
        dao.update(anOrder);
      } catch (OrderNotFoundException e) {
        System.out.println(e.getMessage());
      }

      System.out.println("After update:");
      System.out.println(anOrder);

    } else {

      for (Order order : dao.findAll()) {

        System.out.println(order);
      }
    }
  }

  @Override
  public Order insert(Order order) {

    // Back up if something goes wrong
    backup = (ArrayList<Order>) orders.clone();

    // Use LocalDateTime.now().hashCode() to provide uniqueIds to all users
    order.setId(LocalDateTime.now().hashCode());
    orders.add(order);

    saveFile();

    return order;
  }

  @Override
  public Order update(Order order) {

    backup = (ArrayList<Order>) orders.clone();

    int index = -1;

    for (int i = 0; i < orders.size(); i++) {
      if (orders.get(i).getId() == order.getId()) {
        index = i;
      }
    }

    if (index < 0) {
      throw new OrderNotFoundException("Order of Id " + order.getId() + " wasn't found.");
    }

    orders.remove(orders.get(index));
    orders.add(order);
    saveFile();

    return order;
  }

  @Override
  public void deleteById(int id) {
    // backing up if something goes wrong
    backup = (ArrayList<Order>) orders.clone();

    int index = -1;

    for (int i = 0; i < orders.size(); i++) {

      if (orders.get(i).getId() == id) {
        index = i;
      }
    }

    if (index < 0) {
      throw new OrderNotFoundException(
              "Order of Id " + id + " was not found. Delete action could not be completed");
    }

    orders.remove(index);
    saveFile();
  }

  @Override
  public Order findById(int id) {

    for (Order o : orders) {
      if (o.getId() == id) {
        return o;
      }
    }

    throw new OrderNotFoundException(
            "Order of Id " + id + " was not found. Find action could not be completed");
  }

  @Override
  public List<Order> findAll() {
    return orders;
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

      oos.writeObject(this.orders);

      oos.close();
      fos.close();
    } catch (Exception e) {

      // rollingback
      orders = backup;
      throw new LocalDBIOException(e.getMessage());
    }
  }

  private void readFile() {

    try {

      FileInputStream fis = new FileInputStream(path);
      ObjectInputStream ois = new ObjectInputStream(fis);

      Object obj = ois.readObject();
      orders = (ArrayList<Order>) obj;

      ois.close();
      fis.close();
    } catch (Exception e) {
      throw new LocalDBIOException(e.getMessage());
    }
  }
}
