package main.java.models;

import java.text.SimpleDateFormat;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.time.format.DateTimeFormatter;

public class Order {

  private int id;
  private LocalDateTime orderDate;
  ShoppingCart shoppingCart;
  SalesClerk salesClerk;
  Trainer client;

  public Order(int id, ShoppingCart shoppingCart, Trainer client) {

    this.id = id;
    this.orderDate = LocalDateTime.now(ZoneId.of("America/Recife"));
    this.shoppingCart = shoppingCart;
    this.client = client;
  }

  public Order(int id, ShoppingCart shoppingCart, SalesClerk salesClerk, Trainer client) {

    this.id = id;
    this.orderDate = LocalDateTime.now(ZoneId.of("America/Recife"));
    this.shoppingCart = shoppingCart;
    this.salesClerk = salesClerk;
    this.client = client;
  }

  public int getId() {
    return id;
  }

  public ShoppingCart getShoppingCart() {
    return shoppingCart;
  }

  public Trainer getClient() {
    return client;
  }

  public SalesClerk getSalesClerk() {
    return salesClerk;
  }

  public LocalDateTime getOrderDate() {
    return orderDate;
  }

  public String generateReceipt() {

    List<CartItem> receiptItemList = shoppingCart.getItems();
    StringBuilder receipt = new StringBuilder();

    // Generating receipt header
    receipt.append("Order number: ").append(id).append("\n\n");
    receipt.append("Responsible sales clerk name: ").append(salesClerk.getName()).append("\n");
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
}
