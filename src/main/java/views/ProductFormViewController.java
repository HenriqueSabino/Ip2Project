package main.java.views;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import main.java.controllers.ProductController;
import main.java.models.Product;
import main.java.models.dao.impl.exception.NameOrDescriptionInUseException;
import main.java.views.util.Alerts;
import main.java.views.util.Constraints;

public class ProductFormViewController implements Initializable {

  @FXML private Button confirmProductBt;
  @FXML private Button backBt;
  @FXML private TextField nameProductField;
  @FXML private TextField descriptionProductField;
  @FXML private TextField valueUnitProductField;

  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {
    Constraints.setTextFieldInteger(valueUnitProductField);
  }

  public void onConfirmProductBt(ActionEvent event) {

    if (!ProductController.getInstance().isUpdate()) {
      try {

        if (nameProductField.getText().isEmpty()
            || descriptionProductField.getText().isEmpty()
            || valueUnitProductField.getText().isEmpty()) {
          Alerts.showAlert("Error", null, "The fields must be filled", AlertType.ERROR);
        } else {

          ProductController.getInstance()
              .insertProduct(
                  new Product(
                      nameProductField.getText(),
                      descriptionProductField.getText(),
                      Integer.parseInt(valueUnitProductField.getText())));

          Alerts.showAlert("Sucess", null, "Inserted product", AlertType.INFORMATION);
          goToProductList();
        }

      } catch (NameOrDescriptionInUseException e) {
        Alerts.showAlert("", null, e.getMessage(), AlertType.ERROR);
      }
    } else {

      try {

        if (nameProductField.getText().isEmpty()
            || descriptionProductField.getText().isEmpty()
            || valueUnitProductField.getText().isEmpty()) {
          Alerts.showAlert("Error", null, "The fields must be filled", AlertType.ERROR);
        } else {

          Product product =
              new Product(
                  nameProductField.getText(),
                  descriptionProductField.getText(),
                  Integer.parseInt(valueUnitProductField.getText()));
          product.setId(ProductController.getInstance().getUpdateId());
          ProductController.getInstance().updateProduct(product);

          Alerts.showAlert("Sucess", null, "Update product", AlertType.INFORMATION);
          goToProductList();
        }

      } catch (NameOrDescriptionInUseException e) {
        Alerts.showAlert("", null, e.getMessage(), AlertType.ERROR);
      }
    }
  }

  public void onBackBtAction(ActionEvent action) {
    System.out.println("Going to SalesClerk Main Page");
  }

  public void goToProductList() {

    try {

      Parent newPage =
          FXMLLoader.load(getClass().getResource("/main/java/views/ProductListView.fxml"));
      backBt.getScene().setRoot(newPage);
    } catch (Exception e) {
      System.out.println("Error");
    }
  }
}
