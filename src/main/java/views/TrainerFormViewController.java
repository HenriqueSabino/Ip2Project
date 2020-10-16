package main.java.views;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import main.java.models.Trainer;
import main.java.views.util.Constraints;

import java.net.URL;
import java.util.ResourceBundle;

public class TrainerFormViewController implements Initializable {

  private Trainer entity;

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

  @FXML
  public void onButtonSaveAction() {
    System.out.println("save");
  }

  @FXML
  public void onButtonCancelAction() {
    System.out.println("cancel");
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