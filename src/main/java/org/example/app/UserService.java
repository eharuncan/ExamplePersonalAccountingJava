package org.example.app;

import java.util.*;
import org.example.db.Database;
import org.example.app.common.UserTypes;

public class UserService {

    private final Database database;

    public UserService(Database database) {
        this.database = database;
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
            System.out.println("\nHata: Şifreler Uyuşmuyor. Lütfen tekrar giriniz:");
            return false;
        }

    }

    public User login(String email, String password) {

        if (checkUser(email, password)){
            return getUser(email, password);
        }
        else {
            return null;
        }

    }

    public boolean checkUser(String email, String password) {

        if (Objects.equals(email, "admin@admin.com") && Objects.equals(password, "admin")){
            return true;
        }
        else {
            List<User> userList = database.getUserList();
            if (userList.stream().anyMatch(x -> Objects.equals(x.getEmail(), email) && Objects.equals(x.getPassword(), password))){
                return true;
            }
            else  {
                System.out.println("\nHata: Sistemde böyle bir kullanıcı bulunmuyor.");
                return false;
            }
        }

    }

    public User getUser(String email, String password) {

        if (Objects.equals(email, "admin@admin.com") && Objects.equals(password, "admin")){

            User adminUser = new User();
            adminUser.setName("admin");
            adminUser.setSurname("admin");
            adminUser.setEmail("admin@admin.com");
            adminUser.setPassword("admin");
            adminUser.setType(UserTypes.ADMIN);
            return adminUser;
        }
        else {

            List<User> userList = database.getUserList();
            return userList.stream().filter(x -> Objects.equals(x.getEmail(), email) && Objects.equals(x.getPassword(), password)).findFirst().get();
        }

    }

    public boolean logout(User user) {

        user = null;
        return true;

    }

    public List <User> getAllUsers() {

        List<User> userList = database.getUserList();
        return userList;

    }

    public boolean deleteUser(Integer index) {

        database.getUserList().remove(index);
        return true;

    }

}

