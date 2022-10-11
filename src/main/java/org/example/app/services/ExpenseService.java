package org.example.app.services;

import org.example.app.domain.Expense;

import org.example.db.Database;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static org.example.app.utils.Date.dateFormatter;

public class ExpenseService {

    private final Database database;

    public ExpenseService(Database database) {
        this.database = database;
    }

    public List<Expense> getExpenses() {
        return database.getExpenseList();
    }

    public List<Expense> getExpensesByUserId(Integer userId) {
        return database.getExpenseList().stream()
                .filter(expense -> Objects.equals(expense.getUserId(), userId))
                .collect(Collectors.toList());
    }

    public Expense getExpenseByUserIdAndExpenseId(Integer userId, Integer expenseId) {
        List<Expense> expenseList = database.getExpenseList();
        return expenseList.stream()
                .filter(expense -> Objects.equals(expense.getUserId(), userId) && Objects.equals(expense.getId(), expenseId))
                .findFirst()
                .get();
    }

    public boolean addExpense(Integer userId, Expense newExpense) {
        if (validateExpense(newExpense)) {
            newExpense.setUserId(userId);

            int newExpenseId;
            List <Expense> expenseList = database.getExpenseList();
            if (expenseList.size() == 0){
                newExpenseId = 0;
            }else {
                Expense lastExpense =  expenseList.get(expenseList.size()-1);
                newExpenseId = lastExpense.getId() + 1;
            }
            newExpense.setId(newExpenseId);

            database.getExpenseList().add(newExpense);
            return true;
        } else {
            return false;
        }
    }

    public boolean editExpense(Integer userId, Integer expenseId, Expense editedExpense) {
        if (validateExpense(editedExpense)) {
            int index = getExpenses().indexOf(getExpenseByUserIdAndExpenseId(userId, expenseId));
            database.getExpenseList().set(index, editedExpense);
            return true;
        } else {
            return false;
        }
    }

    public boolean deleteExpense(Integer userId, Integer expenseId) {
        Expense foundExpense = getExpenseByUserIdAndExpenseId(userId, expenseId);
        database.getExpenseList().remove(foundExpense);
        return true;
    }

    public boolean validateExpense(Expense expense) {
        //todo: burası yazılacak
        return true;
    }

    public Double getSumOfExpensesOfDateByUserId(Integer userId, java.util.Date date) {
        List <Expense> currentUsersExpenseList = getExpensesByUserId(userId);
        List <Expense> resultList = currentUsersExpenseList.stream()
                .filter(expense -> Objects.equals(dateFormatter.format(expense.getDate()), dateFormatter.format(date)))
                .collect(Collectors.toList());
        return resultList.stream()
                .mapToDouble(Expense::getAmount)
                .sum();
    }
}

