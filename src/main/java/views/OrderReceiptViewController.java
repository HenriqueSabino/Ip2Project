package main.java.views;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import main.java.services.SalesService;
import main.java.services.exception.OrderWasNullException;
import main.java.views.util.Alerts;

public class OrderReceiptViewController implements Initializable {

  @FXML private Button backBt;
  @FXML private TextArea receiptTxtArea;

  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {

    try {
      receiptTxtArea.setText(SalesService.getInstance().generateReceipt());
    } catch (OrderWasNullException e) {
      Alerts.showAlert("Error!", null, e.getMessage(), AlertType.ERROR);
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
