package main.java.models.dao.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import main.java.models.Administrator;
import main.java.models.Nurse;
import main.java.models.Pokemon;
import main.java.models.PokemonStatus;
import main.java.models.PokemonType;
import main.java.models.SalesClerk;
import main.java.models.Trainer;
import main.java.models.User;
import main.java.models.dao.DaoFactory;
import main.java.models.dao.UserDao;
import main.java.models.dao.impl.exception.LocalDBIOException;
import main.java.models.dao.impl.exception.UserNotFoundException;
import main.java.models.dao.impl.exception.UsernameOrEmailInUseException;

public class UserDaoFiles implements UserDao {

  private final String path = "./localStorage/users.ser";
  private ArrayList<User> users;
  private ArrayList<User> backup;

  public UserDaoFiles() {

    File file = new File(path);

    if (file.exists()) {
      readFile();
    } else {

      users = new ArrayList<>();

      users.add(
          new Administrator(
              "Proprietario",
              "None",
              "None",
              "ProprietarioCentro",
              "123456",
              "proprietario@pokecentro.com"));

      saveFile();
    }
  }

  public static void main(String[] args) {

    // Please, backup and delete localStorage\\users.ser before testing
    UserDao dao = DaoFactory.createUserDao();

    Trainer ash = new Trainer("Ash", "Pallet", "male", "ash", "password", "email");
    Nurse joy = new Nurse("Joy", "Cerulean", "female", "joy", "password", "email1");

    System.out.println("Testing inserts:");

    ash.getPokemons()
        .add(new Pokemon("Pikachu", 60, 60, PokemonType.ELECTRIC, PokemonStatus.NONE, ash));

    dao.insert(ash);

    try {
      dao.insert(ash);
    } catch (UsernameOrEmailInUseException e) {
      System.out.println(e.getMessage());
    }

    dao.insert(joy);

    for (User t : dao.findAll()) {

      System.out.println(t.getName());
    }

    System.out.println("------------------");
    System.out.println("Testing deletes:");

    dao.deleteById(joy.getRegisterId());

    try {
      dao.deleteById(joy.getRegisterId());
    } catch (UserNotFoundException e) {
      System.out.println(e.getMessage());
    }

    for (User t : dao.findAll()) {

      System.out.println(t.getName());
    }

    try {
      dao.findById(joy.getRegisterId());
    } catch (UserNotFoundException e) {
      System.out.println(e.getMessage());
    }

    System.out.println("------------------");
    System.out.println("Testing update:");
    User a = dao.findById(ash.getRegisterId());

    a.setName("Ashe");

    dao.update(a);

    try {
      dao.update(joy);
    } catch (UserNotFoundException e) {
      System.out.println(e.getMessage());
    }

    System.out.println("------------------");
    System.out.println("Testing inheritances");

    for (User u : dao.findAll()) {

      System.out.println(u.getName());

      System.out.println("Is " + u.getName() + " an admin? " + (u instanceof Administrator));
      System.out.println("Is " + u.getName() + " a nurse? " + (u instanceof Nurse));
      System.out.println("Is " + u.getName() + " a sales clerk? " + (u instanceof SalesClerk));
      System.out.println("Is " + u.getName() + " a trainer? " + (u instanceof Trainer));

      if (a instanceof Trainer) {
        System.out.println(
            u.getName()
                + "'s first pokemon is a "
                + ((Trainer) u).getPokemons().get(0).getSpecies());
      }
    }
  }

  @Override
  public User insert(User user) {

    // backing up if something goes wrong
    backup = (ArrayList<User>) users.clone();

    for (User u : users) {

      if (u.getUsername().equals(user.getUsername()) || u.getEmail().equals(user.getEmail())) {
        throw new UsernameOrEmailInUseException(
            "Username and email must be unique. Insert action was canceled");
      }
    }

    // Using LocalDateTime.now().hashCode() to provide uniqueIds to all users
    user.setRegisterId(LocalDateTime.now().hashCode());
    users.add(user);

    saveFile();

    return user;
  }

  @Override
  public User update(User user) {

    // backing up if something goes wrong
    backup = (ArrayList<User>) users.clone();

    int index = -1;

    for (int i = 0; i < users.size(); i++) {

      if (users.get(i).getRegisterId() == user.getRegisterId()) {
        index = i;
      } else if (users.get(i).getUsername().equals(user.getUsername())
          || users.get(i).getEmail().equals(user.getEmail())) {
        throw new UsernameOrEmailInUseException(
            "Username and email must be unique. Update action was canceled");
      }
    }

    if (index < 0) {
      throw new UserNotFoundException("User of Id " + user.getRegisterId() + " was not found.");
    }

    saveFile();

    return user;
  }

  @Override
  public void deleteById(int id) {

    // backing up if something goes wrong
    backup = (ArrayList<User>) users.clone();

    int index = -1;

    for (int i = 0; i < users.size(); i++) {

      if (users.get(i).getRegisterId() == id) {
        index = i;
      }
    }

    if (index < 0) {
      throw new UserNotFoundException(
          "User of Id " + id + " was not found. Delete action could be completed");
    }

    users.remove(index);
    saveFile();
  }

  @Override
  public User findById(int id) {

    for (User u : users) {

      if (u.getRegisterId() == id) {
        return u;
      }
    }

    throw new UserNotFoundException(
        "User of Id " + id + " was not found. Find action could be completed");
  }

  @Override
  public List<User> findAll() {
    return users;
  }

  private void saveFile() {

    try {

      File file = new File(path);

      if (!file.exists()) {

        File parentDir = file.getParentFile();

        if (!parentDir.exists()) {

          if (!parentDir.mkdirs()) {
            return;
          }
        }

        if (!file.createNewFile()) {
          return;
        }
      }

      FileOutputStream fos = new FileOutputStream(path);
      ObjectOutputStream oos = new ObjectOutputStream(fos);

      oos.writeObject(this.users);

      oos.close();
      fos.close();
    } catch (Exception e) {

      // rollingback
      users = backup;
      throw new LocalDBIOException(e.getMessage());
    }
  }

  private void readFile() {

    try {

      FileInputStream fis = new FileInputStream(path);
      ObjectInputStream ois = new ObjectInputStream(fis);

      Object obj = ois.readObject();
      users = (ArrayList<User>) obj;

      ois.close();
      fis.close();
    } catch (Exception e) {
      throw new LocalDBIOException(e.getMessage());
    }
  }
}
