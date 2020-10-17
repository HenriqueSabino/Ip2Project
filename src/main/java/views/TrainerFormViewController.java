package main.java.views;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.Set;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import main.java.controllers.UserController;
import main.java.models.Pokemon;
import main.java.models.Trainer;
import main.java.models.dao.impl.exception.UsernameOrEmailInUseException;
import main.java.models.exceptions.ValidationException;
import main.java.views.listeners.DataChangeListener;
import main.java.views.util.Alerts;
import main.java.views.util.Constraints;
import main.java.views.util.Utils;

public class TrainerFormViewController implements Initializable, DataChangeListener {

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
  @FXML private Button buttonDeletePokemon;

  // pokemon viewtable properties
  @FXML private TableView<Pokemon> tableViewPokemon;
  @FXML private TableColumn<Pokemon, String> tableColumnSpecies;
  @FXML private TableColumn<Pokemon, String> tableColumnMaxLife;
  @FXML private TableColumn<Pokemon, String> tableColumnType;
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
      if (entity.getPokemons().size() > 0) {
        getFormData();
        userController.saveOrUpdate(entity);
        notifyDataChangeListeners();
        Utils.getCurrentStage(event).close();
      } else {
        Alerts.showAlert(
            "Error: invalid number of pokémons",
            null,
            "The trainer needs to have at least one pokémon.",
            Alert.AlertType.ERROR);
      }

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

    if (textFieldName.getText() == null || textFieldName.getText().trim().equals("")) {
      exception.addError("name", "Field can't be empty.");
    } else {
      entity.setName(textFieldName.getText());
    }

    if (textFieldBirthCity.getText() == null || textFieldBirthCity.getText().trim().equals("")) {
      exception.addError("birthCity", "Field can't be empty.");
    } else {
      entity.setBirthCity(textFieldBirthCity.getText());
    }

    if (textFieldGender.getText() == null || textFieldGender.getText().trim().equals("")) {
      exception.addError("gender", "Field can't be empty.");
    } else {
      entity.setGender(textFieldGender.getText());
    }

    if (textFieldUsername.getText() == null || textFieldUsername.getText().trim().equals("")) {
      exception.addError("username", "Field can't be empty.");
    } else {
      entity.setUsername(textFieldUsername.getText());
    }

    if (textFieldPassword.getText() == null || textFieldPassword.getText().trim().equals("")) {
      exception.addError("password", "Field can't be empty.");
    } else {
      entity.setPassword(textFieldPassword.getText());
    }

    if (textFieldEmail.getText() == null || textFieldEmail.getText().trim().equals("")) {
      exception.addError("email", "Field can't be empty.");
    } else {
      entity.setEmail(textFieldEmail.getText());
    }

    if (exception.getErrors().size() > 0) {
      throw exception;
    }
  }

  @FXML
  public void onButtonDeleteAction() {

    Pokemon pokemon = tableViewPokemon.getSelectionModel().getSelectedItem();

    if (entity.getPokemons().size() == 1) {
      Alerts.showAlert(
          "Deletion Alert",
          null,
          "The trainer only has one pokemon. So you can't delete it.",
          Alert.AlertType.WARNING);
    } else {
      if (pokemon != null) {

        Optional<ButtonType> result =
            Alerts.showConfirmation(
                "Delete confirmation screen", "Are you sure you want to delete?");

        if (result.isPresent() && result.get() == ButtonType.OK) {
          entity.getPokemons().remove(pokemon);
          updatePokemonTableView();
        }
      }
    }
  }

  @FXML
  public void onButtonCancelAction(ActionEvent event) {
    Utils.getCurrentStage(event).close();
  }

  @FXML
  public void onButtonNewPokemonAction(ActionEvent event) {
    Stage parentStage = Utils.getCurrentStage(event);
    Pokemon pokemon = new Pokemon();
    // pokemon.setStatus(PokemonStatus.BURNT);
    createDialogForm(pokemon, "/main/java/views/PokemonFormView.fxml", parentStage);
  }

  private void createDialogForm(Pokemon pokemon, String absoluteName, Stage parentStage) {
    try {

      FXMLLoader loader = new FXMLLoader(getClass().getResource(absoluteName));
      Pane pane = loader.load();

      PokemonFormViewController controller = loader.getController();
      controller.setPokemon(pokemon);
      controller.setOwner(entity);
      controller.setUserController(UserController.getInstance());
      controller.updateFormData();
      controller.subscribeDataChangeListener(this);

      Stage dialogStage = new Stage();
      dialogStage.setTitle("Enter Pokemon Data");
      dialogStage.setScene(new Scene(pane));
      dialogStage.setResizable(false);
      dialogStage.initOwner(parentStage);
      dialogStage.initModality(Modality.WINDOW_MODAL);
      dialogStage.showAndWait();
    } catch (IOException ioException) {

      Alerts.showAlert(
          "IO Exception", "Error loding view", ioException.getMessage(), Alert.AlertType.ERROR);
    }
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
    tableColumnMaxLife.setCellValueFactory(new PropertyValueFactory<>("maxLife"));
    tableColumnType.setCellValueFactory(new PropertyValueFactory<>("type"));
    // end nodes
  }

  private void updatePokemonTableView() {

    ObservableList<Pokemon> pokemonObservableList =
        FXCollections.observableArrayList(entity.getPokemons());
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

    updatePokemonTableView();
  }

  private void setErrorMessages(Map<String, String> errors) {

    Set<String> fields = errors.keySet();

    if (fields.contains("name")) {
      labelErrorName.setText(errors.get("name"));
    } else {
      labelErrorName.setText("");
    }

    if (fields.contains("birthCity")) {
      labelErrorBirthCity.setText(errors.get("birthCity"));
    } else {
      labelErrorBirthCity.setText("");
    }

    if (fields.contains("gender")) {
      labelErrorGender.setText(errors.get("gender"));
    } else {
      labelErrorGender.setText("");
    }

    if (fields.contains("username")) {
      labelErrorUsername.setText(errors.get("username"));
    } else {
      labelErrorUsername.setText("");
    }

    if (fields.contains("password")) {
      labelErrorPassword.setText(errors.get("password"));
    } else {
      labelErrorPassword.setText("");
    }

    if (fields.contains("email")) {
      labelErrorEmail.setText(errors.get("email"));
    } else {
      labelErrorEmail.setText("");
    }
  }

  @Override
  public void onDataChanged() {
    updatePokemonTableView();
  }
}
