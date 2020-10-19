package main.java.models;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class Order implements Serializable {

  private int id;
  private LocalDateTime orderDate;
  private ShoppingCart shoppingCart;
  private User employee;
  private Trainer client;

  public Order(ShoppingCart shoppingCart, Trainer client) {

    this.orderDate = LocalDateTime.now(ZoneId.of("America/Recife"));
    this.shoppingCart = shoppingCart;
    this.client = client;
  }

  public Order(ShoppingCart shoppingCart, User employee, Trainer client) {

    this.orderDate = LocalDateTime.now(ZoneId.of("America/Recife"));
    this.shoppingCart = shoppingCart;
    this.employee = employee;
    this.client = client;
  }

  public Order(int id, ShoppingCart shoppingCart, Trainer client) {

    this.id = id;
    this.orderDate = LocalDateTime.now(ZoneId.of("America/Recife"));
    this.shoppingCart = shoppingCart;
    this.client = client;
  }

  public Order(int id, ShoppingCart shoppingCart, User employee, Trainer client) {

    this.id = id;
    this.orderDate = LocalDateTime.now(ZoneId.of("America/Recife"));
    this.shoppingCart = shoppingCart;
    this.employee = employee;
    this.client = client;
  }

  public String generateReceipt() {

    List<CartItem> receiptItemList = shoppingCart.getItems();
    StringBuilder receipt = new StringBuilder();

    // Generating receipt header
    receipt.append("Order number: ").append(id).append("\n\n");
    if (employee != null) {
      receipt.append("Responsible sales clerk name: ").append(employee.getName()).append("\n");
    }
    receipt.append("Client's name: ").append(client.getName()).append("\n");
    receipt
        .append("Data: ")
        .append(orderDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")))
        .append("\n\n");
    receipt.append("Client's items:").append("\n\n");

    // Generating items information
    for (CartItem item : receiptItemList) {

      receipt
          .append("ITEM NAME: ")
          .append(item.getProduct().getName())
          .append(" QTY: ")
          .append(item.getQuantity())
          .append(" VL.UNIT: ")
          .append(item.getProduct().getPrice())
          .append(" VL.ITEMS: ")
          .append(item.calculateTotalCost())
          .append("\n");
    }

    receipt.append("\n").append("TOTAL COST: ").append(shoppingCart.calculateTotalCost());

    return receipt.toString();
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public ShoppingCart getShoppingCart() {
    return shoppingCart;
  }

  public Trainer getClient() {
    return client;
  }

  public User getEmployee() {
    return employee;
  }

  public void setEmployee(User employee) {
    this.employee = employee;
  }

  public LocalDateTime getOrderDate() {
    return orderDate;
  }

  @Override
  public String toString() {

    return "Order {"
        + "\n id: "
        + id
        + "\n"
        + " orderDate: "
        + orderDate
        + "\n"
        + " shoppingCart: "
        + shoppingCart
        + "\n"
        + " employee: "
        + employee.getName()
        + "\n"
        + " client: "
        + client.getName()
        + "\n"
        + "}\n";
  }
}
