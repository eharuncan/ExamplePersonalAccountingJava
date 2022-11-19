package org.example.app;

import org.example.app.db.Database;
import org.example.app.domain.User;
import org.example.app.services.ExpenseCategoryService;
import org.example.app.services.ExpenseService;
import org.example.app.services.UserService;
import org.example.app.ui.Ui;

import java.util.Objects;

import static java.lang.System.exit;

public class App {
    public static ExpenseCategoryService expenseCategoryService;
    public static UserService userService;
    public static ExpenseService expenseService;

    public static User currentUser;

    public static void main(String[] args) {
        Database database = new Database();

        expenseCategoryService = new ExpenseCategoryService(database.getExpenseCategoryList());
        userService = new UserService(database.getUserList());
        expenseService = new ExpenseService(database.getExpenseList());

        Ui.show();

        exit(0);
    }

    public static User loginUser(User newUser) {
        User loginUser = userService.login(newUser);
        if (loginUser != null){
            currentUser = loginUser;
            return loginUser;
        }else{
            return null;
        }
    }

    public static void logoutUser(User user) {
        userService.logout(user);
        currentUser = null;
    }

    public static boolean checkPasswords(String firstPassword, String secondPassword) {
        if (Objects.equals(firstPassword, secondPassword)) {
            return true;
        } else {
            System.out.println("\nHata: Şifreler Uyuşmuyor. Lütfen tekrar giriniz.");
            return false;
        }
    }
}