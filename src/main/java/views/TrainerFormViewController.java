package main.java.views;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import main.java.controllers.UserController;
import main.java.models.Pokemon;
import main.java.models.Trainer;
import main.java.models.dao.impl.exception.UsernameOrEmailInUseException;
import main.java.models.exceptions.ValidationException;
import main.java.views.listeners.DataChangeListener;
import main.java.views.util.Alerts;
import main.java.views.util.Constraints;
import main.java.views.util.Utils;

import java.net.URL;
import java.util.*;

public class TrainerFormViewController implements Initializable {

  private Trainer entity;

  private UserController userController;

  private List<DataChangeListener> dataChangeListeners = new ArrayList<>();

  @FXML private Label labelErrorName;
  @FXML private Label labelErrorBirthCity;
  @FXML private Label labelErrorGender;
  @FXML private Label labelErrorUsername;
  @FXML private Label labelErrorPassword;
  @FXML private Label labelErrorEmail;

  @FXML private TextField textFieldRegisterId;
  @FXML private TextField textFieldName;
  @FXML private TextField textFieldBirthCity;
  @FXML private TextField textFieldGender;
  @FXML private TextField textFieldUsername;
  @FXML private TextField textFieldPassword;
  @FXML private TextField textFieldEmail;

  @FXML private Button buttonSave;
  @FXML private Button buttonCancel;

  // pokemon viewtable properties
  @FXML private TableView<Pokemon> tableViewPokemon;
  @FXML private TableColumn<Pokemon, String> tableColumnSpecies;
  @FXML private TableColumn<Pokemon, String> tableColumnLife;
  @FXML private TableColumn<Pokemon, String> tableColumnMaxLife;
  @FXML private TableColumn<Pokemon, String> tableColumnType;
  @FXML private TableColumn<Pokemon, String> tableColumnStatus;
  // end

  public void setTrainer(Trainer entity) {
    this.entity = entity;
  }

  public void setUserController(UserController userController) {
    this.userController = userController;
  }

  public void subscribeDataChangeListener(DataChangeListener listener) {
    dataChangeListeners.add(listener);
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
      notifyDataChangeListeners();
      Utils.getCurrentStage(event).close();
    } catch (UsernameOrEmailInUseException e) {

      Alerts.showAlert(
          "UsernameOrEmailInUseException", null, e.getMessage(), Alert.AlertType.ERROR);
    } catch (ValidationException validationException) {
      setErrorMessages(validationException.getErrors());
    }
  }

  private void notifyDataChangeListeners() {
    for (DataChangeListener listener : dataChangeListeners) {
      listener.onDataChanged();
    }
  }

  private Trainer getFormData() {

    Trainer trainer = new Trainer();

    ValidationException exception = new ValidationException("Validation error");

    trainer.setRegisterId(Utils.tryParseToInt(textFieldRegisterId.getText()));

    if (textFieldName.getText() == null || textFieldName.getText().trim().equals("")) {
      exception.addError("name", "Field can't be empty.");
    }
    trainer.setName(textFieldName.getText());

    if (textFieldBirthCity.getText() == null || textFieldBirthCity.getText().trim().equals("")) {
      exception.addError("birthCity", "Field can't be empty.");
    }
    trainer.setBirthCity(textFieldBirthCity.getText());

    if (textFieldGender.getText() == null || textFieldGender.getText().trim().equals("")) {
      exception.addError("gender", "Field can't be empty.");
    }
    trainer.setGender(textFieldGender.getText());

    if (textFieldUsername.getText() == null || textFieldUsername.getText().trim().equals("")) {
      exception.addError("username", "Field can't be empty.");
    }
    trainer.setUsername(textFieldUsername.getText());

    if (textFieldPassword.getText() == null || textFieldPassword.getText().trim().equals("")) {
      exception.addError("password", "Field can't be empty.");
    }
    trainer.setPassword(textFieldPassword.getText());

    if (textFieldEmail.getText() == null || textFieldEmail.getText().trim().equals("")) {
      exception.addError("email", "Field can't be empty.");
    }
    trainer.setEmail(textFieldEmail.getText());

    if (exception.getErrors().size() > 0) {
      throw exception;
    }

    return trainer;
  }

  @FXML
  public void onButtonCancelAction(ActionEvent event) {
    Utils.getCurrentStage(event).close();
  }

  @FXML
  public void onButtonNewPokemonAction() {
    System.out.println("onButtonNewPokemonAction");
  }

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    initializeNodes();
    if (entity != null) {
      updatePokemonTableView();
    }
  }

  private void initializeNodes() {

    Constraints.setTextFieldMaxLength(textFieldName, 30);
    Constraints.setTextFieldMaxLength(textFieldBirthCity, 30);
    Constraints.setTextFieldMaxLength(textFieldGender, 6);
    Constraints.setTextFieldMaxLength(textFieldEmail, 30);
    Constraints.setTextFieldMaxLength(textFieldUsername, 20);
    Constraints.setTextFieldMaxLength(textFieldPassword, 10);

    // pokemon tableview nodes
    tableColumnSpecies.setCellValueFactory(new PropertyValueFactory<>("species"));
    tableColumnLife.setCellValueFactory(new PropertyValueFactory<>("life"));
    tableColumnMaxLife.setCellValueFactory(new PropertyValueFactory<>("maxLife"));
    tableColumnType.setCellValueFactory(new PropertyValueFactory<>("type"));
    tableColumnStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
    // end nodes
  }

  private void updatePokemonTableView() {

    // work to continue...
    Pokemon p = new Pokemon();
    List<Pokemon> pokemonList = new ArrayList<>();
    pokemonList.add(p);
    System.out.println("test");
    ObservableList<Pokemon> pokemonObservableList= FXCollections.observableArrayList(pokemonList);
    tableViewPokemon.setItems(pokemonObservableList);
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

  private void setErrorMessages(Map<String, String> errors) {

    Set<String> fields = errors.keySet();

    if (fields.contains("name")) {
      labelErrorName.setText(errors.get("name"));
    }

    if (fields.contains("birthCity")) {
      labelErrorBirthCity.setText(errors.get("birthCity"));
    }

    if (fields.contains("gender")) {
      labelErrorGender.setText(errors.get("gender"));
    }

    if (fields.contains("username")) {
      labelErrorUsername.setText(errors.get("username"));
    }

    if (fields.contains("password")) {
      labelErrorPassword.setText(errors.get("password"));
    }

    if (fields.contains("email")) {
      labelErrorEmail.setText(errors.get("email"));
    }
  }
}
