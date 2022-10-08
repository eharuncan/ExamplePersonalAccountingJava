package org.example.app;

import java.util.*;

import org.example.db.Database;
import org.example.app.enums.UserTypes;

public class UserService {

    private final Database database;
    private User currentUser;

    public UserService(Database database) {
        this.database = database;
    }

    public boolean register(User user, String secondPassword) {

        if (checkPasswords(user.getPassword(), secondPassword)) {
            if (validateUser(user)) {
                user.setId(database.getUserList().size());
                database.getUserList().add(user);
                currentUser = user;
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }

    }

    public boolean checkPasswords(String firstPassword, String secondPassword) {

        return Objects.equals(firstPassword, secondPassword);

    }

    public boolean login(String email, String password) {

        if (checkUser(email, password)) {
            currentUser = getUserByEmailAndPassword(email, password);
            return true;
        } else {
            return false;
        }

    }

    public boolean checkUser(String email, String password) {

        List<User> userList = database.getUserList();

        return userList.stream().anyMatch(x -> Objects.equals(x.getEmail(), email) && Objects.equals(x.getPassword(), password));

    }

    public User getUserByEmailAndPassword(String email, String password) {

        List<User> userList = database.getUserList();
        return userList.stream().filter(x -> Objects.equals(x.getEmail(), email) && Objects.equals(x.getPassword(), password)).findFirst().get();

    }

    public User getUserByIndex(Integer index) {

        return database.getUserList().get(index);

    }

    public boolean logout() {

        // Burada user adına olan oturum açma bilgileri silinir.
        currentUser = null;
        return true;

    }

    public List<User> getAllUsers() {

        return database.getUserList();

    }

    public boolean deleteUser(Integer index) {

        User foundUser = getUserByIndex(index);
        database.getUserList().remove(foundUser);
        return true;

    }

    public User getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }

    public boolean validateUser(User user) {

        //todo: burası yazılacak
        return true;

    }

}

