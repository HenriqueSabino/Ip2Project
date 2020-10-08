package main.java.models.dao.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import main.java.models.Cure;
import main.java.models.Nurse;
import main.java.models.Pokemon;
import main.java.models.PokemonStatus;
import main.java.models.PokemonType;
import main.java.models.Trainer;
import main.java.models.dao.CureDao;
import main.java.models.dao.DaoFactory;
import main.java.models.dao.impl.exception.CureNotFoundException;
import main.java.models.dao.impl.exception.LocalDBIOException;
import main.java.models.dao.impl.exception.UserNotFoundException;

public class CureDaoFiles implements CureDao {

  private final String path = "./localStorage/cures.ser";
  private ArrayList<Cure> cures;
  private ArrayList<Cure> backup;

  public CureDaoFiles() {

    File file = new File(path);

    if (file.exists()) {
      readFile();
    } else {

      cures = new ArrayList<>();

      Trainer ash = new Trainer("Ash", "Pallet", "male", "ash", "password", "email");
      Nurse joy = new Nurse("Joy", "Cerulean", "female", "joy", "password", "email1");

      LocalDateTime time = LocalDateTime.now();

      ash.getPokemons()
          .add(new Pokemon("Pikachu", 60, 60, PokemonType.ELECTRIC, PokemonStatus.NONE, ash));

      insert(new Cure(time, ash.getPokemons().get(0), 60, PokemonStatus.NONE, joy, ash));

      saveFile();
    }
  }

  public static void main(String[] args) {

    // Please, backup and delete localStorage\\cures.ser before testing
    CureDao dao = DaoFactory.createCureDao();

    if (dao.findAll().size() == 1) {

      Trainer misty = new Trainer("Misty", "Cerulean", "female", "misty", "password", "email");
      Nurse joy2 = new Nurse("Joy", "Cerulean", "female", "joy2", "password", "email1");

      LocalDateTime time = LocalDateTime.now();

      misty
          .getPokemons()
          .add(new Pokemon("Staryu", 70, 70, PokemonType.WATER, PokemonStatus.NONE, misty));

      misty
          .getPokemons()
          .add(new Pokemon("Starmie", 85, 85, PokemonType.WATER, PokemonStatus.NONE, misty));

      Cure cure1 = new Cure(time, misty.getPokemons().get(0), 70, PokemonStatus.NONE, joy2, misty);
      Cure cure2 = new Cure(time, misty.getPokemons().get(1), 85, PokemonStatus.NONE, joy2, misty);

      System.out.println("Testing inserts:");

      dao.insert(cure1);

      dao.insert(cure2);

      for (Cure t : dao.findAll()) {

        System.out.println("Cure of Pokemon: " + t.getPokemon().getSpecies());
      }

      System.out.println("------------------");
      System.out.println("Testing deletes:");

      dao.deleteById(cure2.getId());

      try {
        dao.deleteById(cure2.getId());
      } catch (CureNotFoundException e) {
        System.out.println(e.getMessage());
      }

      for (Cure t : dao.findAll()) {

        System.out.println("Cure of Pokemon: " + t.getPokemon().getSpecies());
      }

      try {
        dao.findById(cure2.getId());
      } catch (CureNotFoundException e) {
        System.out.println(e.getMessage());
      }

      System.out.println("------------------");
      System.out.println("Testing update:");
      Cure a = dao.findById(cure1.getId());

      a.setCureDate(time);

      dao.update(a);

      try {
        dao.update(cure2);
      } catch (CureNotFoundException e) {
        System.out.println(e.getMessage());
      }
    }
  }

  @Override
  public Cure insert(Cure cure) {

    // backing up if something goes wrong
    backup = (ArrayList<Cure>) cures.clone();

    // Using LocalDateTime.now().hashCode() to provide uniqueIds to all users
    cure.setId(LocalDateTime.now().hashCode());
    cures.add(cure);

    saveFile();

    return cure;
  }

  @Override
  public Cure update(Cure cure) {

    // backing up if something goes wrong
    backup = (ArrayList<Cure>) cures.clone();

    int index = -1;

    for (int i = 0; i < cures.size(); i++) {

      if (cures.get(i).getId() == cure.getId()) {
        index = i;
      }
    }

    if (index < 0) {
      throw new CureNotFoundException("Cure of Id " + cure.getId() + " was not found.");
    }

    saveFile();

    return cure;
  }

  @Override
  public void deleteById(int id) {

    // backing up if something goes wrong
    backup = (ArrayList<Cure>) cures.clone();

    int index = -1;

    for (int i = 0; i < cures.size(); i++) {

      if (cures.get(i).getId() == id) {
        index = i;
      }
    }

    if (index < 0) {
      throw new UserNotFoundException(
          "Cure of Id " + id + " was not found. Delete action could not complete");
    }
  }

  @Override
  public Cure findById(int id) {

    for (Cure u : cures) {

      if (u.getId() == id) {
        return u;
      }
    }

    throw new UserNotFoundException(
        "Cure of Id " + id + " was not found. Find action could not complete");
  }

  @Override
  public List<Cure> findAll() {
    return cures;
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

      oos.writeObject(this.cures);

      oos.close();
      fos.close();
    } catch (Exception e) {

      // rollingback
      cures = backup;
      throw new LocalDBIOException(e.getMessage());
    }
  }

  private void readFile() {

    try {

      FileInputStream fis = new FileInputStream(path);
      ObjectInputStream ois = new ObjectInputStream(fis);

      Object obj = ois.readObject();
      cures = (ArrayList<Cure>) obj;

      ois.close();
      fis.close();
    } catch (Exception e) {
      throw new LocalDBIOException(e.getMessage());
    }
  }
}
