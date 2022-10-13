package org.example.app;

import org.example.app.services.ExpenseCategoryService;
import org.example.app.services.ExpenseService;
import org.example.app.services.UserService;

import org.example.app.ui.Common;

import org.example.db.Database;

import static java.lang.System.exit;

public class App {
    public static UserService userService;
    public static ExpenseService expenseService;
    public static ExpenseCategoryService expenseCategoryService;

    public static void main(String[] args) {
        Database database = connectDatabase();
        if(database==null){
            System.out.print("Database'e bağlanılamadı.");
            exit(0);
        }
        initializeServices(database);
        startUI();
    }

    private static Database connectDatabase(){
        Database database = new Database();
        String username = "admin";
        String password = "admin";
        if(database.connect(username, password)){
            return database;
        }else {
            return null;
        }
    }

    private static void initializeServices(Database database){
        expenseCategoryService = new ExpenseCategoryService(database.getExpenseCategoryList());
        userService = new UserService(database.getUserList());
        expenseService = new ExpenseService(database.getExpenseList());
    }

    private static void startUI(){
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