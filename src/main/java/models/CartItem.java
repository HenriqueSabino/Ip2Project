package main.java.models;

public class CartItem {

  private ShoppingCart shoppingCart;
  private Product product;
  private int quantity;

  public CartItem() {}

  public CartItem(Product product, int quantity, ShoppingCart shoppingCart) {

    this.shoppingCart = shoppingCart;
    this.product = product;
    this.quantity = quantity;
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

  public int calculateTotalCost() {
    return product.getPrice() * quantity;
  }

  public ShoppingCart getShoppingCart() {
    return shoppingCart;
  }
}
