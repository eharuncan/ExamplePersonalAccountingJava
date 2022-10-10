package org.example.app;

import org.example.app.services.ExpenseService;
import org.example.app.services.UserService;
import org.example.app.ui.Common;
import org.example.db.Database;

public class App {

    public static UserService userService;
    public static ExpenseService expenseService;

    public static void main(String[] args) {
        Database database = new Database();

        userService = new UserService(database);
        expenseService = new ExpenseService(database);

        Common common = new Common();

        System.out.println("\nŞAHSİ MUHASEBEM - HARCAMALARINIZI TAKİP EDİN !");

        common.menuSelector();

        System.out.println("\nUygulama başarıyla kapatıldı.");
    }

}