package main.java.models.dao;

import java.util.List;
import main.java.models.Trainer;

public interface TrainerDao {

  boolean insert(Trainer trainer);

  boolean update(Trainer trainer);

  boolean deleteById(int id);

  Trainer findById(int id);

  List<Trainer> findAll();
}
