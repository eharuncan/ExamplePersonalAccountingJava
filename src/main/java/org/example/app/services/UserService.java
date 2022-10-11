package org.example.app.services;

import java.util.*;

import org.example.app.domain.User;
import org.example.app.enums.UserTypes;

import org.example.db.Database;

import static org.example.app.App.expenseCategoryService;

public class UserService {

    private final Database database;
    private static User currentUser;


    public UserService(Database database) {
        this.database = database;

        User adminUser = new User(0, UserTypes.ADMIN, "admin", "admin", "admin@admin.com", "admin");
        database.getUserList().add(adminUser);

        User customerUser = new User(1, UserTypes.CUSTOMER, "customer1", "customer1", "1", "1");
        database.getUserList().add(customerUser);
    }

    public List<User> getUsers() {
        return database.getUserList();
    }

    public User getUserById(Integer userId) {
        List<User> userList = database.getUserList();
        return userList.stream()
                .filter(user -> Objects.equals(user.getId(), userId))
                .findFirst()
                .get();
    }

    public User getUserByEmailAndPassword(String email, String password) {
        List<User> userList = database.getUserList();
        return userList.stream()
                .filter(user -> Objects.equals(user.getEmail(), email) && Objects.equals(user.getPassword(), password))
                .findFirst()
                .get();
    }

    public boolean register(User user, String secondPassword) {
        if (checkPasswords(user.getPassword(), secondPassword)) {
            if (validateUser(user)) {
                int newUserId;
                List <User> userList = database.getUserList();
                if (userList.size() == 0){
                    newUserId = 0;
                }else {
                    User lastUser =  userList.get(userList.size()-1);
                    newUserId = lastUser.getId() + 1;
                }
                user.setId(newUserId);

                expenseCategoryService.addExpenseCategory(newUserId,"Çocuk" );
                expenseCategoryService.addExpenseCategory(newUserId,"Güvenlik" );
                expenseCategoryService.addExpenseCategory(newUserId,"Kitap" );
                expenseCategoryService.addExpenseCategory(newUserId,"Sağlık" );
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

    public boolean deleteUser(Integer userId) {
        if (userId == 0) {
            return false;
        } else {
            User foundUser = getUserById(userId);
            database.getUserList().remove(foundUser);
            return true;
        }
    }

    public boolean login(String email, String password) {
        if (checkUser(email, password)) {
            currentUser = getUserByEmailAndPassword(email, password);
            return true;
        } else {
            return false;
        }
    }

    public boolean logout() {
        // Burada user adına tutulan oturum açma bilgileri silinir.
        currentUser = null;
        return true;
    }

    public boolean validateUser(User user) {

        //todo: burası yazılacak
        return true;

    }

    public boolean checkUser(String email, String password) {
        List<User> userList = database.getUserList();
        return userList.stream()
                .anyMatch(user -> Objects.equals(user.getEmail(), email) && Objects.equals(user.getPassword(), password));
    }

    public boolean checkPasswords(String firstPassword, String secondPassword) {
        return Objects.equals(firstPassword, secondPassword);
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }
}

