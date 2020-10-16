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

<<<<<<< HEAD
    Parent root = FXMLLoader.load(getClass().getResource("/main/java/views/TrainerListView.fxml"));
=======
    Parent root = FXMLLoader.load(getClass().getResource("/main/java/views/Login.fxml"));
>>>>>>> 88522ead29bf94187141a38b02093a3c178e3240
    stage.setScene(new Scene(root));
    stage.setTitle("PokéCenter");
    stage.setResizable(false);
    stage.show();
  }
}
