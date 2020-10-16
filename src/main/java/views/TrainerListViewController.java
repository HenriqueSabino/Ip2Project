package main.java.views;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import main.java.controllers.TmpUserController;
import main.java.models.Trainer;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class TrainerListViewController implements Initializable {

  @FXML private Button buttonNewTrainer;
  @FXML private Button buttonUpdate;
  @FXML private Button buttonDelete;
  @FXML private Button buttonBack;
  @FXML private TableView<Trainer> tableViewTrainer;
  @FXML private TableColumn<Trainer, String> tableColumnRegisterId;
  @FXML private TableColumn<Trainer, String> tableColumnName;
  @FXML private TableColumn<Trainer, String> tableColumnBirthCity;
  @FXML private TableColumn<Trainer, String> tableColumnGender;
  @FXML private TableColumn<Trainer, String> tableColumnUsername;
  @FXML private TableColumn<Trainer, String> tableColumnPassword;
  @FXML private TableColumn<Trainer, String> tableColumnEmail;

  private List<Trainer> trainerList;
  private ObservableList<Trainer> trainerObservableList;
  private TmpUserController userController;

  public void setUserController(TmpUserController userController) {
    this.userController = userController;
  }

  @Override
  public void initialize(URL location, ResourceBundle resources) {

    initializeNodes();
    setUserController(TmpUserController.getInstance());
    updateTableView();
  }

  private void initializeNodes() {

    tableColumnRegisterId.setCellValueFactory(new PropertyValueFactory<>("registerId"));
    tableColumnName.setCellValueFactory(new PropertyValueFactory<>("name"));
    tableColumnBirthCity.setCellValueFactory(new PropertyValueFactory<>("birthCity"));
    tableColumnGender.setCellValueFactory(new PropertyValueFactory<>("gender"));
    tableColumnUsername.setCellValueFactory(new PropertyValueFactory<>("username"));
    tableColumnPassword.setCellValueFactory(new PropertyValueFactory<>("password"));
    tableColumnEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
  }

  private void updateTableView() {

    if (userController == null) {
      throw new IllegalStateException("userController was null");
    }

    List<Trainer> trainerList = userController.findAllTrainers();

    trainerObservableList = FXCollections.observableArrayList(trainerList);
    tableViewTrainer.setItems(trainerObservableList);
  }

  public void onButtonNewTrainer() {
    System.out.println("A new trainer was created.");
  }

  public void onButtonUpdate() {
    System.out.println("onButtonUpdate");
  }

  public void onButtonDelete() {
    System.out.println("onButtonDelete");
  }

  public void onButtonBack() {
    System.out.println("onButtonBack");
  }
}
