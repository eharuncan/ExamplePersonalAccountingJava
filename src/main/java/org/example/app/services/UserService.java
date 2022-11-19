package org.example.app.services;

import java.util.*;

import org.example.app.domain.User;
import org.example.app.enums.UserTypes;

import static org.example.app.App.expenseCategoryService;

public class UserService {
    private final List<User> userList;

    public UserService(List<User> userList) {
        this.userList = userList;
        register(new User("admin", "admin", "admin", "admin"));
        register(new User("customer1", "customer1", "1", "1"));
    }

    public List<User> getUsers() {
        return userList;
    }

    public User getUserById(Long userId) {
        return userList.stream()
                .filter(user -> Objects.equals(user.getId(), userId))
                .findFirst()
                .get();
    }

    public User getUserByEmailAndPassword(String email, String password) {
        return userList.stream()
                .filter(user -> Objects.equals(user.getEmail(), email) && Objects.equals(user.getPassword(), password))
                .findFirst()
                .get();
    }

    public User register(User newUser) {
        if (userList.size() == 0) {
            newUser.setId(1L);
        } else {
            User lastUser = userList.get(userList.size() - 1);
            newUser.setId(lastUser.getId() + 1);
        }
        if (Objects.equals(newUser.getName(), "admin")) {
            newUser.setType(UserTypes.ADMIN);
        } else {
            newUser.setType(UserTypes.CUSTOMER);
        }
        userList.add(newUser);
        if (newUser.getType() == UserTypes.CUSTOMER) {
            expenseCategoryService.addDefaultExpenseCategoriesOfUser(newUser.getId());
        }
        return newUser;
    }

    public User editUser(User newUser, Long id) {
        int index = getUsers().indexOf(getUserById(id));
        userList.set(index, newUser);
        return newUser;
    }

    public void deleteUser(Long userId) {
        if (userId != 1) {
            User foundUser = getUserById(userId);
            userList.remove(foundUser);
        }
    }

    public User login(User newUser) {
        return getUserByEmailAndPassword(newUser.getEmail(), newUser.getPassword());
    }

    public void logout(User user) {

    }

}

