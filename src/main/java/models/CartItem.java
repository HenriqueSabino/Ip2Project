package main.java.models;

import java.io.Serializable;

public class CartItem implements Serializable {

  private Product product;
  private int quantity;

  public CartItem() {}

  public CartItem(Product product, int quantity) {

    this.product = product;
    this.quantity = quantity;
  }

  public int calculateTotalCost() {
    return product.getPrice() * quantity;
  }

  public int getQuantity() {
    return quantity;
  }

  public void setQuantity(int quantity) {
    this.quantity = quantity;
  }

  public Product getProduct() {
    return product;
  }

  public void setProduct(Product product) {
    this.product = product;
  }
}
