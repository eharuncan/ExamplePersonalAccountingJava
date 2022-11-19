package org.example.app;

import org.example.app.db.Database;
import org.example.app.domain.User;
import org.example.app.services.ExpenseCategoryService;
import org.example.app.services.ExpenseService;
import org.example.app.services.UserService;
import org.example.app.menus.CommonMenu;

import java.util.Objects;

import static java.lang.System.exit;
import static org.example.app.utils.Utils.screenScanner;

public class App {
    public static ExpenseCategoryService expenseCategoryService;
    public static UserService userService;
    public static ExpenseService expenseService;

    public static User currentUser;

    public static void main(String[] args) {
        Database database = new Database();
        startServices(database);
        startUi();
    }

    private static void startServices(Database database){
        expenseCategoryService = new ExpenseCategoryService(database.getExpenseCategoryList());
        userService = new UserService(database.getUserList());
        expenseService = new ExpenseService(database.getExpenseList());
    }

    private static void startUi(){
        System.out.println("\nŞAHSİ MUHASEBEM - HARCAMALARINIZI TAKİP EDİN !");
        showMenus();
        System.out.println("\nUygulama başarıyla kapatıldı.");
        exit(0);
    }

    private static void showMenus(){
        CommonMenu commonMenu = new CommonMenu();
        commonMenu.selector();
    }

    public static String getInput(Object defaultValue) {
        String input = screenScanner.nextLine();
        if (defaultValue == null) {
            return input;
        } else {
            if (Objects.equals(input, "")) {
                return (String) defaultValue;
            } else {
                return input;
            }
        }
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