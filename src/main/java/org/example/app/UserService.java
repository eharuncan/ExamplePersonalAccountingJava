package org.example.app;

import java.util.*;

import org.example.db.Database;
import org.example.app.common.UserTypes;

public class UserService {

    private final Database database;
    private User currentUser;

    public UserService(Database database, User currentUser) {
        this.database = database;
        this.currentUser = currentUser;
    }

    public boolean register(User user, String secondPassword) {

        if (checkPasswords(user.getPassword(), secondPassword)) {
            database.getUserList().add(user);
            return true;
        } else {
            return false;
        }

    }

    public boolean checkPasswords(String firstPassword, String secondPassword) {

        if (Objects.equals(firstPassword, secondPassword)) {
            return true;
        } else {
            System.out.println("\nHata: Şifreler Uyuşmuyor. Lütfen tekrar giriniz.");
            return false;
        }

    }

    public User login(String email, String password) {

        if (checkUser(email, password)) {
            return getUserByEmailAndPassword(email, password);
        } else {
            return null;
        }

    }

    public boolean checkUser(String email, String password) {

        List<User> userList = database.getUserList();

        if (Objects.equals(email, "admin@admin.com") && Objects.equals(password, "admin")) {

            return true;

        } else if (userList.stream().anyMatch(x -> Objects.equals(x.getEmail(), email) && Objects.equals(x.getPassword(), password))){

            return true;

        }
        else {

            System.out.println("\nHata: Sistemde böyle bir kullanıcı bulunmuyor.");
            return false;

        }

    }

    public User getUserByEmailAndPassword(String email, String password) {

        if (Objects.equals(email, "admin@admin.com") && Objects.equals(password, "admin")) {

            User adminUser = new User();
            adminUser.setName("admin");
            adminUser.setSurname("admin");
            adminUser.setEmail("admin@admin.com");
            adminUser.setPassword("admin");
            adminUser.setType(UserTypes.ADMIN);
            return adminUser;

        } else {

            List<User> userList = database.getUserList();
            return userList.stream().filter(x -> Objects.equals(x.getEmail(), email) && Objects.equals(x.getPassword(), password)).findFirst().get();

        }

    }

    public User getUserByIndex(Integer index) {

        return database.getUserList().get(index);

    }

    public boolean logout(User user) {

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

}

