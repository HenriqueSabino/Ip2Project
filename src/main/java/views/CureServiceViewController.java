package main.java.views;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import main.java.controllers.UserController;
import main.java.models.Administrator;
import main.java.models.Nurse;
import main.java.models.Pokemon;
import main.java.models.PokemonStatus;
import main.java.models.Trainer;
import main.java.models.User;
import main.java.services.CureService;
import main.java.views.util.Alerts;
import main.java.views.util.Constraints;

public class CureServiceViewController implements Initializable {

  @FXML private Button backBt;
  @FXML private Text trainerTxt;
  @FXML private ComboBox<Pokemon> pokemonCb;
  @FXML private TextField specieField;
  @FXML private TextField typeField;
  @FXML private TextField maxLifeField;
  @FXML private TextField lifeField;
  @FXML private ComboBox<PokemonStatus> pokemonStatusCb;
  @FXML private Button generateRegister;

  private User employee;
  private Trainer selectedTrainer;
  private Pokemon selectedPokemon;
  private Pokemon previousPokemon;
  private PokemonStatus selectedPokemonStatus;
  private ObservableList<Pokemon> pokemonsObsList;
  private ObservableList<PokemonStatus> pokemonStatusObsList;

  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {

    Constraints.setTextFieldInteger(lifeField);

    employee = UserController.getInstance().getLoggedUser();
    initializePokemonsStatusListCb();

    lifeField.setVisible(false);
    maxLifeField.setVisible(false);
    specieField.setVisible(false);
    typeField.setVisible(false);
    pokemonStatusCb.setVisible(false);
    generateRegister.setVisible(false);

    lifeField.setDisable(true);
    maxLifeField.setDisable(true);
    specieField.setDisable(true);
    typeField.setDisable(true);
    pokemonStatusCb.setDisable(true);
    generateRegister.setDisable(true);
  }

  public void loadTrainerInfo() {

    List<Pokemon> pokemons = selectedTrainer.getPokemons();

    initializeTrainerTxt();
    initializePokemonsListCb(pokemons);
  }

  // UI events
  public void onBackBtAction(ActionEvent event) {

    if (employee instanceof Nurse) {
      try {

        Parent newPage =
            FXMLLoader.load(getClass().getResource("/main/java/views/NurseMainPage.fxml"));
        backBt.getScene().setRoot(newPage);
      } catch (Exception e) {
        System.out.println("Error");
      }
    }

    if (employee instanceof Administrator) {
      try {

        Parent newPage =
            FXMLLoader.load(getClass().getResource("/main/java/views/NurseMainPage.fxml"));
        backBt.getScene().setRoot(newPage);
      } catch (Exception e) {
        System.out.println("Error");
      }
    }
  }

  public void onGenerateRegisterBtAction(ActionEvent event) {
    CureService.getInstance().startCures(selectedTrainer, employee);

    try {

      FXMLLoader loader =
          new FXMLLoader(getClass().getResource("/main/java/views/CureReportView.fxml"));
      Parent newPage = loader.load();
      CureReportViewController controller = loader.getController();
      controller.getCureReport().setClient(selectedTrainer);
      controller.updateTrainerCb();
      backBt.getScene().setRoot(newPage);
    } catch (Exception e) {
      System.out.println("Error");
    }
  }

