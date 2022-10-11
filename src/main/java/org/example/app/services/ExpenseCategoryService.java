package org.example.app.services;

import org.example.app.domain.Expense;
import org.example.app.domain.ExpenseCategory;
import org.example.db.Database;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class ExpenseCategoryService {

    private final Database database;

    private final List<String> defaultExpenseCategoryList = new ArrayList<>();

    public List<ExpenseCategory> getExpenseCategories() {
        return database.getExpenseCategoryList();
    }

    public ExpenseCategoryService(Database database) {
        this.database = database;
    }

    public List <ExpenseCategory> getExpenseCategoriesByUserId(Integer userId) {
        return database.getExpenseCategoryList().stream()
                .filter(expenseCategory -> Objects.equals(expenseCategory.getUserId(), userId))
                .collect(Collectors.toList());
    }

    public ExpenseCategory getExpenseCategoryByUserIdAndIndex(Integer userId, Integer index) {
        return getExpenseCategoriesByUserId(userId).get(index);
    }

    public ExpenseCategory getExpenseCategoryByUserIdAndExpenseCategoryId(Integer userId, Integer expenseCategoryId) {
        List<ExpenseCategory> expenseCategoryList = database.getExpenseCategoryList();
        return expenseCategoryList.stream()
                .filter(expenseCategory -> Objects.equals(expenseCategory.getUserId(), userId) && Objects.equals(expenseCategory.getId(), expenseCategoryId))
                .findFirst()
                .get();
    }

    public boolean addExpenseCategoryByUserId(Integer userId, String expenseName) {
        Integer newExpenseCategoryId = getExpenseCategoriesByUserId(userId).size();
        ExpenseCategory expenseCategory = new ExpenseCategory(userId, newExpenseCategoryId, expenseName);
        database.getExpenseCategoryList().add(expenseCategory);
        return true;
    }

    public boolean editExpenseCategoryByUserIdAndExpenseCategoryId(Integer userId, Integer expenseCategoryId, ExpenseCategory editedExpenseCategory) {
        if (validateExpenseCategory(editedExpenseCategory)) {
            int index = getExpenseCategories().indexOf(getExpenseCategoryByUserIdAndExpenseCategoryId(userId, expenseCategoryId));
            database.getExpenseCategoryList().set(index, editedExpenseCategory);
            return true;
        } else {
            return false;
        }
    }

    public boolean deleteExpenseCategoryByUserId(Integer userId, Integer expenseCategoryId) {
        ExpenseCategory foundExpenseCategory = getExpenseCategoryByUserIdAndExpenseCategoryId(userId, expenseCategoryId);
        database.getExpenseCategoryList().remove(foundExpenseCategory);
        return true;
    }

    public boolean validateExpenseCategory(ExpenseCategory expenseCategory) {
        //todo: burası yazılacak
        return true;
    }
}
