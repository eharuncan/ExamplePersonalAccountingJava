package org.example.db;

import org.example.app.domain.Expense;
import org.example.app.domain.User;

import java.util.ArrayList;
import java.util.List;

public class Database {
    private List<User> userList = new ArrayList<>();
    private List<Expense> expenseList = new ArrayList<>();

    public List<User> getUserList() {
        return userList;
    }
    public void setUserList(List<User> userList) {
        this.userList = userList;
    }

    public List<Expense> getExpenseList() {
        return expenseList;
    }
    public void setExpenseList(List<Expense> expenseList) {
        this.expenseList = expenseList;
    }
}
