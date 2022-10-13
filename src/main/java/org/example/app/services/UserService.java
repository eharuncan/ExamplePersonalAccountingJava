package org.example.app.services;

import java.util.*;

import org.example.app.domain.User;
import org.example.app.enums.UserTypes;

import static org.example.app.App.expenseCategoryService;

public class UserService {
    private final List <User> userListDB;
    private static User currentUser;

    public UserService(List <User> userListDB) {
        this.userListDB = userListDB;

        User adminUser = new User(0, UserTypes.ADMIN, "admin", "admin", "admin", "admin");
        register(adminUser);

        User customerUser = new User(1, UserTypes.CUSTOMER, "customer1", "customer1", "1", "1");
        register(customerUser);

        currentUser = null;
    }

    public List<User> getUsers() {
        return userListDB;
    }

    public User getUserById(Integer userId) {
        return userListDB.stream()
                .filter(user -> Objects.equals(user.getId(), userId))
                .findFirst()
                .get();
    }

    public User getUserByEmailAndPassword(String email, String password) {
        return userListDB.stream()
                .filter(user -> Objects.equals(user.getEmail(), email) && Objects.equals(user.getPassword(), password))
                .findFirst()
                .get();
    }

    public boolean register(User user) {
        if (validateUser(user)) {
            int newUserId;
            List<User> userList = userListDB;
            if (userList.size() == 0) {
                newUserId = 1;
            } else {
                User lastUser = userList.get(userList.size() - 1);
                newUserId = lastUser.getId() + 1;
            }
            user.setId(newUserId);

            if (!expenseCategoryService.addDefaultExpenseCategories(newUserId)) {
                return false;
            }

            userListDB.add(user);
            currentUser = user;
            return true;
        } else {
            return false;
        }
    }

    public boolean editUser(User user, User editedUser) {
        if (validateUser(editedUser)) {
            int index = getUsers().indexOf(user);
            userListDB.set(index, editedUser);
            return true;
        } else {
            return false;
        }
    }

    public boolean deleteUser(Integer userId) {
        if (userId == 1) {
            return false;
        } else {
            User foundUser = getUserById(userId);
            userListDB.remove(foundUser);
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
        return userListDB.stream()
                .anyMatch(user -> Objects.equals(user.getEmail(), email) && Objects.equals(user.getPassword(), password));
    }

    public User getCurrentUser() {
        return currentUser;
    }
    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }
}