  public void onPokemonCbAction(ActionEvent event) {

    if (selectedPokemon == null) {

      selectedPokemon = pokemonCb.getValue();

      initializeMaxLifeTxt();
      initializeSpecieTxt();
      initializeTypeTxt();
      initializeLifeTxt();
      initializeStatus();

      System.out.println("Selected " + selectedPokemon.getSpecies());

      lifeField.setVisible(true);
      maxLifeField.setVisible(true);
      specieField.setVisible(true);
      typeField.setVisible(true);
      pokemonStatusCb.setVisible(true);
      generateRegister.setVisible(true);

      lifeField.setDisable(false);
      maxLifeField.setDisable(false);
      specieField.setDisable(false);
      typeField.setDisable(false);
      pokemonStatusCb.setDisable(false);
      generateRegister.setDisable(false);

    } else if (selectedPokemon != pokemonCb.getValue()) {

      if (!lifeField.getText().isEmpty()
          && Integer.parseInt(lifeField.getText()) <= selectedPokemon.getMaxLife()) {

        int pokemonLife = Integer.parseInt(lifeField.getText());

        if (selectedPokemon.getLife() != pokemonLife
            || selectedPokemon.getStatus() != selectedPokemonStatus) {

          selectedPokemon.setLife(pokemonLife);

          selectedPokemon.setStatus(selectedPokemonStatus);

          Alerts.showAlert(
              "Success!",
              null,
              selectedPokemon.getSpecies() + "'s life and status were altered.",
              AlertType.INFORMATION);
        }

        selectedPokemon = pokemonCb.getValue();

        initializeMaxLifeTxt();
        initializeSpecieTxt();
        initializeTypeTxt();
        initializeLifeTxt();
        initializeStatus();

        System.out.println("Selected " + selectedPokemon.getSpecies());
      } else {

        if (lifeField.getText().isEmpty()) {
          Alerts.showAlert("Error", null, "Life field can't be empty", AlertType.WARNING);
        } else {

          Alerts.showAlert(
              "Error",
              null,
              "Pokemon's life must be lower than pokemon's max. life.",
              AlertType.WARNING);
        }

        lifeField.setText(String.valueOf(selectedPokemon.getLife()));
        pokemonCb.setValue(selectedPokemon);
      }
    }
    System.out.println(
        "Life: " + selectedPokemon.getLife() + " Status: " + selectedPokemon.getStatus());
  }

  public void onPokemonStatusCbAction(ActionEvent event) {
    selectedPokemonStatus = pokemonStatusCb.getValue();
  }

  private void initializeTrainerTxt() {

    String newText = trainerTxt.getText();

    newText = newText.replace("{name}", selectedTrainer.getName());
    newText = newText.replace("{city}", selectedTrainer.getBirthCity());

    trainerTxt.setText(newText);
  }

  private void initializeSpecieTxt() {
    if (selectedPokemon != null) {
      specieField.setText(selectedPokemon.getSpecies());
    }
  }

  private void initializeTypeTxt() {
    if (selectedPokemon != null) {
      typeField.setText(selectedPokemon.getType().name());
    }
  }

  private void initializeMaxLifeTxt() {
    if (selectedPokemon != null) {
      maxLifeField.setText(String.valueOf(selectedPokemon.getMaxLife()));
    }
  }

  private void initializeLifeTxt() {
    if (selectedPokemon != null) {
      lifeField.setText(String.valueOf(selectedPokemon.getLife()));
    }
  }

  private void initializeStatus() {
    if (selectedPokemon != null) {
      pokemonStatusCb.setValue(selectedPokemon.getStatus());
    }
  }

  private void initializePokemonsListCb(List<Pokemon> pokemons) {

    pokemonsObsList = FXCollections.observableArrayList(pokemons);
    pokemonCb.setItems(pokemonsObsList);

    pokemonCb.setCellFactory(this::displayPokemon);
    pokemonCb.setButtonCell(pokemonCb.getCellFactory().call(null));
  }

  private void initializePokemonsStatusListCb() {

    pokemonStatusObsList = FXCollections.observableArrayList(PokemonStatus.values());
    pokemonStatusCb.setItems(pokemonStatusObsList);

    pokemonStatusCb.setCellFactory(this::displayPokemonStatus);
    pokemonStatusCb.setButtonCell(pokemonStatusCb.getCellFactory().call(null));
  }

  private ListCell<Pokemon> displayPokemon(ListView<Pokemon> view) {

    return new ListCell<>() {

      @Override
      protected void updateItem(Pokemon pokemon, boolean empty) {

        super.updateItem(pokemon, empty);

        if (empty) {
          setText("");
        } else {
          setText(pokemon.getSpecies());
        }
      }
    };
  }

  private ListCell<PokemonStatus> displayPokemonStatus(ListView<PokemonStatus> view) {

    return new ListCell<>() {

      @Override
      protected void updateItem(PokemonStatus pokemonStatus, boolean empty) {

        super.updateItem(pokemonStatus, empty);

        if (empty) {
          setText("");
        } else {
          setText(pokemonStatus.name());
        }
      }
    };
  }

  public void setSelectedTrainer(Trainer selectedTrainer) {
    this.selectedTrainer = selectedTrainer;
  }
}
