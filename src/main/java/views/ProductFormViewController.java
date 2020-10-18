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
  private Product product;

  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {
    Constraints.setTextFieldInteger(valueUnitProductField);
  }

  public void onConfirmProductBt(ActionEvent event) {

    if (product == null) {
      try {

        if (nameProductField.getText().trim().isEmpty()
            || descriptionProductField.getText().trim().isEmpty()
            || valueUnitProductField.getText().trim().isEmpty()) {
          Alerts.showAlert("Error", null, "The fields must be filled", AlertType.ERROR);
        } else {

          if (Integer.parseInt(valueUnitProductField.getText()) > 0) {
            ProductController.getInstance()
                .insertProduct(
                    new Product(
                        nameProductField.getText(),
                        descriptionProductField.getText(),
                        Integer.parseInt(valueUnitProductField.getText())));

            Alerts.showAlert("Sucess", null, "Inserted product", AlertType.INFORMATION);
            goToProductList();
          } else {
            Alerts.showAlert("Error ", null, "Price must be greater than zero", AlertType.ERROR);
          }
        }
      } catch (NameOrDescriptionInUseException e) {
        Alerts.showAlert("", null, e.getMessage(), AlertType.ERROR);
      }
    } else {

      try {

        if (nameProductField.getText().trim().isEmpty()
            || descriptionProductField.getText().trim().isEmpty()
            || valueUnitProductField.getText().trim().isEmpty()) {
          Alerts.showAlert("Error", null, "The fields must be filled", AlertType.ERROR);
        } else {

          if (Integer.parseInt(valueUnitProductField.getText()) > 0) {

            product.setName(nameProductField.getText());
            product.setDescription(descriptionProductField.getText());
            product.setPrice(Integer.parseInt(valueUnitProductField.getText()));
            ProductController.getInstance().updateProduct(product);

            Alerts.showAlert("Sucess", null, "Update product", AlertType.INFORMATION);
            goToProductList();
          } else {
            Alerts.showAlert("Error ", null, "Price must be greater than zero", AlertType.ERROR);
          }
        }

      } catch (NameOrDescriptionInUseException e) {
        Alerts.showAlert("", null, e.getMessage(), AlertType.ERROR);
      }
    }
  }

  public void onBackBtAction(ActionEvent action) {

    try {

      Parent newPage =
          FXMLLoader.load(getClass().getResource("/main/java/views/ProductListView.fxml"));
      backBt.getScene().setRoot(newPage);
    } catch (Exception e) {
      System.out.println("Error");
    }
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

  public void fillFields() {

    if (product == null) {
      throw new IllegalStateException("Product was null.");
    }

    nameProductField.setText(product.getName());
    descriptionProductField.setText(product.getDescription());
    valueUnitProductField.setText(String.valueOf(product.getPrice()));
  }

  public void setProduct(Product product) {
    this.product = product;
  }
}
