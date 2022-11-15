package org.example.app.services;

import java.util.*;

import org.example.app.domain.User;
import org.example.app.enums.UserTypes;

import static org.example.app.App.expenseCategoryService;

public class UserService {
    private final List<User> userListDB;
    private User currentUser;

    public UserService(List<User> userListDB) {
        this.userListDB = userListDB;

        register("admin", "admin", "admin", "admin", "admin");
        register("customer1", "customer1", "1", "1", "1");

        currentUser = null;
    }

    public List<User> getUsers() {
        return userListDB;
    }

    public User getUserById(Long userId) {
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

    public boolean register(String name, String surname, String email, String password, String retypedPassword) {

        if (checkPasswords(password, retypedPassword)) {

            long newUserId;
            List<User> userList = userListDB;
            if (userList.size() == 0) {
                newUserId = 1;
            } else {
                User lastUser = userList.get(userList.size() - 1);
                newUserId = lastUser.getId() + 1;
            }

            if (!expenseCategoryService.addDefaultExpenseCategories(newUserId)) {
                return false;
            }

            UserTypes selectedUserType;
            if (Objects.equals(name, "admin")) {
                selectedUserType = UserTypes.ADMIN;
            } else {
                selectedUserType = UserTypes.CUSTOMER;
            }

            User newUser = new User(newUserId, selectedUserType, name, surname, email, password);
            userListDB.add(newUser);
            currentUser = newUser;
            return true;

        }else {
            return false;
        }

    }

    public boolean editUser(Long id, String editedName, String editedSurname, String editedEmail, String editedPassword, String retypedPassword) {
        if (this.checkPasswords(editedPassword, retypedPassword)) {
            User editedUser = new User(id, UserTypes.CUSTOMER, editedName, editedSurname, editedEmail, editedPassword);
            int index = getUsers().indexOf(getUserById(id));
            userListDB.set(index, editedUser);
            return true;
        } else {
            return false;
        }
    }

    public boolean deleteUser(Long userId) {
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
            System.out.println("\nHata: Sistemde bu bilgilere sahip bir kullanıcı bulunamadı.");
            return false;
        }
    }

    public boolean logout() {
        // Burada user adına tutulan oturum açma bilgileri silinir.
        currentUser = null;
        return true;
    }

    public boolean checkPasswords(String firstPassword, String secondPassword) {
        if (Objects.equals(firstPassword, secondPassword)) {
            return true;
        } else {
            System.out.println("\nHata: Şifreler Uyuşmuyor. Lütfen tekrar giriniz.");
            return false;
        }
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

