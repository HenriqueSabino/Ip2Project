package main.java.views;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import main.java.controllers.UserController;
import main.java.models.Administrator;
import main.java.models.CureReport;
import main.java.models.Nurse;
import main.java.models.Trainer;
import main.java.models.User;
import main.java.services.CureService;

public class CureReportViewController implements Initializable {

  @FXML private Button backBt;
  @FXML private DatePicker startDateDp;
  @FXML private DatePicker endDateDp;
  @FXML private ComboBox<Trainer> selectTrainerCb;
  @FXML private ComboBox<User> selectEmployeeCb;
  @FXML private Button generateReportBt;
  @FXML private TextArea cureReportTxt;

  private ObservableList<Trainer> trainersObsList;
  private ObservableList<User> employeeObsList;
  private CureReport cureReport;

  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {

    cureReport = new CureReport();

    initializeTrainerListCb();
    initializeEmployeeListCb();

    if (!(UserController.getInstance().getLoggedUser() instanceof Trainer)) {

      selectEmployeeCb.setValue(UserController.getInstance().getLoggedUser());
    } else {

      selectTrainerCb.setValue((Trainer) UserController.getInstance().getLoggedUser());
      selectTrainerCb.setDisable(true);
    }

    if (CureService.getInstance().getCures().size() > 0) {
      cureReportTxt.setText(cureReport.generateReport(CureService.getInstance().getCures()));
      CureService.getInstance().finishCures();
    }
  }

  public void onBackBtAction(ActionEvent event) {

    if (UserController.getInstance().getLoggedUser() instanceof Nurse) {
      try {

        Parent newPage =
            FXMLLoader.load(getClass().getResource("/main/java/views/NurseMainPage.fxml"));
        backBt.getScene().setRoot(newPage);
      } catch (Exception e) {
        System.out.println("Error");
      }
    }

    if (UserController.getInstance().getLoggedUser() instanceof Administrator) {
      try {

        Parent newPage =
            FXMLLoader.load(getClass().getResource("/main/java/views/AdministratorMainPage.fxml"));
        backBt.getScene().setRoot(newPage);
      } catch (Exception e) {
        System.out.println("Error");
      }
    }

    if (UserController.getInstance().getLoggedUser() instanceof Trainer) {
      try {

        Parent newPage =
            FXMLLoader.load(getClass().getResource("/main/java/views/TrainerMainPage.fxml"));
        backBt.getScene().setRoot(newPage);
      } catch (Exception e) {
        System.out.println("Error");
      }
    }
  }

  public void onStartDateDpAction(ActionEvent event) {
    cureReport.setStartDate(startDateDp.getValue());
  }

  public void onEndDateDpAction(ActionEvent event) {
    cureReport.setEndDate(endDateDp.getValue());
  }

  public void onSelectedTrainerCbAction(ActionEvent event) {
    cureReport.setClient(selectTrainerCb.getValue());
  }

  public void onSelectedEmployeeCbAction(ActionEvent event) {
    cureReport.setEmployee(selectEmployeeCb.getValue());
  }

  public void onGenerateReportBtAction(ActionEvent event) {
    cureReportTxt.setText(CureService.getInstance().getReport(cureReport));
  }

  private void initializeTrainerListCb() {

    trainersObsList =
        FXCollections.observableArrayList(UserController.getInstance().getAllTrainers());
    trainersObsList.add(0, null);
    selectTrainerCb.setItems(trainersObsList);

    selectTrainerCb.setCellFactory(this::displayTrainerCell);
    selectTrainerCb.setButtonCell(displayTrainerSelection());
  }

  private void initializeEmployeeListCb() {

    employeeObsList =
        FXCollections.observableArrayList(UserController.getInstance().getAllNurses());
    employeeObsList.addAll(UserController.getInstance().getAllAdministrators());
    selectEmployeeCb.setItems(employeeObsList);

    selectEmployeeCb.setCellFactory(this::displayUser);
    selectEmployeeCb.setButtonCell(selectEmployeeCb.getCellFactory().call(null));
  }

  private ListCell<User> displayUser(ListView<User> view) {

    return new ListCell<>() {

      @Override
      protected void updateItem(User user, boolean empty) {

        super.updateItem(user, empty);

        if (empty) {
          setText("");
        } else {
          setText(user.getName() + " from " + user.getBirthCity());
        }
      }
    };
  }

  private ListCell<Trainer> displayTrainerCell(ListView<Trainer> view) {

    return new ListCell<>() {

      @Override
      protected void updateItem(Trainer trainer, boolean empty) {

        super.updateItem(trainer, empty);

        if (empty || trainer == null) {
          setText("None");
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
          setText(selectTrainerCb.getPromptText());
        } else {
          setText(trainer.getName() + " from " + trainer.getBirthCity());
        }
      }
    };
  }

  public void updateTrainerCb() {
    selectTrainerCb.setValue(cureReport.getClient());
  }

  public CureReport getCureReport() {
    return cureReport;
  }
}
