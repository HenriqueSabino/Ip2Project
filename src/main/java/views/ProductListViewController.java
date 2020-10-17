package main.java.views;

import java.io.IOException;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import main.java.controllers.ProductController;
import main.java.models.Product;
import main.java.views.util.Alerts;

public class ProductListViewController implements Initializable {

  @FXML private Button buttonNewProduct;
  @FXML private Button buttonUpdate;
  @FXML private Button buttonDelete;
  @FXML private Button buttonBack;
  @FXML private TableView<Product> tableViewProduct;
  @FXML private TableColumn<Product, String> tableColumnNameProduct;
  @FXML private TableColumn<Product, String> tableColumnDescription;
  @FXML private TableColumn<Product, String> tableColumnPrice;
  private List<Product> productList;
  private ObservableList<Product> ProductObservableList;
  private ProductController productController;

  public void setProductController(ProductController productController) {
    this.productController = productController;
  }

  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {

    initializeNodes();
    setProductController(ProductController.getInstance());
    updateTableView();
  }

  private void initializeNodes() {

    tableColumnNameProduct.setCellValueFactory(new PropertyValueFactory<>("name"));
    tableColumnDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
    tableColumnPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
  }

  private void updateTableView() {

    if (productController == null) {
      throw new IllegalStateException("productController was null");
    }

    productList = productController.getAllProducts();

    ProductObservableList = FXCollections.observableArrayList(productList);
    tableViewProduct.setItems(ProductObservableList);
  }

  @FXML
  public void onButtonNewProductAction(ActionEvent event) {

    ProductController.getInstance().setUpdate(false);

    try {

      Parent newPage =
          FXMLLoader.load(getClass().getResource("/main/java/views/ProductFormView.fxml"));
      buttonNewProduct.getScene().setRoot(newPage);
    } catch (Exception e) {
      System.out.println("Error");
    }
  }

  @FXML
  public void onButtonUpdateAction() {

    try {

      if (!tableViewProduct.getSelectionModel().isEmpty()) {

        ProductController.getInstance()
            .setIdForUpdate(tableViewProduct.getSelectionModel().getSelectedItem().getId());
        ProductController.getInstance().setUpdate(true);
        try {

          Parent newPage =
              FXMLLoader.load(getClass().getResource("/main/java/views/ProductFormView.fxml"));
          buttonNewProduct.getScene().setRoot(newPage);
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

      ProductController.getInstance()
          .deleteProductById(tableViewProduct.getSelectionModel().getSelectedItem().getId());
      updateTableView();
    } catch (Exception e) {
      Alerts.showAlert("Error", null, "Select the field to be deleted", AlertType.ERROR);
    }
  }

  @FXML
  public void onButtonBackAction() {

    try {

      Parent newPage =
          FXMLLoader.load(getClass().getResource("/main/java/views/AdministratorMainPage.fxml"));
      buttonBack.getScene().setRoot(newPage);
    } catch (Exception e) {
      System.out.println("Error");
    }
  }
}
