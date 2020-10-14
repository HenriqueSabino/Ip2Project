package main.java.services;

import main.java.models.CartItem;
import main.java.models.Order;
import main.java.models.ShoppingCart;
import main.java.models.Trainer;
import main.java.models.User;

// Singleton
public class SalesService {

  private static SalesService instance;

  private Order order;
  private ShoppingCart shoppingCart;

  private SalesService() {}

  public static SalesService getInstance() {
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

    boolean alreadyInCart = false;

    for (CartItem i : shoppingCart.getItems()) {

      if (i.getProduct().equals(item.getProduct())) {

        i.setQuantity(i.getQuantity() + item.getQuantity());
        alreadyInCart = true;
        break;
      }
    }

    if (!alreadyInCart) {
      shoppingCart.addItem(item);
    }
  }

  public void removeFromCart(CartItem item) {

    boolean alreadyInCart = false;

    for (CartItem i : shoppingCart.getItems()) {

      if (i.getProduct().equals(item.getProduct())) {

        if (i.getQuantity() > item.getQuantity()) {
          i.setQuantity(i.getQuantity() - item.getQuantity());
          break;
        } else if (i.getQuantity() == item.getQuantity()) {
          shoppingCart.removeItem(i);
          break;
        } else {
          // exception, net quantity cannot be negative
        }

        alreadyInCart = true;
        break;
      }
    }

    if (!alreadyInCart && item.getQuantity() != 0) {

      // exception, item quantity cannot be negative
    }
  }
}
