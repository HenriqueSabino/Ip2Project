package main.java.application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class TrainerViewTest extends Application {

  public static void main(String[] args) {
    launch(args);
  }

  @Override
  public void start(Stage primaryStage) throws IOException {
    Parent root = FXMLLoader.load(getClass().getResource("/main/java/views/TrainerListView.fxml"));
    primaryStage.setTitle("TrainerListView");
    primaryStage.setScene(new Scene(root, 800, 600));
    primaryStage.show();
  }
}
