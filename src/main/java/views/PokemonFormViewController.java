package main.java.views;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;
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

public class PokemonFormViewController implements Initializable {

  private Pokemon entity;
  private Trainer owner;
  private UserController controller;
  private List<DataChangeListener> dataChangeListeners = new ArrayList<>();

  @FXML private Label labelErrorSpecies;
  @FXML private Label labelErrorMaxLife;
  @FXML private Label labelErrorType;

  @FXML private TextField textFieldSpecies;
  @FXML private TextField textFieldMaxLife;

  @FXML private ComboBox<PokemonType> comboBoxPokemonType;

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

      getFormData();
      if (owner.getPokemons().size() < 6) {
        owner.getPokemons().add(entity);
      }
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

  private void getFormData() {

    ValidationException exception = new ValidationException("Validation error");

    if (textFieldSpecies.getText() == null || textFieldSpecies.getText().trim().equals("")) {
      exception.addError("species", "Field can't be empty.");
    } else {
      entity.setSpecies(textFieldSpecies.getText());
    }

    if (Utils.tryParseToInt(textFieldMaxLife.getText()) == 0) {
      exception.addError("maxLife", "Field can't be zero.");
    } else {
      entity.setMaxLife(Utils.tryParseToInt(textFieldMaxLife.getText()));
      entity.setLife(entity.getMaxLife());
    }

    if (comboBoxPokemonType.getValue() == null) {
      exception.addError("type", "Field can't be empty.");
    } else {
      entity.setType(comboBoxPokemonType.getValue());
      entity.setStatus(PokemonStatus.NONE);
    }

    if (exception.getErrors().size() > 0) {
      throw exception;
    }
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

    Constraints.setTextFieldInteger(textFieldMaxLife);
    Constraints.setTextFieldMaxLength(textFieldSpecies, 20);
  }

  public void updateFormData() {

    if (entity == null) {
      throw new IllegalStateException("Entity was null.");
    }

    textFieldSpecies.setText(entity.getSpecies());
    textFieldMaxLife.setText(String.valueOf(entity.getMaxLife()));
    comboBoxPokemonType.setValue(entity.getType());
  }

  private void setErrorMessages(Map<String, String> errors) {

    Set<String> fields = errors.keySet();

    if (fields.contains("species")) {
      labelErrorSpecies.setText(errors.get("species"));
    } else {
      labelErrorSpecies.setText("");
    }

    if (fields.contains("maxLife")) {
      labelErrorMaxLife.setText(errors.get("maxLife"));
    } else {
      labelErrorMaxLife.setText("");
    }

    if (fields.contains("type")) {
      labelErrorType.setText(errors.get("type"));
    } else {
      labelErrorType.setText("");
    }
  }
}
