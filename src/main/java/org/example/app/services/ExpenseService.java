package org.example.app.services;

import org.example.app.domain.Expense;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class ExpenseService {
    private final List<Expense> expenseListDB;

    public ExpenseService(List<Expense> expenseListDB) {
        this.expenseListDB = expenseListDB;
    }

    public List<Expense> getExpenses() {
        return expenseListDB;
    }

    public List<Expense> getExpensesByUserId(Long userId) {
        return expenseListDB.stream()
                .filter(expense -> Objects.equals(expense.getUserId(), userId))
                .collect(Collectors.toList());
    }

    public Expense getExpenseByUserIdAndExpenseId(Long userId, Long expenseId) {
        return expenseListDB.stream()
                .filter(expense -> Objects.equals(expense.getUserId(), userId) && Objects.equals(expense.getId(), expenseId))
                .findFirst()
                .get();
    }

    public boolean addExpense(Long userId, String name, Double amount, Date date, Long categoryId) {
        long newExpenseId;
        List<Expense> expenseList = expenseListDB;
        if (expenseList.size() == 0) {
            newExpenseId = 1;
        } else {
            Expense lastExpense = expenseList.get(expenseList.size() - 1);
            newExpenseId = lastExpense.getId() + 1;
        }
        Expense newExpense = new Expense(userId, newExpenseId, name, amount, date, categoryId);

        expenseListDB.add(newExpense);
        return true;

    }

    public boolean editExpense(Long userId, Long id, String editedName, Double editedAmount, Date editedDate, Long editedCategoryId) {
        Expense expense = getExpenseByUserIdAndExpenseId(userId, id);
        int index = getExpenses().indexOf(expense);
        Expense editedExpense = new Expense(userId, id, editedName, editedAmount, editedDate, editedCategoryId);
        expenseListDB.set(index, editedExpense);
        return true;
    }

    public boolean deleteExpense(Long userId, Long expenseId) {
        Expense foundExpense = getExpenseByUserIdAndExpenseId(userId, expenseId);
        expenseListDB.remove(foundExpense);
        return true;
    }

    public Double getSumOfUserExpensesOfDay(Long userId, Date date) {
        LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        int year = localDate.getYear();
        int month = localDate.getMonthValue();
        int day = localDate.getDayOfMonth();

        List<Expense> currentUsersExpenseList = getExpensesByUserId(userId);
        List<Expense> resultList = currentUsersExpenseList.stream()
                .filter(expense ->
                        Objects.equals(expense.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate().getYear(), year)
                                && Objects.equals(expense.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate().getMonthValue(), month)
                                && Objects.equals(expense.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate().getDayOfMonth(), day)
                )
                .collect(Collectors.toList());
        return resultList.stream()
                .mapToDouble(Expense::getAmount)
                .sum();
    }

    public Double getSumOfUserExpensesOfMonth(Long userId, java.util.Date date) {
        LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        int year = localDate.getYear();
        int month = localDate.getMonthValue();

        List<Expense> currentUsersExpenseList = getExpensesByUserId(userId);
        List<Expense> resultList = currentUsersExpenseList.stream()
                .filter(expense ->
                        Objects.equals(expense.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate().getYear(), year)
                                && Objects.equals(expense.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate().getMonthValue(), month)
                )
                .collect(Collectors.toList());
        return resultList.stream()
                .mapToDouble(Expense::getAmount)
                .sum();
    }

    public Double getSumOfUserExpensesOfYear(Long userId, java.util.Date date) {
        LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        int year = localDate.getYear();

        List<Expense> currentUsersExpenseList = getExpensesByUserId(userId);
        List<Expense> resultList = currentUsersExpenseList.stream()
                .filter(expense ->
                        Objects.equals(expense.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate().getYear(), year)
                )
                .collect(Collectors.toList());
        return resultList.stream()
                .mapToDouble(Expense::getAmount)
                .sum();
    }
}

