package main.java.views;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import main.java.controllers.ProductController;
import main.java.controllers.UserController;
import main.java.models.Administrator;
import main.java.models.CartItem;
import main.java.models.Product;
import main.java.models.SalesClerk;
import main.java.models.Trainer;
import main.java.models.User;
import main.java.services.SalesService;
import main.java.services.exception.CartItemNotFoundException;
import main.java.views.util.Alerts;

public class ShoppingCartViewController implements Initializable {

  @FXML private TableView<CartItem> productsTV;
  @FXML private TableColumn<CartItem, String> productNamesTC;
  @FXML private TableColumn<CartItem, String> productDescriptionsTC;
  @FXML private TableColumn<CartItem, String> unitPricesTC;
  @FXML private TableColumn<CartItem, String> quantityTC;
  @FXML private TableColumn<CartItem, String> subtotalTC;
  @FXML private Button backBt;
  @FXML private Button addItemBt;
  @FXML private Button removeItemBt;
  @FXML private Button clearCartBt;
  @FXML private Button placeOrderBt;
  @FXML private TextField totalCostTxt;

  private List<CartItem> allProducts;

  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {

    allProducts = new ArrayList<>();

    initializeTableColumns();
    initializeTableValues();
    updateScreen();
  }

  public void addItemBtAction(ActionEvent e) {

    CartItem item = productsTV.getSelectionModel().getSelectedItem();

    if (item == null) {

      Alerts.showAlert(
          "Error!",
          null,
          "There must be an item selected to perform this operation.",
          AlertType.WARNING);
    } else {

      SalesService.getInstance().addToCart(item);
      updateScreen();
    }
  }

  public void removeItemBtAction(ActionEvent e) {

    CartItem item = productsTV.getSelectionModel().getSelectedItem();

    if (item == null) {

      Alerts.showAlert(
          "Error!",
          null,
          "There must be an item selected to perform this operation.",
          AlertType.WARNING);
    } else {

      try {
        SalesService.getInstance().removeFromCart(item);

        updateScreen();
      } catch (CartItemNotFoundException ex) {

        Alerts.showAlert(
            "Error!",
            null,
            "This item can't be removed because it is not in your cart.",
            AlertType.WARNING);
      }
    }
  }

  public void clearCartBtAction(ActionEvent e) {

    SalesService.getInstance().getShoppingCart().clearShoppingCart();

    for (CartItem ci : allProducts) {
      ci.setQuantity(0);
    }

    updateScreen();
  }

  public void backBtAction(ActionEvent e) {

    SalesService.getInstance().cancelOrder();

    User loggedUser = UserController.getInstance().getLoggedUser();

    if (loggedUser instanceof SalesClerk) {

      try {

        Parent newPage =
            FXMLLoader.load(getClass().getResource("/main/java/views/SalesClerkMainPage.fxml"));
        backBt.getScene().setRoot(newPage);
      } catch (Exception ex) {
        System.out.println("Error");
      }
    } else if (loggedUser instanceof Administrator) {

      try {

        Parent newPage =
            FXMLLoader.load(getClass().getResource("/main/java/views/AdministratorMainPage.fxml"));
        backBt.getScene().setRoot(newPage);
      } catch (Exception ex) {
        System.out.println("Error");
      }
    } else if (loggedUser instanceof Trainer) {

      try {

        Parent newPage =
            FXMLLoader.load(getClass().getResource("/main/java/views/TrainerMainPage.fxml"));
        backBt.getScene().setRoot(newPage);
      } catch (Exception ex) {
        System.out.println("Error");
      }
    }
  }

  public void placeOrderBtAction(ActionEvent e) {

    if (SalesService.getInstance().getShoppingCart().getItems().size() > 0) {
      SalesService.getInstance().placeOrder();

      Trainer client = SalesService.getInstance().getOrder().getClient();

      try {

        FXMLLoader loader =
            new FXMLLoader(getClass().getResource("/main/java/views/OrderReportView.fxml"));
        Parent newPage = loader.load();
        OrderReportViewController controller = loader.getController();
        controller.getOrderReport().setClient(client);
        controller.updateTrainerCb();
        backBt.getScene().setRoot(newPage);
      } catch (Exception ex) {
        System.out.println("Error");
        ex.printStackTrace();
      }
    } else {
      Alerts.showAlert("Error!", null, "No items were requested!", AlertType.WARNING);
    }
  }

  private void initializeTableColumns() {

    productNamesTC.setCellValueFactory(
        p -> {
          if (p.getValue() != null) {
            return new SimpleStringProperty(p.getValue().getProduct().getName());
          } else {
            return new SimpleStringProperty("");
          }
        });

    productDescriptionsTC.setCellValueFactory(
        p -> {
          if (p.getValue() != null) {
            return new SimpleStringProperty(p.getValue().getProduct().getDescription());
          } else {
            return new SimpleStringProperty("");
          }
        });

    unitPricesTC.setCellValueFactory(
        p -> {
          if (p.getValue() != null) {
            return new SimpleStringProperty("$" + p.getValue().getProduct().getPrice());
          } else {
            return new SimpleStringProperty("");
          }
        });

    quantityTC.setCellValueFactory(new PropertyValueFactory<>("quantity"));

    subtotalTC.setCellValueFactory(
        p -> {
          if (p.getValue() != null) {
            return new SimpleStringProperty("$" + p.getValue().calculateTotalCost());
          } else {
            return new SimpleStringProperty("");
          }
        });
  }

  private void initializeTableValues() {

    productsTV.getItems().clear();

    for (Product p : ProductController.getInstance().getAllProducts()) {

      CartItem item = new CartItem();
      item.setProduct(p);

      allProducts.add(item);
    }

    ObservableList<CartItem> list = FXCollections.observableList(allProducts);

    productsTV.setItems(list);
  }

  private void updateScreen() {

    productsTV.refresh();

    totalCostTxt.setText(
        "Total cost: $" + SalesService.getInstance().getShoppingCart().calculateTotalCost());
  }
}
