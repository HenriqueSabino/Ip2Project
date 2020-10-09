package main.java.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ShoppingCart implements Serializable {

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

  public List<CartItem> getItems() {
    return cartItemList;
  }

  @Override
  public String toString() {

    StringBuilder sb = new StringBuilder();

    sb.append("[");
    for (int i = 0; i < cartItemList.size(); i++) {
      if (i == cartItemList.size() - 1) {
        sb.append(cartItemList.get(i).getProduct().getName());
        break;
      }
      sb.append(cartItemList.get(i).getProduct().getName() + ", ");
    }
    sb.append("]");

    return sb.toString();
  }
}
