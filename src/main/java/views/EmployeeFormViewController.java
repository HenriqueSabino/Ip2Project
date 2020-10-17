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
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import main.java.controllers.UserController;
import main.java.models.Nurse;
import main.java.models.SalesClerk;
import main.java.models.dao.impl.exception.UsernameOrEmailInUseException;
import main.java.views.util.Alerts;

public class EmployeeFormViewController implements Initializable {

  @FXML private TextField nameField;
  @FXML private TextField birthCityField;
  @FXML private TextField genderField;
  @FXML private TextField usernameField;
  @FXML private TextField emailField;
  @FXML private PasswordField passwordField;
  @FXML private Button confirmBt;
  @FXML private Button backBt;
  @FXML private RadioButton nurseBt;
  @FXML private RadioButton salesBt;
  @FXML private ToggleGroup toggleGroup;

  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {}

  @FXML
  public void onConfirmBtAction(ActionEvent event) {

    if (!UserController.getInstance().getUpdate()) {

      try {

        if (nameField.getText().isEmpty()
            || birthCityField.getText().isEmpty()
            || genderField.getText().isEmpty()
            || usernameField.getText().isEmpty()
            || emailField.getText().isEmpty()
            || passwordField.getText().isEmpty()) {
          Alerts.showAlert("Error", null, "The fields must be filled", AlertType.ERROR);
        } else {

          if (nurseBt.isSelected()) {

            UserController.getInstance()
                .insertEmployee(
                    new Nurse(
                        nameField.getText(),
                        birthCityField.getText(),
                        genderField.getText(),
                        usernameField.getText(),
                        passwordField.getText(),
                        emailField.getText()));
          } else {

            UserController.getInstance()
                .insertEmployee(
                    new SalesClerk(
                        nameField.getText(),
                        birthCityField.getText(),
                        genderField.getText(),
                        usernameField.getText(),
                        passwordField.getText(),
                        emailField.getText()));
          }
          Alerts.showAlert("", null, "Registration completed", AlertType.INFORMATION);
          goToEmployeeList();
        }
      } catch (UsernameOrEmailInUseException e) {
        Alerts.showAlert("Error", null, e.getMessage(), AlertType.ERROR);
      }
    } else {

      try {

        if (nameField.getText().isEmpty()
            || birthCityField.getText().isEmpty()
            || genderField.getText().isEmpty()
            || usernameField.getText().isEmpty()
            || emailField.getText().isEmpty()
            || passwordField.getText().isEmpty()) {
          Alerts.showAlert("Error", null, "The fields must be filled to update", AlertType.ERROR);
        } else {

          if (nurseBt.isSelected()) {

            Nurse nurse =
                new Nurse(
                    nameField.getText(),
                    birthCityField.getText(),
                    genderField.getText(),
                    usernameField.getText(),
                    passwordField.getText(),
                    emailField.getText());
            nurse.setRegisterId(UserController.getInstance().getIdForUpdate());
            UserController.getInstance().updateEmployee(nurse);
          } else {

            SalesClerk salesClerk =
                new SalesClerk(
                    nameField.getText(),
                    birthCityField.getText(),
                    genderField.getText(),
                    usernameField.getText(),
                    passwordField.getText(),
                    emailField.getText());
            salesClerk.setRegisterId(UserController.getInstance().getIdForUpdate());
            UserController.getInstance().updateEmployee(salesClerk);
          }

          Alerts.showAlert("", null, "Update completed", AlertType.INFORMATION);
          goToEmployeeList();
        }
      } catch (UsernameOrEmailInUseException e) {
        Alerts.showAlert("Error", null, e.getMessage(), AlertType.ERROR);
      }
    }
  }

  public void onBackBtAction(ActionEvent action) {

    try {

      Parent newPage =
          FXMLLoader.load(getClass().getResource("/main/java/views/EmployeeListView.fxml"));
      backBt.getScene().setRoot(newPage);
    } catch (Exception e) {
      System.out.println("Error");
    }
  }

  public void goToEmployeeList() {

    try {

      Parent newPage =
          FXMLLoader.load(getClass().getResource("/main/java/views/EmployeeListView.fxml"));
      backBt.getScene().setRoot(newPage);
    } catch (Exception e) {
      System.out.println("Error");
    }
  }
}
