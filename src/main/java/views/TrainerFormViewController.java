package main.java.views;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
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
import main.java.views.listeners.DataChangeListener;
import main.java.views.util.Alerts;
import main.java.views.util.Constraints;
import main.java.views.util.Utils;

public class TrainerFormViewController implements Initializable, DataChangeListener {

  // end
  private Trainer entity;
  private UserController userController;
  private List<DataChangeListener> dataChangeListeners = new ArrayList<>();
  @FXML private TextField textFieldName;
  @FXML private TextField textFieldBirthCity;
  @FXML private TextField textFieldGender;
  @FXML private TextField textFieldUsername;
  @FXML private TextField textFieldPassword;
  @FXML private TextField textFieldEmail;
  @FXML private Button buttonSave;
  @FXML private Button buttonCancel;
  @FXML private Button buttonDeletePokemon;
  private TrainerListViewController trainerListViewController;

  // pokemon viewtable properties
  @FXML private TableView<Pokemon> tableViewPokemon;
  @FXML private TableColumn<Pokemon, String> tableColumnSpecies;
  @FXML private TableColumn<Pokemon, String> tableColumnMaxLife;
  @FXML private TableColumn<Pokemon, String> tableColumnType;

  public void setTrainer(Trainer entity) {
    this.entity = entity;
  }

  public void setUserController(UserController userController) {
    this.userController = userController;
  }

  public void setTrainerListViewController(TrainerListViewController trainerListViewController) {
    this.trainerListViewController = trainerListViewController;
  }

  public void subscribeDataChangeListener(DataChangeListener listener) {
    dataChangeListeners.add(listener);
  }

  @FXML
  public void onButtonSaveAction(ActionEvent event) {

    boolean isUpdate;

    if (entity == null) {
      throw new IllegalStateException("Entity was null.");
    }

    if (userController == null) {
      throw new IllegalStateException("userController was null.");
    }

    try {

      if (entity.getRegisterId() != 0) {
        isUpdate = true;
      } else {
        isUpdate = false;
      }

      if (!getFormData()) {
        if (isUpdate) {
          Alerts.showAlert("Error", null, "The fields must be filled to update", AlertType.ERROR);
        } else {
          Alerts.showAlert("Error", null, "The fields must be filled", AlertType.ERROR);
        }

      } else {
        if (entity.getPokemons().size() > 0) {
          getFormData();

          userController.saveOrUpdate(entity);
          notifyDataChangeListeners();
          goToTrainerListView();

          if (isUpdate) {
            Alerts.showAlert("", null, "Update completed", AlertType.INFORMATION);
          } else {
            Alerts.showAlert("", null, "Registration completed", AlertType.INFORMATION);
          }

          Utils.getCurrentStage(event).close();
        } else {
          Alerts.showAlert(
              "Error: invalid number of pokémons",
              null,
              "The trainer needs to have at least one pokémon.",
              Alert.AlertType.ERROR);
        }
      }

    } catch (UsernameOrEmailInUseException e) {

      Alerts.showAlert(
          "UsernameOrEmailInUseException", null, e.getMessage(), Alert.AlertType.ERROR);
    }
  }

  private void notifyDataChangeListeners() {
    for (DataChangeListener listener : dataChangeListeners) {
      listener.onDataChanged();
    }
  }

  private boolean getFormData() {

    boolean isValid = true;

    if (textFieldName.getText() == null || textFieldName.getText().trim().equals("")) {
      isValid = false;
    } else {
      entity.setName(textFieldName.getText());
    }

    if (textFieldBirthCity.getText() == null || textFieldBirthCity.getText().trim().equals("")) {
      isValid = false;
    } else {
      entity.setBirthCity(textFieldBirthCity.getText());
    }

    if (textFieldGender.getText() == null || textFieldGender.getText().trim().equals("")) {
      isValid = false;
    } else {
      entity.setGender(textFieldGender.getText());
    }

    if (textFieldUsername.getText() == null || textFieldUsername.getText().trim().equals("")) {
      isValid = false;
    } else {
      entity.setUsername(textFieldUsername.getText());
    }

    if (textFieldPassword.getText() == null || textFieldPassword.getText().trim().equals("")) {
      isValid = false;
    } else {
      entity.setPassword(textFieldPassword.getText());
    }

    if (textFieldEmail.getText() == null || textFieldEmail.getText().trim().equals("")) {
      isValid = false;
    } else {
      entity.setEmail(textFieldEmail.getText());
    }
    return isValid;
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

    goToTrainerListView();
    Utils.getCurrentStage(event).close();
  }

  @FXML
  public void onButtonNewPokemonAction(ActionEvent event) {
    if (entity.getPokemons().size() < 6) {
      Stage parentStage = Utils.getCurrentStage(event);
      Pokemon pokemon = new Pokemon();
      createDialogForm(pokemon, "/main/java/views/PokemonFormView.fxml", parentStage);
    } else {
      Alerts.showAlert(
          "Alert", null, "The trainer already has 6 pokemons", Alert.AlertType.WARNING);
    }
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

    textFieldName.setText(entity.getName());
    textFieldBirthCity.setText(entity.getBirthCity());
    textFieldGender.setText(entity.getGender());
    textFieldUsername.setText(entity.getUsername());
    textFieldPassword.setText(entity.getPassword());
    textFieldEmail.setText(entity.getEmail());

    updatePokemonTableView();
  }

  @Override
  public void onDataChanged() {
    updatePokemonTableView();
  }

  public void fillFields() {

    if (entity == null) {
      throw new IllegalStateException("Trainer was null.");
    }

    textFieldName.setText(entity.getName());
    textFieldBirthCity.setText(entity.getBirthCity());
    textFieldGender.setText(entity.getGender());
    textFieldUsername.setText(entity.getUsername());
    textFieldPassword.setText(entity.getPassword());
    textFieldEmail.setText(entity.getEmail());
  }

  public void goToTrainerListView() {

    try {

      Parent newPage =
          FXMLLoader.load(getClass().getResource("/main/java/views/TrainerListView.fxml"));
      Stage stage = new Stage();

      stage.setScene(new Scene(newPage, 600, 400));
      stage.setTitle("PokéCenter");
      stage.setResizable(false);
      stage.show();

    } catch (Exception e) {
      System.out.println("Error");
    }
  }
}
