package org.example.app.db;

import org.example.app.domain.Expense;
import org.example.app.domain.ExpenseCategory;
import org.example.app.domain.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Database {
    private static Boolean connectStatus = false;

    private static final String username = "admin";
    private static final String password = "admin";

    private static List<User> userList = new ArrayList<>();
    private static List<Expense> expenseList = new ArrayList<>();
    private static List<ExpenseCategory> expenseCategoryList = new ArrayList<>();

    public static Boolean connect(String username, String password) {
        if (Objects.equals(Database.username, username) && Objects.equals(Database.password, password)) {
            connectStatus = true;
            return true;
        } else {
            connectStatus = false;
            return false;
        }
    }

    public List<User> getUserList() {
        if (connectStatus)
            return userList;
        return null;
    }

    public void setUserList(List<User> userList) {
        if (connectStatus)
            Database.userList = userList;
    }

    public List<Expense> getExpenseList() {
        if (connectStatus)
            return expenseList;
        return null;
    }

    public void setExpenseList(List<Expense> expenseList) {
        if (connectStatus)
            Database.expenseList = expenseList;
    }

    public List<ExpenseCategory> getExpenseCategoryList() {
        if (connectStatus)
            return expenseCategoryList;
        return null;
    }

    public void setExpenseCategoryList(List<ExpenseCategory> expenseCategoryList) {
        if (connectStatus)
            Database.expenseCategoryList = expenseCategoryList;
    }
}
