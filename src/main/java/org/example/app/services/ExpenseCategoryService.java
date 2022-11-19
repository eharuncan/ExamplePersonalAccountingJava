package org.example.app.services;

import org.example.app.domain.ExpenseCategory;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class ExpenseCategoryService {
    private final List<ExpenseCategory> expenseCategoryListDB;
    private final String[] defaultExpenseCategoryNames = new String[]{"Çocuk", "Güvenlik", "Kitap", "Sağlık"};

    public ExpenseCategoryService(List<ExpenseCategory> expenseCategoryListDB) {
        this.expenseCategoryListDB = expenseCategoryListDB;
    }

    public List<ExpenseCategory> getExpenseCategories() {
        return expenseCategoryListDB;
    }

    public List<ExpenseCategory> getExpenseCategoriesOfUser(Long userId) {
        return expenseCategoryListDB.stream()
                .filter(expenseCategory -> Objects.equals(expenseCategory.getUserId(), userId))
                .collect(Collectors.toList());
    }

    public ExpenseCategory getExpenseCategory(Long id) {
        return expenseCategoryListDB.stream()
                .filter(expenseCategory ->  Objects.equals(expenseCategory.getId(), id))
                .findFirst()
                .get();
    }

    public ExpenseCategory getExpenseCategoryOfUser(Long userId, Long id) {
        return expenseCategoryListDB.stream()
                .filter(expenseCategory -> Objects.equals(expenseCategory.getUserId(), userId) && Objects.equals(expenseCategory.getId(), id))
                .findFirst()
                .get();
    }

    public ExpenseCategory addExpenseCategory(ExpenseCategory newExpenseCategory) {
        List<ExpenseCategory> expenseCategoryList = getExpenseCategoriesOfUser(newExpenseCategory.getUserId());
        if (expenseCategoryList.size() == 0) {
            newExpenseCategory.setId(1L);
        } else {
            ExpenseCategory lastExpenseCategory = expenseCategoryList.get(expenseCategoryList.size() - 1);
            newExpenseCategory.setId(lastExpenseCategory.getId() + 1);
        }
        expenseCategoryListDB.add(newExpenseCategory);
        return newExpenseCategory;
    }

    public ExpenseCategory editExpenseCategory(ExpenseCategory newExpenseCategory, Long id) {
        ExpenseCategory expenseCategory = getExpenseCategoryOfUser(newExpenseCategory.getUserId(), id);
        int index = getExpenseCategories().indexOf(expenseCategory);
        expenseCategoryListDB.set(index, newExpenseCategory);
        return newExpenseCategory;
    }

    public void deleteExpenseCategory(Long id) {
        ExpenseCategory foundExpenseCategory = getExpenseCategory(id);
        expenseCategoryListDB.remove(foundExpenseCategory);
    }

    public void addDefaultExpenseCategoriesOfUser(Long userId) {
        ExpenseCategory newExpenseCategory;
        for (String defaultExpenseCategoryName : defaultExpenseCategoryNames) {
            newExpenseCategory = new ExpenseCategory(userId, defaultExpenseCategoryName);
            addExpenseCategory(newExpenseCategory);
        }
    }

}
