package main.java.views;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import main.java.controllers.UserController;
import main.java.models.Pokemon;
import main.java.models.PokemonStatus;
import main.java.models.PokemonType;
import main.java.views.util.Constraints;
import main.java.views.util.Utils;

import java.net.URL;
import java.util.ResourceBundle;

public class PokemonFormViewController implements Initializable {

  private Pokemon entity;
  private UserController controller;

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

  public void setUserController(UserController controller) {
    this.controller = controller;
  }

  @FXML
  public void onButtonSaveAction() {
    System.out.println("onButtonSaveAction");
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
}
