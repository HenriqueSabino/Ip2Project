package main.java.models;

public abstract class User extends Person {

    private String username;
    private String password;
    private String email;

    public User() {}

    public User(String name, String birthCity, String gender, String username, String password, String email) {

        super(name, birthCity, gender);
        this.username = username;
        this.password = password;
        this.email = email;
    }
}
