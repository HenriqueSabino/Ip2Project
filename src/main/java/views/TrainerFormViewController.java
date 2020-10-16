package main.java.views;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import main.java.controllers.UserController;
import main.java.models.Trainer;
import main.java.models.dao.impl.exception.UsernameOrEmailInUseException;
import main.java.views.util.Alerts;
import main.java.views.util.Constraints;
import main.java.views.util.Utils;

import java.net.URL;
import java.util.ResourceBundle;

public class TrainerFormViewController implements Initializable {

  private Trainer entity;

  private UserController userController;

  @FXML private TextField textFieldRegisterId;
  @FXML private TextField textFieldName;
  @FXML private TextField textFieldBirthCity;
  @FXML private TextField textFieldGender;
  @FXML private TextField textFieldUsername;
  @FXML private TextField textFieldPassword;
  @FXML private TextField textFieldEmail;

  @FXML private Button buttonSave;
  @FXML private Button buttonCancel;

  public void setTrainer(Trainer entity) {
    this.entity = entity;
  }

  public void setUserController(UserController userController) {
    this.userController = userController;
  }

  @FXML
  public void onButtonSaveAction(ActionEvent event) {

    if (entity == null) {
      throw new IllegalStateException("Entity was null.");
    }

    if (userController == null) {
      throw new IllegalStateException("userController was null.");
    }
    try {
      entity = getFormData();
      userController.saveOrUpdate(entity);
      Utils.getCurrentStage(event).close();
    } catch (UsernameOrEmailInUseException e) {
      Alerts.showAlert(
          "UsernameOrEmailInUseException", null, e.getMessage(), Alert.AlertType.ERROR);
    }
  }

  private Trainer getFormData() {

    Trainer trainer = new Trainer();

    trainer.setName(textFieldName.getText());
    trainer.setBirthCity(textFieldBirthCity.getText());
    trainer.setGender(textFieldGender.getText());
    trainer.setUsername(textFieldUsername.getText());
    trainer.setPassword(textFieldPassword.getText());
    trainer.setEmail(textFieldEmail.getText());

    return trainer;
  }

  @FXML
  public void onButtonCancelAction(ActionEvent event) {
    Utils.getCurrentStage(event).close();
  }

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    initializeNodes();
  }

  private void initializeNodes() {

    Constraints.setTextFieldInteger(textFieldRegisterId);
    Constraints.setTextFieldMaxLength(textFieldName, 30);
    Constraints.setTextFieldMaxLength(textFieldBirthCity, 30);
    Constraints.setTextFieldMaxLength(textFieldGender, 6);
    Constraints.setTextFieldMaxLength(textFieldEmail, 30);
    Constraints.setTextFieldMaxLength(textFieldUsername, 20);
    Constraints.setTextFieldMaxLength(textFieldPassword, 10);
  }

  public void updateFormData() {

    if (entity == null) {
      throw new IllegalStateException("Entity was null.");
    }

    textFieldRegisterId.setText(String.valueOf(entity.getRegisterId()));
    textFieldName.setText(entity.getName());
    textFieldBirthCity.setText(entity.getBirthCity());
    textFieldGender.setText(entity.getGender());
    textFieldUsername.setText(entity.getUsername());
    textFieldPassword.setText(entity.getPassword());
    textFieldEmail.setText(entity.getEmail());
  }
}
