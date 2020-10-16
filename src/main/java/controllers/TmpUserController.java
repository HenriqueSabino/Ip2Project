package main.java.controllers;

import main.java.models.Trainer;
import main.java.models.dao.DaoFactory;
import main.java.models.dao.UserDao;

import java.util.ArrayList;
import java.util.List;

public class TmpUserController {

  private static TmpUserController instance;

  private UserDao userDao;

  private TmpUserController() {
    userDao = DaoFactory.createUserDao();
  }

  public static TmpUserController getInstance() {

    if (instance == null) {
      instance = new TmpUserController();
    }

    return instance;
  }

  public List<Trainer> findAllTrainers() {

    List<Trainer> trainers = new ArrayList<>();
    trainers.add(new Trainer("Hyan", "Nova Morada", "Male", "hyanbatista42", "12345", "hyan@gmail.com"));
    trainers.add(new Trainer("Nelsu", "Detran", "Male", "nelsucc", "12345", "nelsu@gmail.com"));
    trainers.add(new Trainer("Silas", "Ribeirão", "Male", "silascmm", "12345", "silas@gmail.com"));
    trainers.add(new Trainer("Henrico", "Iputinga", "Male", "henricomg", "12345", "henrico@gmail.com"));
    trainers.add(new Trainer("Hyan", "Nova Morada", "Male", "hyanbatista42", "12345", "hyan@gmail.com"));
    trainers.add(new Trainer("Nelsu", "Detran", "Male", "nelsucc", "12345", "nelsu@gmail.com"));
    trainers.add(new Trainer("Silas", "Ribeirão", "Male", "silascmm", "12345", "silas@gmail.com"));
    trainers.add(new Trainer("Henrico", "Iputinga", "Male", "henricomg", "12345", "henrico@gmail.com"));
    trainers.add(new Trainer("Hyan", "Nova Morada", "Male", "hyanbatista42", "12345", "hyan@gmail.com"));
    trainers.add(new Trainer("Nelsu", "Detran", "Male", "nelsucc", "12345", "nelsu@gmail.com"));
    trainers.add(new Trainer("Silas", "Ribeirão", "Male", "silascmm", "12345", "silas@gmail.com"));
    trainers.add(new Trainer("Henrico", "Iputinga", "Male", "henricomg", "12345", "henrico@gmail.com"));
    trainers.add(new Trainer("Hyan", "Nova Morada", "Male", "hyanbatista42", "12345", "hyan@gmail.com"));
    trainers.add(new Trainer("Nelsu", "Detran", "Male", "nelsucc", "12345", "nelsu@gmail.com"));
    trainers.add(new Trainer("Silas", "Ribeirão", "Male", "silascmm", "12345", "silas@gmail.com"));
    trainers.add(new Trainer("Henrico", "Iputinga", "Male", "henricomg", "12345", "henrico@gmail.com"));
    trainers.add(new Trainer("Hyan", "Nova Morada", "Male", "hyanbatista42", "12345", "hyan@gmail.com"));
    trainers.add(new Trainer("Nelsu", "Detran", "Male", "nelsucc", "12345", "nelsu@gmail.com"));
    trainers.add(new Trainer("Silas", "Ribeirão", "Male", "silascmm", "12345", "silas@gmail.com"));
    trainers.add(new Trainer("Henrico", "Iputinga", "Male", "henricomg", "12345", "henrico@gmail.com"));
    trainers.add(new Trainer("Hyan", "Nova Morada", "Male", "hyanbatista42", "12345", "hyan@gmail.com"));
    trainers.add(new Trainer("Nelsu", "Detran", "Male", "nelsucc", "12345", "nelsu@gmail.com"));
    trainers.add(new Trainer("Silas", "Ribeirão", "Male", "silascmm", "12345", "silas@gmail.com"));
    trainers.add(new Trainer("Henrico", "Iputinga", "Male", "henricomg", "12345", "henrico@gmail.com"));
    trainers.add(new Trainer("Hyan", "Nova Morada", "Male", "hyanbatista42", "12345", "hyan@gmail.com"));
    trainers.add(new Trainer("Nelsu", "Detran", "Male", "nelsucc", "12345", "nelsu@gmail.com"));
    trainers.add(new Trainer("Silas", "Ribeirão", "Male", "silascmm", "12345", "silas@gmail.com"));
    trainers.add(new Trainer("Henrico", "Iputinga", "Male", "henricomg", "12345", "henrico@gmail.com"));
    trainers.add(new Trainer("Hyan", "Nova Morada", "Male", "hyanbatista42", "12345", "hyan@gmail.com"));
    trainers.add(new Trainer("Nelsu", "Detran", "Male", "nelsucc", "12345", "nelsu@gmail.com"));
    trainers.add(new Trainer("Silas", "Ribeirão", "Male", "silascmm", "12345", "silas@gmail.com"));
    trainers.add(new Trainer("Henrico", "Iputinga", "Male", "henricomg", "12345", "henrico@gmail.com"));
    trainers.add(new Trainer("Hyan", "Nova Morada", "Male", "hyanbatista42", "12345", "hyan@gmail.com"));
    trainers.add(new Trainer("Nelsu", "Detran", "Male", "nelsucc", "12345", "nelsu@gmail.com"));
    trainers.add(new Trainer("Silas", "Ribeirão", "Male", "silascmm", "12345", "silas@gmail.com"));
    trainers.add(new Trainer("Henrico", "Iputinga", "Male", "henricomg", "12345", "henrico@gmail.com"));
    trainers.add(new Trainer("Hyan", "Nova Morada", "Male", "hyanbatista42", "12345", "hyan@gmail.com"));
    trainers.add(new Trainer("Nelsu", "Detran", "Male", "nelsucc", "12345", "nelsu@gmail.com"));
    trainers.add(new Trainer("Silas", "Ribeirão", "Male", "silascmm", "12345", "silas@gmail.com"));
    trainers.add(new Trainer("Henrico", "Iputinga", "Male", "henricomg", "12345", "henrico@gmail.com"));

    return trainers;
  }
}
