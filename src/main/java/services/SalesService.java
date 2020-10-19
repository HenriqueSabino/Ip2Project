package main.java.services;

import java.util.ArrayList;
import java.util.List;
import main.java.models.CartItem;
import main.java.models.Order;
import main.java.models.OrderReport;
import main.java.models.ShoppingCart;
import main.java.models.Trainer;
import main.java.models.User;
import main.java.models.dao.DaoFactory;
import main.java.models.dao.IOrderDao;
import main.java.services.exception.CartItemNotFoundException;
import main.java.services.exception.OrderWasNullException;

// Singleton
public class SalesService {

  private static SalesService instance;

  private Order order;
  private IOrderDao orderDao;
  private ShoppingCart shoppingCart;

  private SalesService() {
    orderDao = DaoFactory.createOrderDao();
  }

  public static SalesService getInstance() {

    if (instance == null) {
      instance = new SalesService();
    }

    return instance;
  }

  public void startOrder(User employee, Trainer client) {

    shoppingCart = new ShoppingCart();
    order = new Order(shoppingCart, employee, client);
  }

  public void startOrder(Trainer client) {

    shoppingCart = new ShoppingCart();
    order = new Order(shoppingCart, client);
  }

  public void placeOrder() {

    if (order != null) {
      order = orderDao.insert(order);
    } else {
      // TODO: throw exception
    }
  }

  public void finishOrder() {
    order = null;
    shoppingCart = null;
  }

  public void cancelOrder() {

    shoppingCart = null;
    order = null;
  }

  public void addToCart(CartItem item) {

    boolean itemInCart = false;

    item.setQuantity(item.getQuantity() + 1);

    for (CartItem i : shoppingCart.getItems()) {

      if (i.getProduct().equals(item.getProduct())) {
        itemInCart = true;
        break;
      }
    }

    if (!itemInCart) {
      shoppingCart.addItem(item);
    }
  }

  public void removeFromCart(CartItem item) throws CartItemNotFoundException {

    boolean success = false;

    for (CartItem i : shoppingCart.getItems()) {

      if (i.equals(item)) {

        if (i.getQuantity() > 1) {

          i.setQuantity(i.getQuantity() - 1);

          success = true;
          break;
        } else {

          i.setQuantity(0);
          shoppingCart.removeItem(i);

          success = true;
          break;
        }
      }
    }

    if (!success) {

      throw new CartItemNotFoundException("This item is not in your shopping cart");
    }
  }

  public String generateReceipt() throws OrderWasNullException {

    String receipt = "";

    if (order != null) {
      receipt = order.generateReceipt();
    } else {
      throw new OrderWasNullException("No order was started.");
    }

    return receipt;
  }

  public Order getOrder() {
    return order;
  }

  public List<Order> getAllOrders() {
    return new ArrayList<>(orderDao.findAll());
  }

  public String getReport(OrderReport orderReport) {

    List<Order> allOrders = getAllOrders();
    List<Order> orders = new ArrayList<>();

    if (orderReport.getEmployee() != null) {

      for (Order o : allOrders) {

        // Since the trainer can buy thing by themselves we must check if the order employee is null
        if (o.getEmployee() == null
            || !orderReport.getEmployee().getUsername().equals(o.getEmployee().getUsername())) {

          orders.add(o);
        }
      }
    }

    if (orderReport.getClient() != null) {

      for (Order o : allOrders) {

        if (!orderReport.getClient().getUsername().equals(o.getClient().getUsername())) {
          orders.add(o);
        }
      }
    }

    if (orderReport.getStartDate() != null) {

      for (Order o : allOrders) {

        if (!o.getOrderDate().isAfter(orderReport.getStartDate().atTime(0, 0, 0))
            && !o.getOrderDate().isEqual(orderReport.getStartDate().atTime(0, 0, 0))) {
          orders.add(o);
        }
      }
    }

    if (orderReport.getEndDate() != null) {

      for (Order o : allOrders) {

        if (!o.getOrderDate().isBefore(orderReport.getEndDate().atTime(0, 0, 0))
            && !o.getOrderDate().isEqual(orderReport.getEndDate().atTime(0, 0, 0))) {
          orders.add(o);
        }
      }
    }

    allOrders.removeAll(orders);

    return orderReport.generateReport(allOrders);
  }

  public ShoppingCart getShoppingCart() {
    return shoppingCart;
  }
}
