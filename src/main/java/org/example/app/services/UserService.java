package org.example.app.services;

import java.util.*;

import org.example.app.domain.User;
import org.example.app.enums.UserTypes;

import org.example.db.Database;

public class UserService {

    private final Database database;
    private static User currentUser;
    private final List<String> defaultExpenseCategoryList = new ArrayList<>();

    public UserService(Database database) {
        this.database = database;

        defaultExpenseCategoryList.add("Çocuk");
        defaultExpenseCategoryList.add("Güvenlik");
        defaultExpenseCategoryList.add("Kitap");
        defaultExpenseCategoryList.add("Sağlık");

        User adminUser = new User(0, UserTypes.ADMIN, "admin", "admin", "admin@admin.com", "admin", null   );
        database.getUserList().add(adminUser);

        User customerUser = new User(1, UserTypes.CUSTOMER, "customer1", "customer1", "1", "1", defaultExpenseCategoryList);
        database.getUserList().add(customerUser);
    }

    public boolean register(User user, String secondPassword) {
        if (checkPasswords(user.getPassword(), secondPassword)) {
            if (validateUser(user)) {
                user.setId(database.getUserList().size());
                user.setExpenseCategoryList(defaultExpenseCategoryList);
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
        return userList.stream()
                .anyMatch(user -> Objects.equals(user.getEmail(), email) && Objects.equals(user.getPassword(), password));
    }

    public User getUserByEmailAndPassword(String email, String password) {
        List<User> userList = database.getUserList();
        return userList.stream()
                .filter(user -> Objects.equals(user.getEmail(), email) && Objects.equals(user.getPassword(), password))
                .findFirst()
                .get();
    }

    public User getUserById(Integer userId) {
        List<User> userList = database.getUserList();
        return userList.stream()
                .filter(user -> Objects.equals(user.getId(), userId))
                .findFirst()
                .get();
    }

    public User getUserByIndex(Integer index) {
        return database.getUserList().get(index);
    }

    public boolean logout() {
        // Burada user adına tutulan oturum açma bilgileri silinir.
        currentUser = null;
        return true;
    }

    public List<User> getUsers() {
        return database.getUserList();
    }

    public boolean deleteUserByIndex(Integer index) {
        if (index == 0) {
            return false;
        } else {
            User foundUser = getUserByIndex(index);
            database.getUserList().remove(foundUser);
            return true;
        }
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

    public String getExpenseCategoryByUserIdAndIndex(Integer userId, Integer index) {
        return getUserById(userId).getExpenseCategoryList().get(index);
    }

    public List<String> getDefaultExpenseCategoryList() {
        return defaultExpenseCategoryList;
    }
}

