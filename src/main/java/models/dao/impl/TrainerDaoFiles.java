package main.java.models.dao.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import main.java.models.Trainer;
import main.java.models.dao.DaoFactory;
import main.java.models.dao.TrainerDao;

public class TrainerDaoFiles implements TrainerDao {

  private final String path = "./localStorage/trainers.ser";
  private List<Trainer> trainers;

  public TrainerDaoFiles() {

    File file = new File(path);

    if (file.exists()) {
      readFile();
    } else {

      trainers = new ArrayList<>();
      saveFile();
    }
  }

  public static void main(String[] args) {

    TrainerDao dao = DaoFactory.createTrainerDao();

    dao.insert(new Trainer("Ash", "Pallet", "male", "ash", "password", "email", 1));
    dao.insert(new Trainer("Ash", "Pallet", "male", "ash", "password", "email", 1));
    dao.insert(new Trainer("Misty", "Cerulean", "female", "ash", "password", "email", 2));

    for (Trainer t : dao.findAll()) {

      System.out.println(t.getName());
    }

    System.out.println("------------------");

    dao.deleteById(2);

    for (Trainer t : dao.findAll()) {

      System.out.println(t.getName());
    }

    Trainer a = dao.findById(1);

    a.setName("Ashe");

    dao.update(a);

    System.out.println("------------------");

    for (Trainer t : dao.findAll()) {

      System.out.println(t.getName());
    }
  }

  @Override
  public boolean insert(Trainer trainer) {

    for (Trainer t : trainers) {

      if (t.getRegisterId() == trainer.getRegisterId()) {

        System.out.println("Id already exists");

        return false;
      }
    }

    trainers.add(trainer);

    saveFile();

    return true;
  }

  @Override
  public boolean update(Trainer trainer) {

    for (int i = 0; i < trainers.size(); i++) {

      if (trainers.get(i).getRegisterId() == trainer.getRegisterId()) {

        trainers.set(i, trainer);
        break;
      }
    }

    saveFile();

    return false;
  }

  @Override
  public boolean deleteById(int id) {

    for (int i = 0; i < trainers.size(); i++) {

      if (trainers.get(i).getRegisterId() == id) {

        trainers.remove(trainers.get(i));
        break;
      }
    }

    saveFile();

    return false;
  }

  @Override
  public Trainer findById(int id) {

    for (Trainer trainer : trainers) {

      if (trainer.getRegisterId() == id) {

        return trainer;
      }
    }

    return null;
  }

  @Override
  public List<Trainer> findAll() {

    readFile();
    return trainers;
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

      oos.writeObject(this.trainers);

      oos.close();
      fos.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private void readFile() {

    try {

      FileInputStream fis = new FileInputStream(path);
      ObjectInputStream ois = new ObjectInputStream(fis);

      Object obj = ois.readObject();
      trainers = (List<Trainer>) obj;

      ois.close();
      fis.close();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
