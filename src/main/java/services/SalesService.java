package main.java.services;

import main.java.models.CartItem;
import main.java.models.Order;
import main.java.models.ShoppingCart;
import main.java.models.Trainer;
import main.java.models.User;
import main.java.services.exception.CartItemNotFoundException;
import main.java.services.exception.OrderWasNullException;

// Singleton
public class SalesService {

  private static SalesService instance;

  private Order order;
  private ShoppingCart shoppingCart;

  private SalesService() {}

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

  public void cancelOrder() {

    shoppingCart = null;
    order = null;
  }

  public void addToCart(CartItem item) {

    for (CartItem i : shoppingCart.getItems()) {

      if (i.getProduct().equals(item.getProduct())) {

        shoppingCart.removeItem(i);
        break;
      }
    }

    shoppingCart.addItem(item);
  }

  public void removeFromCart(CartItem item) throws CartItemNotFoundException {

    boolean success = false;

    for (CartItem i : shoppingCart.getItems()) {

      if (i.equals(item)) {

        shoppingCart.removeItem(i);
        success = true;
        break;
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
}
