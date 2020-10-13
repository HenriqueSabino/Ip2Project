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
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import main.java.controllers.UserController;
import main.java.models.Administrator;
import main.java.models.Nurse;
import main.java.models.SalesClerk;
import main.java.models.Trainer;
import main.java.models.User;
import main.java.views.util.Alerts;

public class LoginViewController implements Initializable {

  @FXML private TextField usernameField;
  @FXML private PasswordField passwordField;
  @FXML private Button loginBt;

  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {}

  @FXML
  public void onLoginBtAction(ActionEvent event) {

    if (usernameField.getText().isEmpty() || passwordField.getText().isEmpty()) {

      Alerts.showAlert(
          "Error", null, "Username and password fields must be filled", AlertType.ERROR);
    } else {

      boolean logged =
          UserController.getInstance().Login(usernameField.getText(), passwordField.getText());

      if (logged) {

        User loggedUser = UserController.getInstance().getLoggedUser();

        if (loggedUser instanceof Administrator) {
          goToSalesClerkMainPage();
        } else if (loggedUser instanceof SalesClerk) {
          goToSalesClerkMainPage();
        } else if (loggedUser instanceof Nurse) {
          System.out.println("nur");
        } else if (loggedUser instanceof Trainer) {
          goToSalesClerkMainPage();
        }
      } else {
        Alerts.showAlert(
            "Could not login", null, "Wrong username or password", AlertType.INFORMATION);
      }
    }
  }

  private void goToSalesClerkMainPage() {
    try {

      Parent newPage =
          FXMLLoader.load(getClass().getResource("/main/java/views/SalesClerkMainPage.fxml"));
      passwordField.getScene().setRoot(newPage);
    } catch (Exception e) {
      System.out.println("Error");
    }
  }
}
