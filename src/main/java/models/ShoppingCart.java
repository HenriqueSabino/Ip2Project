package main.java.models;

import java.util.ArrayList;
import java.util.List;

public class ShoppingCart {

  private List<CartItem> cartItemList;

  public ShoppingCart() {
    cartItemList = new ArrayList<>();
  }

  public ShoppingCart(List<CartItem> cartItemList) {
    this.cartItemList = cartItemList;
  }

  public int calculateTotalCost() {

    int summation = 0;

    for (CartItem item : cartItemList) {

      summation += item.calculateTotalCost();
    }

    return summation;
  }

  public void addItem(CartItem cartItem) {
    cartItemList.add(cartItem);
  }

  public void removeItem(CartItem cartItem) {
    cartItemList.remove(cartItem);
  }

  public void clearShoppingCart() {
    cartItemList.clear();
  }

  public List getItems() {
    return cartItemList;
  }
}
