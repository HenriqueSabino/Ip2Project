package main.java.views;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginViewController implements Initializable {

  @FXML private TextField usernameField;
  @FXML private PasswordField passwordField;
  @FXML private Button loginBt;

  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {}

  @FXML
  public void onLoginBtAction(ActionEvent event) {

    System.out.println("Logging in with:");
    System.out.println("Username: " + usernameField.getText());
    System.out.println("Password: " + passwordField.getText());
  }
}
