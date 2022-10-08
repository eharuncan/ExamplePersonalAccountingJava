package org.example.app;

import org.example.db.Database;

import java.sql.Date;
import java.util.Scanner;

public class ExpenseService {

    private final Database database;

    public ExpenseService(Database database) {
        this.database = database;
    }

    public void showExpenses() {

        System.out.println("asfdasdgagfdsg");
        System.out.println("asfdasdgagfdsg");
        System.out.println("asfdasdgagfdsg");
        System.out.println("asfdasdgagfdsg");
        System.out.println("asfdasdgagfdsg");
        System.out.println("asfdasdgagfdsg");
        System.out.println("asfdasdgagfdsg");

    }

    public boolean addExpense() {

        Expense newExpense = new Expense();

        Scanner readScreen = new Scanner(System.in);

        System.out.println("Harcama adını giriniz:");
        newExpense.setName(readScreen.nextLine());

        System.out.println("Harcama miktarını giriniz: (TL)");
        newExpense.setAmount(Double.valueOf(readScreen.nextLine()));

        System.out.println("Harcama tarihini giriniz:");
        newExpense.setDate(Date.valueOf(readScreen.nextLine()));

        System.out.println("Harcama kategorisini giriniz:");
        newExpense.setCategory(readScreen.nextLine());

        return true;

    }

    public boolean editExpense() {

        return true;

    }

    public boolean deleteExpense() {

        return true;

    }
}

