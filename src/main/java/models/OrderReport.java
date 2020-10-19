package main.java.models;

import java.time.LocalDate;
import java.util.List;

public class OrderReport {

  private LocalDate startDate;
  private LocalDate endDate;
  private Trainer client;
  private User employee;

  public OrderReport() {}

  public String generateReport(List<Order> orders) {

    StringBuilder report = new StringBuilder();

    report.append("Trainer's shopping history:").append("\n\n");

    for (Order order : orders) {
      report.append(order.generateReceipt()).append("\n\n");
    }

    return report.toString();
  }

  public LocalDate getStartDate() {
    return startDate;
  }

  public void setStartDate(LocalDate startDate) {
    this.startDate = startDate;
  }

  public LocalDate getEndDate() {
    return endDate;
  }

  public void setEndDate(LocalDate endDate) {
    this.endDate = endDate;
  }

  public Trainer getClient() {
    return client;
  }

  public void setClient(Trainer client) {
    this.client = client;
  }

  public User getEmployee() {
    return employee;
  }

  public void setEmployee(User employee) {
    this.employee = employee;
  }
}
