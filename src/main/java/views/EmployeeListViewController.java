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
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import main.java.controllers.UserController;
import main.java.models.User;
import main.java.views.util.Alerts;

public class EmployeeListViewController implements Initializable {

  @FXML private Button buttonNewEmployee;
  @FXML private Button buttonUpdate;
  @FXML private Button buttonDelete;
  @FXML private Button buttonBack;
  @FXML private TableView<User> tableViewEmployee;
  @FXML private TableColumn<User, String> tableColumnName;
  @FXML private TableColumn<User, String> tableColumnBirthCity;
  @FXML private TableColumn<User, String> tableColumnGender;
  @FXML private TableColumn<User, String> tableColumnUsername;
  @FXML private TableColumn<User, String> tableColumnEmail;
  private List<User> employeeList;
  private ObservableList<User> EmployeeObservableList;
  private UserController userController;

  public void setUserController(UserController userController) {
    this.userController = userController;
  }

  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {

    initializeNodes();
    setUserController(UserController.getInstance());
    updateTableView();
  }

  private void initializeNodes() {

    tableColumnName.setCellValueFactory(new PropertyValueFactory<>("name"));
    tableColumnBirthCity.setCellValueFactory(new PropertyValueFactory<>("birthCity"));
    tableColumnGender.setCellValueFactory(new PropertyValueFactory<>("gender"));
    tableColumnUsername.setCellValueFactory(new PropertyValueFactory<>("username"));
    tableColumnEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
  }

  private void updateTableView() {

    if (userController == null) {
      throw new IllegalStateException("userController was null");
    }

    employeeList = userController.getAllEmployees();

    EmployeeObservableList = FXCollections.observableArrayList(employeeList);
    tableViewEmployee.setItems(EmployeeObservableList);
  }

  @FXML
  public void onButtonNewEmployeeAction(ActionEvent event) {
    try {

      Parent newPage =
          FXMLLoader.load(getClass().getResource("/main/java/views/EmployeeFormView.fxml"));
      buttonNewEmployee.getScene().setRoot(newPage);
    } catch (Exception e) {
      System.out.println("Error");
    }
  }

  @FXML
  public void onButtonUpdateAction() {

    try {

      if (!tableViewEmployee.getSelectionModel().isEmpty()) {

        //        UserController.getInstance()
        //            .setIdForUpdate(
        //                tableViewEmployee.getSelectionModel().getSelectedItem().getRegisterId());
        //        UserController.getInstance().setUpdate(true);
        try {

          FXMLLoader loader =
              new FXMLLoader(getClass().getResource("/main/java/views/EmployeeFormView.fxml"));
          Parent newPage = loader.load();
          EmployeeFormViewController controller = loader.getController();
          controller.setProduct(tableViewEmployee.getSelectionModel().getSelectedItem());
          controller.fillFields();

          buttonNewEmployee.getScene().setRoot(newPage);
        } catch (Exception e) {
          System.out.println("Error");
        }
      } else {
        throw new IOException("Select the field to be updated");
      }
    } catch (IOException e) {
      Alerts.showAlert("Error", null, e.getMessage(), AlertType.ERROR);
    }
  }

  @FXML
  public void onButtonDeleteAction() {

    try {

      if (tableViewEmployee.getSelectionModel().getSelectedItem().getRegisterId() != 0) {

        Optional<ButtonType> result =
            Alerts.showConfirmation(
                "Confirmation", "Are you sure you want to delete the selected field ?");
        if (result.isPresent() && result.get() == ButtonType.OK) {

          UserController.getInstance()
              .removeUser(tableViewEmployee.getSelectionModel().getSelectedItem());
          updateTableView();
        }
      }
    } catch (Exception e) {
      Alerts.showAlert("Error", null, "Select the field to be deleted", AlertType.ERROR);
    }
  }

  @FXML
  public void onButtonBackAction() {

    try {

      Parent newPage =
          FXMLLoader.load(getClass().getResource("/main/java/views/AdministratorMainPage.fxml"));
      tableViewEmployee.getScene().setRoot(newPage);
    } catch (Exception e) {
      System.out.println("Error");
    }
  }
}
