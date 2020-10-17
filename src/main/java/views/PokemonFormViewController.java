package main.java.views;

import javafx.fxml.Initializable;
import main.java.controllers.UserController;
import main.java.models.Pokemon;

import java.net.URL;
import java.util.ResourceBundle;

public class PokemonFormViewController implements Initializable {

  private Pokemon entity;
  private UserController controller;

  public void setPokemon(Pokemon entity) {
    this.entity = entity;
  }

  public void setUserController(UserController controller) {
    this.controller = controller;
  }

  @Override
  public void initialize(URL location, ResourceBundle resources) {

  }
}
