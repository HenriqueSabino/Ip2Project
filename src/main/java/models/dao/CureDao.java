package main.java.models.dao;

import java.util.List;
import main.java.models.Cure;

public interface CureDao {

  Cure insert(Cure cure);

  Cure update(Cure cure);

  void deleteById(int id);

  Cure findById(int id);

  List<Cure> findAll();
}
