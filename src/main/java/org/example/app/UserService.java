package org.example.app;

import java.util.*;
import org.example.db.Database;
import org.example.app.common.UserTypes;

public class UserService {

    private final Database database;

    public UserService(Database database) {
        this.database = database;
    }

    public User register() {

        User newUser = new User();

        Scanner readScreen = new Scanner(System.in);

        System.out.println("Adınızı giriniz:");
        newUser.setName(readScreen.nextLine());

        System.out.println("Soyadınızı giriniz:");
        newUser.setSurname(readScreen.nextLine());

        System.out.println("Eposta adresinizi giriniz:");
        newUser.setEmail(readScreen.nextLine());

        boolean check = false;
        String firstPassword = null;

        while (!check) {

            System.out.println("Şifrenizi giriniz:");
            firstPassword = readScreen.nextLine();

            System.out.println("Şifrenizi tekrar giriniz:");
            String secondPassword = readScreen.nextLine();

            if (checkPasswords(firstPassword, secondPassword)) {
                check = true;
            }

        }

        newUser.setPassword(firstPassword);

        newUser.setType(UserTypes.CUSTOMER);

        database.getUserList().add(newUser);

        return newUser;

    }

    public boolean checkPasswords(String firstPassword, String secondPassword) {

        if (Objects.equals(firstPassword, secondPassword)) {
            return true;
        } else {
            System.out.println("Hata: Şifreler Uyuşmuyor. Lütfen tekrar giriniz:");
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
            return userList.stream().anyMatch(x -> Objects.equals(x.getEmail(), email) && Objects.equals(x.getPassword(), password));
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

