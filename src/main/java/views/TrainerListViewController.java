package main.java.views;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import main.java.controllers.UserController;
import main.java.models.Trainer;
import main.java.views.listeners.DataChangeListener;
import main.java.views.util.Alerts;
import main.java.views.util.Utils;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class TrainerListViewController implements Initializable, DataChangeListener {

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
  // @FXML private TableColumn<Trainer, String> tableColumnPassword;
  @FXML private TableColumn<Trainer, String> tableColumnEmail;

  private List<Trainer> trainerList;
  private ObservableList<Trainer> trainerObservableList;
  private UserController userController;

  public void setUserController(UserController userController) {
    this.userController = userController;
  }

  @Override
  public void initialize(URL location, ResourceBundle resources) {

    initializeNodes();
    setUserController(UserController.getInstance());
    updateTableView();
  }

  private void initializeNodes() {

    tableColumnRegisterId.setCellValueFactory(new PropertyValueFactory<>("registerId"));
    tableColumnName.setCellValueFactory(new PropertyValueFactory<>("name"));
    tableColumnBirthCity.setCellValueFactory(new PropertyValueFactory<>("birthCity"));
    tableColumnGender.setCellValueFactory(new PropertyValueFactory<>("gender"));
    tableColumnUsername.setCellValueFactory(new PropertyValueFactory<>("username"));
    // tableColumnPassword.setCellValueFactory(new PropertyValueFactory<>("password"));
    tableColumnEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
  }

  private void updateTableView() {

    if (userController == null) {
      throw new IllegalStateException("userController was null");
    }

    trainerList = userController.getAllTrainers();

    trainerObservableList = FXCollections.observableArrayList(trainerList);
    tableViewTrainer.setItems(trainerObservableList);
  }

  @FXML
  public void onButtonNewTrainerAction(ActionEvent event) {
    Stage parentStage = Utils.getCurrentStage(event);
    Trainer trainer = new Trainer();
    createDialogForm(trainer, "/main/java/views/TrainerFormView.fxml", parentStage);
  }

  @FXML
  public void onButtonUpdateAction() {
    System.out.println("onButtonUpdate");
  }

  @FXML
  public void onButtonDeleteAction() {
    System.out.println("onButtonDelete");
  }

  @FXML
  public void onButtonBackAction() {
    System.out.println("onButtonBack");
  }

  private void createDialogForm(Trainer trainer, String absoluteName, Stage parentStage) {
    try {

      FXMLLoader loader = new FXMLLoader(getClass().getResource(absoluteName));
      Pane pane = loader.load();

      TrainerFormViewController controller = loader.getController();
      controller.setTrainer(trainer);
      controller.setUserController(UserController.getInstance());
      controller.updateFormData();
      controller.subscribeDataChangeListener(this);

      Stage dialogStage = new Stage();
      dialogStage.setTitle("Enter Trainer Data");
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
  public void onDataChanged() {
    updateTableView();
  }
}
