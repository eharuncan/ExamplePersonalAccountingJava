package org.example.app.db;

import org.example.app.domain.Expense;
import org.example.app.domain.ExpenseCategory;
import org.example.app.domain.User;

import java.util.ArrayList;
import java.util.List;

public class Database {

    private static List<User> userList = new ArrayList<>();
    private static List<Expense> expenseList = new ArrayList<>();
    private static List<ExpenseCategory> expenseCategoryList = new ArrayList<>();

    public List<User> getUserList() {
        return userList;
    }

    public void setUserList(List<User> userList) {
        Database.userList = userList;
    }

    public List<Expense> getExpenseList() {
        return expenseList;
    }

    public void setExpenseList(List<Expense> expenseList) {
        Database.expenseList = expenseList;
    }

    public List<ExpenseCategory> getExpenseCategoryList() {
        return expenseCategoryList;
    }

    public void setExpenseCategoryList(List<ExpenseCategory> expenseCategoryList) {
        Database.expenseCategoryList = expenseCategoryList;
    }
}
