package org.example.app;

import org.example.app.services.ExpenseService;
import org.example.app.services.UserService;
import org.example.app.ui.Common;
import org.example.db.Database;

public class App {

    public static void main(String[] args) {
        Database database = new Database();

        UserService userService = new UserService(database);
        ExpenseService expenseService = new ExpenseService(database, userService);
        Common common = new Common(userService, expenseService);

        System.out.println("\nŞAHSİ MUHASEBEM - HARCAMALARINIZI TAKİP EDİN !");

        common.menuSelector();

        System.out.println("\nUygulama başarıyla kapatıldı.");
    }

}