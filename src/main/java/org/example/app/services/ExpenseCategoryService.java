package org.example.app.services;

import org.example.app.domain.ExpenseCategory;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class ExpenseCategoryService {
    private final List <ExpenseCategory> expenseCategoryListDB;
    private final String[] defaultExpenseCategories = new String[] {"Çocuk","Güvenlik","Kitap","Sağlık"};

    public List<ExpenseCategory> getExpenseCategories() {
        return expenseCategoryListDB;
    }

    public ExpenseCategoryService(List <ExpenseCategory> expenseCategoryListDB) {
        this.expenseCategoryListDB = expenseCategoryListDB;
    }

    public List <ExpenseCategory> getExpenseCategoriesByUserId(Integer userId) {
        return expenseCategoryListDB.stream()
                .filter(expenseCategory -> Objects.equals(expenseCategory.getUserId(), userId))
                .collect(Collectors.toList());
    }

    public ExpenseCategory getExpenseCategoryByUserIdAndExpenseCategoryId(Integer userId, Integer expenseCategoryId) {
        return expenseCategoryListDB.stream()
                .filter(expenseCategory -> Objects.equals(expenseCategory.getUserId(), userId) && Objects.equals(expenseCategory.getId(), expenseCategoryId))
                .findFirst()
                .get();
    }

    public boolean addExpenseCategory(Integer userId, String expenseCategoryName) {
        int newExpenseCategoryId;
        List <ExpenseCategory> expenseCategoryList = getExpenseCategoriesByUserId(userId);
        if (expenseCategoryList.size() == 0){
            newExpenseCategoryId = 1;
        }else {
            ExpenseCategory lastExpenseCategory =  expenseCategoryList.get(expenseCategoryList.size()-1);
            newExpenseCategoryId = lastExpenseCategory.getId() + 1;
        }

        ExpenseCategory expenseCategory = new ExpenseCategory(userId, newExpenseCategoryId, expenseCategoryName);
        expenseCategoryListDB.add(expenseCategory);
        return true;
    }

    public boolean editExpenseCategory(ExpenseCategory expenseCategory, ExpenseCategory editedExpenseCategory) {
        if (validateExpenseCategory(editedExpenseCategory)) {
            int index = getExpenseCategories().indexOf(expenseCategory);
            expenseCategoryListDB.set(index, editedExpenseCategory);
            return true;
        } else {
            return false;
        }
    }

    public boolean deleteExpenseCategory(Integer userId, Integer expenseCategoryId) {
        ExpenseCategory foundExpenseCategory = getExpenseCategoryByUserIdAndExpenseCategoryId(userId, expenseCategoryId);
        expenseCategoryListDB.remove(foundExpenseCategory);
        return true;
    }

    public boolean addDefaultExpenseCategories(Integer userId){
        for (String expenseCategory:defaultExpenseCategories) {
            addExpenseCategory(userId,expenseCategory );
        }
        return true;
    }

    public boolean validateExpenseCategory(ExpenseCategory expenseCategory) {
        //todo: burası yazılacak
        return true;
    }
}
