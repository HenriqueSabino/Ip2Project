package main.java.views;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import main.java.controllers.UserController;
import main.java.models.Pokemon;
import main.java.models.PokemonStatus;
import main.java.models.PokemonType;
import main.java.models.Trainer;
import main.java.models.dao.impl.exception.UsernameOrEmailInUseException;
import main.java.models.exceptions.ValidationException;
import main.java.views.listeners.DataChangeListener;
import main.java.views.util.Alerts;
import main.java.views.util.Constraints;
import main.java.views.util.Utils;

import java.net.URL;
import java.util.*;

public class PokemonFormViewController implements Initializable {

  private Pokemon entity;
  private Trainer owner;
  private UserController controller;
  private List<DataChangeListener> dataChangeListeners = new ArrayList<>();

  @FXML private Label labelErrorSpecies;
  @FXML private Label labelErrorLife;
  @FXML private Label labelErrorMaxLife;
  @FXML private Label labelErrorType;
  @FXML private Label labelErrorStatus;

  @FXML private TextField textFieldSpecies;
  @FXML private TextField textFieldLife;
  @FXML private TextField textFieldMaxLife;

  @FXML private ComboBox<PokemonType> comboBoxPokemonType;
  @FXML private ComboBox<PokemonStatus> comboBoxPokemonStatus;

  public void setPokemon(Pokemon entity) {
    this.entity = entity;
  }

  public void setOwner(Trainer owner) {
    this.owner = owner;
  }

  public void setUserController(UserController controller) {
    this.controller = controller;
  }

  public void subscribeDataChangeListener(DataChangeListener listener) {
    dataChangeListeners.add(listener);
  }

  @FXML
  public void onButtonSaveAction(ActionEvent event) {
    if (entity == null) {
      throw new IllegalStateException("Entity was null.");
    }

    if (controller == null) {
      throw new IllegalStateException("userController was null.");
    }
    try {

      entity = getFormData();
      owner.addPokemon(entity);
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

  private Pokemon getFormData() {

    Pokemon pokemon = new Pokemon();

    ValidationException exception = new ValidationException("Validation error");

    if (textFieldSpecies.getText() == null || textFieldSpecies.getText().trim().equals("")) {
      exception.addError("species", "Field can't be empty.");
    }
    pokemon.setSpecies(textFieldSpecies.getText());

    if (Utils.tryParseToInt(textFieldMaxLife.getText()) == 0) {
      exception.addError("maxLife", "Field can't be zero.");
    }
    pokemon.setMaxLife(Utils.tryParseToInt(textFieldMaxLife.getText()));

    if (comboBoxPokemonType.getValue() == null) {
      exception.addError("type", "Field can't be empty.");
    }
    pokemon.setType(comboBoxPokemonType.getValue());

    if (exception.getErrors().size() > 0) {
      throw exception;
    }

    return pokemon;
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

    comboBoxPokemonType.getItems().setAll(PokemonType.values());
    comboBoxPokemonStatus.getItems().setAll(PokemonStatus.values());

    Constraints.setTextFieldInteger(textFieldLife);
    Constraints.setTextFieldInteger(textFieldMaxLife);
    Constraints.setTextFieldMaxLength(textFieldSpecies, 20);
  }

  public void updateFormData() {

    if (entity == null) {
      throw new IllegalStateException("Entity was null.");
    }

    textFieldSpecies.setText(entity.getSpecies());
    textFieldLife.setText(String.valueOf(entity.getLife()));
    textFieldMaxLife.setText(String.valueOf(entity.getMaxLife()));
    comboBoxPokemonType.setValue(entity.getType());
    comboBoxPokemonStatus.setValue(entity.getStatus());
  }

  private void setErrorMessages(Map<String, String> errors) {

    Set<String> fields = errors.keySet();

    if (fields.contains("species")) {
      labelErrorSpecies.setText(errors.get("species"));
    }

    if (fields.contains("maxLife")) {
      labelErrorMaxLife.setText(errors.get("maxLife"));
    }

    if (fields.contains("type")) {
      labelErrorType.setText(errors.get("type"));
    }
  }
}
