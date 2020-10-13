package main.java.views;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
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
import javafx.scene.control.Hyperlink;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.text.Text;
import main.java.models.SalesClerk;
import main.java.models.Trainer;

public class SalesClerkMainPageViewController implements Initializable {

  @FXML private Button backBt;
  @FXML private Button startShoppingBt;
  @FXML private Button shoppingHistoryBt;
  @FXML private ComboBox<Trainer> trainersCb;
  @FXML private Hyperlink editOwnRegisterHl;
  @FXML private Hyperlink managerTrainersHl;
  @FXML private Hyperlink manageProductsHl;
  @FXML private Text welcomeTxt;

  private SalesClerk salesClerk;
  private Trainer selectedTrainer;
  private ObservableList<Trainer> trainersObsList;

  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {

    // MOCKING CODE
    salesClerk =
        new SalesClerk(
            "May", "Pallet", "Female", "maypallet", "123456", "maypallet@pokecenter.com");

    List<Trainer> trainers = new ArrayList<>();

    trainers.add(
        new Trainer("Ash", "Pallet", "Male", "ashpallet", "123456", "ashtrainner@pokemail.com"));

    trainers.add(
        new Trainer(
            "Misty",
            "Cerulean",
            "Female",
            "mistycerulean",
            "123456",
            "mistycerulean@pokemail.com"));

    trainers.add(
        new Trainer("Brock", "Kanto", "Male", "brockkanto", "123456", "brockkanto@pokemail.com"));

    initializeWelcomeTxt();
    initializeTrainersListCb(trainers);

    startShoppingBt.setDisable(true);
    shoppingHistoryBt.setDisable(true);
  }

  // UI events

  public void onBackBtAction(ActionEvent event) {

    System.out.println("Logging out");

    try {

      Parent newPage = FXMLLoader.load(getClass().getResource("/main/java/views/Login.fxml"));
      backBt.getScene().setRoot(newPage);
    } catch (Exception e) {
      System.out.println("Error");
    }
  }

  public void onStartShoppingBtAction(ActionEvent event) {

    if (selectedTrainer == null) {
      System.out.println("Error, trainer is null");
    } else {
      System.out.println("Start shopping with " + selectedTrainer.getName());
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
    }
  }

  public void onEditOwnRegisterHlAction(ActionEvent event) {
    System.out.println("Editing own register");
  }

  public void onManagerTrainersHlAction(ActionEvent event) {
    System.out.println("Going to Trainers CRUD");
  }

  public void onManageProductsHlAction(ActionEvent event) {
    System.out.println("Going to Products CRUD");
  }

  // Initialization methods

  private void initializeWelcomeTxt() {

    String newText = welcomeTxt.getText();

    newText = newText.replace("{name}", salesClerk.getName());
    newText = newText.replace("{city}", salesClerk.getBirthCity());

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
