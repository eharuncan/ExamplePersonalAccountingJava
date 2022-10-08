package org.example.app;

import org.example.db.Database;

import java.util.List;

public class ExpenseService {

    private final Database database;

    public ExpenseService(Database database) {
        this.database = database;
    }

    public List<Expense> getAllExpenses() {

        return database.getExpenseList();

    }

    public boolean addExpense(Expense expense) {

        if (validateExpense(expense)) {
            database.getExpenseList().add(expense);
            return true;
        } else {
            return false;
        }

    }

    public boolean editExpense(Integer index, Expense expense) {

        if (validateExpense(expense)) {
            database.getExpenseList().set(index, expense);
            return true;
        } else {
            return false;
        }

    }

    public Expense getExpenseByIndex(Integer index) {

        return database.getExpenseList().get(index);

    }

    public boolean deleteExpense(Integer index) {

        Expense foundExpense = getExpenseByIndex(index);
        database.getExpenseList().remove(foundExpense);
        return true;

    }

    public boolean validateExpense(Expense expense) {

        //todo: burası yazılacak
        return true;

    }
}

