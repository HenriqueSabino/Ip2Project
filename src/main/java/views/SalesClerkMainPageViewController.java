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
import javafx.scene.control.Hyperlink;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.text.Text;
import main.java.controllers.UserController;
import main.java.models.Trainer;
import main.java.models.User;
import main.java.services.SalesService;
import main.java.views.util.Alerts;

public class SalesClerkMainPageViewController implements Initializable {

  @FXML private Button backBt;
  @FXML private Button startShoppingBt;
  @FXML private Button shoppingHistoryBt;
  @FXML private ComboBox<Trainer> trainersCb;
  @FXML private Hyperlink editOwnRegisterHl;
  @FXML private Hyperlink managerTrainersHl;
  @FXML private Hyperlink manageProductsHl;
  @FXML private Text welcomeTxt;

  private User employee;
  private Trainer selectedTrainer;
  private ObservableList<Trainer> trainersObsList;

  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {

    employee = UserController.getInstance().getLoggedUser();

    List<Trainer> trainers = UserController.getInstance().getAllTrainers();
    if (employee != null) {
      initializeWelcomeTxt();
    }

    initializeTrainersListCb(trainers);

    startShoppingBt.setDisable(true);
    shoppingHistoryBt.setDisable(true);
  }

  // UI events

  public void onBackBtAction(ActionEvent event) {

    UserController.getInstance().Logout();

    try {

      Parent newPage = FXMLLoader.load(getClass().getResource("/main/java/views/Login.fxml"));
      managerTrainersHl.getScene().setRoot(newPage);
    } catch (Exception e) {
      System.out.println("Error");
    }
  }

  public void onStartShoppingBtAction(ActionEvent event) {

    if (selectedTrainer == null) {
      Alerts.showAlert(
          "Error", null, "A trainer must be selected to begin shopping!", AlertType.ERROR);
    } else {
      SalesService.getInstance().startOrder(employee, selectedTrainer);

      try {

        Parent newPage =
            FXMLLoader.load(getClass().getResource("/main/java/views/ShoppingCartView.fxml"));
        managerTrainersHl.getScene().setRoot(newPage);
      } catch (Exception e) {
        System.out.println("Error");
      }
    }
  }

  public void onShoppingHistoryBtAction(ActionEvent event) {
    if (selectedTrainer == null) {
      System.out.println("Error, trainer is null");
    } else {
      try {

        FXMLLoader loader =
            new FXMLLoader(getClass().getResource("/main/java/views/OrderReportView.fxml"));
        Parent newPage = loader.load();
        OrderReportViewController controller = loader.getController();
        controller.getOrderReport().setClient(selectedTrainer);
        controller.updateTrainerCb();
        backBt.getScene().setRoot(newPage);
      } catch (Exception e) {
        System.out.println("Error");
      }
    }
  }

  public void onTrainersCbAction(ActionEvent event) {

    selectedTrainer = trainersCb.getValue();

    if (selectedTrainer != null) {

      System.out.println("Selected " + selectedTrainer.getName());

      startShoppingBt.setDisable(false);
      shoppingHistoryBt.setDisable(false);
    } else {
      trainersCb.getSelectionModel().clearSelection();
      startShoppingBt.setDisable(true);
      shoppingHistoryBt.setDisable(true);
    }
  }

  public void onEditOwnRegisterHlAction(ActionEvent event) {
    System.out.println("Editing own register");
  }

  public void onManagerTrainersHlAction(ActionEvent event) {

    try {

      Parent newPage =
          FXMLLoader.load(getClass().getResource("/main/java/views/TrainerListView.fxml"));
      backBt.getScene().setRoot(newPage);
    } catch (Exception e) {
      System.out.println("Error");
    }
  }

  public void onManageProductsHlAction(ActionEvent event) {
    System.out.println("Going to Products CRUD");
  }

  // Initialization methods

  private void initializeWelcomeTxt() {

    String newText = welcomeTxt.getText();

    newText = newText.replace("{name}", employee.getName());
    newText = newText.replace("{city}", employee.getBirthCity());

    welcomeTxt.setText(newText);
  }

  private void initializeTrainersListCb(List<Trainer> trainers) {

    trainersObsList = FXCollections.observableArrayList(trainers);
    trainersObsList.add(0, null);
    trainersCb.setItems(trainersObsList);

    trainersCb.setCellFactory(this::displayTrainerCell);
    trainersCb.setButtonCell(displayTrainerSelection());
  }

  private ListCell<Trainer> displayTrainerCell(ListView<Trainer> view) {

    return new ListCell<>() {

      @Override
      protected void updateItem(Trainer trainer, boolean empty) {

        super.updateItem(trainer, empty);

        if (empty || trainer == null) {
          setText("");
        } else {
          setText(trainer.getName() + " from " + trainer.getBirthCity());
        }
      }
    };
  }

  private ListCell<Trainer> displayTrainerSelection() {

    return new ListCell<>() {

      @Override
      protected void updateItem(Trainer trainer, boolean empty) {

        super.updateItem(trainer, empty);

        if (empty || trainer == null) {
          setText(trainersCb.getPromptText());
        } else {
          setText(trainer.getName() + " from " + trainer.getBirthCity());
        }
      }
    };
  }
}
