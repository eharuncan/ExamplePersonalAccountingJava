package org.example.app.services;

import org.example.app.domain.ExpenseCategory;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class ExpenseCategoryService {
    private final List<ExpenseCategory> expenseCategoryListDB;
    private final String[] defaultExpenseCategories = new String[]{"Çocuk", "Güvenlik", "Kitap", "Sağlık"};

    public ExpenseCategoryService(List<ExpenseCategory> expenseCategoryListDB) {
        this.expenseCategoryListDB = expenseCategoryListDB;
    }

    public List<ExpenseCategory> getExpenseCategories() {
        return expenseCategoryListDB;
    }

    public List<ExpenseCategory> getExpenseCategoriesByUserId(Long userId) {
        return expenseCategoryListDB.stream()
                .filter(expenseCategory -> Objects.equals(expenseCategory.getUserId(), userId))
                .collect(Collectors.toList());
    }

    public ExpenseCategory getExpenseCategoryByUserIdAndExpenseCategoryId(Long userId, Long expenseCategoryId) {
        return expenseCategoryListDB.stream()
                .filter(expenseCategory -> Objects.equals(expenseCategory.getUserId(), userId) && Objects.equals(expenseCategory.getId(), expenseCategoryId))
                .findFirst()
                .get();
    }

    public boolean addExpenseCategory(Long userId, String expenseCategoryName) {
        long newExpenseCategoryId;
        List<ExpenseCategory> expenseCategoryList = getExpenseCategoriesByUserId(userId);
        if (expenseCategoryList.size() == 0) {
            newExpenseCategoryId = 1;
        } else {
            ExpenseCategory lastExpenseCategory = expenseCategoryList.get(expenseCategoryList.size() - 1);
            newExpenseCategoryId = lastExpenseCategory.getId() + 1;
        }

        ExpenseCategory expenseCategory = new ExpenseCategory(userId, newExpenseCategoryId, expenseCategoryName);
        expenseCategoryListDB.add(expenseCategory);
        return true;
    }

    public boolean editExpenseCategory(Long userId, Long id, String editedName) {
        ExpenseCategory expenseCategory = getExpenseCategoryByUserIdAndExpenseCategoryId(userId, id);
        int index = getExpenseCategories().indexOf(expenseCategory);
        ExpenseCategory editedExpenseCategory = new ExpenseCategory(userId, id, editedName);
        expenseCategoryListDB.set(index, editedExpenseCategory);
        return true;
    }

    public boolean deleteExpenseCategory(Long userId, Long expenseCategoryId) {
        ExpenseCategory foundExpenseCategory = getExpenseCategoryByUserIdAndExpenseCategoryId(userId, expenseCategoryId);
        expenseCategoryListDB.remove(foundExpenseCategory);
        return true;
    }

    public boolean addDefaultExpenseCategories(Long userId) {
        for (String expenseCategory : defaultExpenseCategories) {
            addExpenseCategory(userId, expenseCategory);
        }
        return true;
    }

}
