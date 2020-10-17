package main.java.application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

  public static void main(String[] args) {
    launch(args);
  }

  @Override
  public void start(Stage stage) throws Exception {

    Parent root = FXMLLoader.load(getClass().getResource("/main/java/views/ProductListView.fxml"));
    stage.setScene(new Scene(root));
    stage.setTitle("PokéCenter");
    stage.setResizable(false);
    stage.show();
  }
}
