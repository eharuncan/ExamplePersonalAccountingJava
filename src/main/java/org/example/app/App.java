package org.example.app;

import org.example.app.db.Database;
import org.example.app.domain.User;
import org.example.app.services.ExpenseCategoryService;
import org.example.app.services.ExpenseService;
import org.example.app.services.UserService;
import org.example.app.ui.Common;

import static java.lang.System.exit;

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
        Common common = new Common();
        common.menuSelector();
    }
}