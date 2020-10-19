package main.java.views;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import main.java.controllers.UserController;
import main.java.models.Trainer;
import main.java.models.User;
import main.java.services.SalesService;

public class TrainerMainPageViewController implements Initializable {

  @FXML private Button backBt;
  @FXML private Button startShoppingBt;
  @FXML private Button shoppingHistoryBt;
  @FXML private Button cureHistoryBt;
  @FXML private Text welcomeTxt;

  private User trainer;

  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {

    trainer = UserController.getInstance().getLoggedUser();

    initializeWelcomeTxt();
  }

  // UI events

  public void onBackBtAction(ActionEvent event) {

    UserController.getInstance().Logout();

    try {

      Parent newPage = FXMLLoader.load(getClass().getResource("/main/java/views/Login.fxml"));
      backBt.getScene().setRoot(newPage);
    } catch (Exception e) {
      System.out.println("Error");
    }
  }

  public void onStartShoppingBtAction(ActionEvent event) {

    SalesService.getInstance().startOrder((Trainer) trainer);

    try {

      Parent newPage =
          FXMLLoader.load(getClass().getResource("/main/java/views/ShoppingCartView.fxml"));
      backBt.getScene().setRoot(newPage);
    } catch (Exception e) {
      System.out.println("Error");
    }
  }

  public void onShoppingHistoryBtAction(ActionEvent event) {

    try {

      Parent newPage =
          FXMLLoader.load(getClass().getResource("/main/java/views/OrderReportView.fxml"));
      backBt.getScene().setRoot(newPage);
    } catch (Exception e) {
      System.out.println("Error");
    }
  }

  public void onCureHistoryBtAction(ActionEvent event) {
    try {

      Parent newPage =
          FXMLLoader.load(getClass().getResource("/main/java/views/CureReportView.fxml"));
      backBt.getScene().setRoot(newPage);
    } catch (Exception e) {
      System.out.println("Error");
    }
  }

  // Initialization methods

  private void initializeWelcomeTxt() {

    String newText = welcomeTxt.getText();

    newText = newText.replace("{name}", trainer.getName());
    newText = newText.replace("{city}", trainer.getBirthCity());

    welcomeTxt.setText(newText);
  }
}
