package main.java.views;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import main.java.models.CartItem;
import main.java.models.Order;
import main.java.models.Product;
import main.java.models.ShoppingCart;
import main.java.models.Trainer;

public class OrderReceiptViewController implements Initializable {

  @FXML private Button backBt;
  @FXML private TextArea receiptTxtArea;

  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {

    // Mocking code
    ShoppingCart cart = new ShoppingCart();

    cart.addItem(new CartItem(new Product(0, "Potion", "A potion that heals 20HP", 100), 1, cart));
    cart.addItem(
        new CartItem(new Product(0, "Super Potion", "A potion that heals 50HP", 100), 2, cart));
    cart.addItem(new CartItem(new Product(0, "Potion", "A potion that heals 200HP", 100), 4, cart));

    Trainer trainer = new Trainer("Ash", "Pallet", "Male", "ash", "123", "ash@pallet.com");

    Order order = new Order(0, cart, trainer);

    if (order != null) {
      receiptTxtArea.setText(order.generateReceipt());
    } else {
      System.out.println("Error! order was null.");
    }
  }

  public void onBackBtAction(ActionEvent action) {

    try {

      Parent newPage =
          FXMLLoader.load(getClass().getResource("/main/java/views/SalesClerkMainPage.fxml"));
      backBt.getScene().setRoot(newPage);
    } catch (Exception e) {
      System.out.println("Error");
    }
  }
}
