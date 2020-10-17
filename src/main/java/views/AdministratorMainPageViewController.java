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

public class AdministratorMainPageViewController implements Initializable {

  @FXML private Button backBt;
  @FXML private Button startShoppingBt;
  @FXML private Button shoppingHistoryBt;
  @FXML private Button salesClerkBt;
  @FXML private Button cureServiceBt;
  @FXML private ComboBox<Trainer> trainersCb;
  @FXML private Hyperlink editOwnRegisterHl;
  @FXML private Hyperlink managerTrainersHl;
  @FXML private Hyperlink manageProductsHl;
  @FXML private Hyperlink manageEmployeeHl;
  @FXML private Text welcomeTxt;

  private User administrator;
  private Trainer selectedTrainer;
  private ObservableList<Trainer> trainersObsList;

  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {

    administrator = UserController.getInstance().getLoggedUser();

    List<Trainer> trainers = UserController.getInstance().getAllTrainers();

    if (administrator != null) {
      initializeWelcomeTxt();
    }
    initializeTrainersListCb(trainers);

    cureServiceBt.setDisable(true);
    startShoppingBt.setDisable(true);
    shoppingHistoryBt.setDisable(true);
  }

  // UI events

  public void onBackBtAction(ActionEvent event) {

    UserController.getInstance().Logout();

    try {

      Parent newPage = FXMLLoader.load(getClass().getResource("/main/java/views/Login.fxml"));
      backBt.getScene().setRoot(newPage);
    } catch (Exception e) {
      System.out.println("Error");
    }
  }

  public void onSalesClerkBtAction(ActionEvent event) {

    try {

      Parent newPage =
          FXMLLoader.load(getClass().getResource("/main/java/views/SalesClerkMainPage.fxml"));
      backBt.getScene().setRoot(newPage);
    } catch (Exception e) {
      System.out.println("Error");
    }
  }

  public void onCureServiceBtAction(ActionEvent event) {

    System.out.println("Going to Cure Service View");
  }

  public void onStartShoppingBtAction(ActionEvent event) {

    if (selectedTrainer == null) {
      Alerts.showAlert(
          "Error", null, "A trainer must be selected to begin shopping!", AlertType.ERROR);
    } else {
      SalesService.getInstance().startOrder(administrator, selectedTrainer);
    }
  }

  public void onShoppingHistoryBtAction(ActionEvent event) {
    if (selectedTrainer == null) {
      System.out.println("Error, trainer is null");
    } else {
      System.out.println(selectedTrainer.getName() + " shopping history");
    }
  }

  public void onTrainersCbAction(ActionEvent event) {

    selectedTrainer = trainersCb.getValue();

    if (selectedTrainer != null) {

      System.out.println("Selected " + selectedTrainer.getName());

      startShoppingBt.setDisable(false);
      shoppingHistoryBt.setDisable(false);
      cureServiceBt.setDisable(false);
    }
  }

  public void onEditOwnRegisterHlAction(ActionEvent event) {
    System.out.println("Editing own register");
  }

  public void onManagerTrainersHlAction(ActionEvent event) {
    System.out.println("Going to Trainers CRUD");
  }

  public void onManageProductsHlAction(ActionEvent event) {

    try {

      Parent newPage =
          FXMLLoader.load(getClass().getResource("/main/java/views/ProductListView.fxml"));
      manageProductsHl.getScene().setRoot(newPage);
    } catch (Exception e) {
      System.out.println("Error");
    }
  }

  public void onManageEmployeesHlAction(ActionEvent event) {

    try {

      Parent newPage =
          FXMLLoader.load(getClass().getResource("/main/java/views/EmployeeListView.fxml"));
      manageEmployeeHl.getScene().setRoot(newPage);
    } catch (Exception e) {
      System.out.println("Error");
    }
  }

  // Initialization methods

  private void initializeWelcomeTxt() {

    String newText = welcomeTxt.getText();

    newText = newText.replace("{name}", administrator.getName());

    welcomeTxt.setText(newText);
  }

  private void initializeTrainersListCb(List<Trainer> trainers) {

    trainersObsList = FXCollections.observableArrayList(trainers);
    trainersCb.setItems(trainersObsList);

    trainersCb.setCellFactory(this::displayTrainer);
    trainersCb.setButtonCell(trainersCb.getCellFactory().call(null));
  }

  private ListCell<Trainer> displayTrainer(ListView<Trainer> view) {

    return new ListCell<>() {

      @Override
      protected void updateItem(Trainer trainer, boolean empty) {

        super.updateItem(trainer, empty);

        if (empty) {
          setText("");
        } else {
          setText(trainer.getName() + " from " + trainer.getBirthCity());
        }
      }
    };
  }
}
