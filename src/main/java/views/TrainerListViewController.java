package main.java.views;

import java.io.IOException;
import java.net.URL;
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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import main.java.controllers.UserController;
import main.java.models.Nurse;
import main.java.models.SalesClerk;
import main.java.models.Trainer;
import main.java.models.User;
import main.java.views.listeners.DataChangeListener;
import main.java.views.util.Alerts;
import main.java.views.util.Utils;

public class TrainerListViewController implements Initializable, DataChangeListener {

  @FXML private Button buttonNewTrainer;
  @FXML private Button buttonBack;
  @FXML private Button buttonUpdate;
  @FXML private Button buttonDelete;
  @FXML private TableView<Trainer> tableViewTrainer;
  @FXML private TableColumn<Trainer, String> tableColumnRegisterId;
  @FXML private TableColumn<Trainer, String> tableColumnName;
  @FXML private TableColumn<Trainer, Trainer> tableColumnEDIT;
  @FXML private TableColumn<Trainer, Trainer> tableColumnREMOVE;
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

  protected void updateTableView() {

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
  public void onButtonBackAction() {

    User lu = userController.getLoggedUser();

    if (lu instanceof SalesClerk) {

      try {

        Parent newPage =
            FXMLLoader.load(getClass().getResource("/main/java/views/SalesClerkMainPage.fxml"));
        buttonBack.getScene().setRoot(newPage);
      } catch (Exception e) {
        System.out.println("Error");
      }
    } else if (lu instanceof Nurse) {

      try {

        Parent newPage =
            FXMLLoader.load(getClass().getResource("/main/java/views/NurseMainPage.fxml"));
        buttonBack.getScene().setRoot(newPage);
      } catch (Exception e) {
        System.out.println("Error");
      }
    } else {

      try {

        Parent newPage =
            FXMLLoader.load(getClass().getResource("/main/java/views/AdministratorMainPage.fxml"));
        buttonBack.getScene().setRoot(newPage);
      } catch (Exception e) {
        System.out.println("Error");
      }
    }
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
      controller.setTrainerListViewController(this);

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

  public void onButtonDeleteTrainer() {

    try {

      if (tableViewTrainer.getSelectionModel().getSelectedItem().getRegisterId() != 0) {

        Optional<ButtonType> result =
            Alerts.showConfirmation(
                "Confirmation", "Are you sure you want to delete the selected field ?");
        if (result.isPresent() && result.get() == ButtonType.OK) {

          UserController.getInstance()
              .removeUser(tableViewTrainer.getSelectionModel().getSelectedItem());
          updateTableView();
        }
      }
    } catch (Exception e) {
      Alerts.showAlert("Error", null, "Select the field to be deleted", AlertType.ERROR);
    }
  }

  public void onUpdateButton(ActionEvent event) {

    try {

      if (!tableViewTrainer.getSelectionModel().isEmpty()) {

        try {

          FXMLLoader loader =
              new FXMLLoader(getClass().getResource("/main/java/views/TrainerFormView.fxml"));
          Parent newPage = loader.load();
          TrainerFormViewController controller = loader.getController();
          controller.setTrainer(tableViewTrainer.getSelectionModel().getSelectedItem());
          controller.fillFields();
          Stage parentStage = Utils.getCurrentStage(event);
          Utils.getCurrentStage(event).close();
          createDialogForm(
              tableViewTrainer.getSelectionModel().getSelectedItem(),
              "/main/java/views/TrainerFormView.fxml",
              parentStage);

          //  buttonUpdate.getScene().setRoot(newPage);
        } catch (Exception e) {
          e.printStackTrace();
        }
      } else {
        throw new IOException("Select the field to be updated");
      }
    } catch (IOException e) {
      Alerts.showAlert("Error", null, e.getMessage(), AlertType.ERROR);
    }
  }
}
