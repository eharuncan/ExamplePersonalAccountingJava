package org.example.app.services;

import org.example.app.domain.Expense;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class ExpenseService {
    private final List<Expense> expenseList;

    public ExpenseService(List<Expense> expenseList) {
        this.expenseList = expenseList;
    }

    public List<Expense> getExpenses() {
        return expenseList;
    }

    public List<Expense> getExpensesOfUser(Long userId) {
        return expenseList.stream()
                .filter(expense -> Objects.equals(expense.getUserId(), userId))
                .collect(Collectors.toList());
    }

    public Expense getExpenseById(Long expenseId) {
        return expenseList.stream()
                .filter(expense -> Objects.equals(expense.getId(), expenseId))
                .findFirst()
                .get();
    }

    public Expense addExpenseOfUser(Expense newExpense) {
        if (expenseList.size() == 0) {
            newExpense.setId(1L);
        } else {
            Expense lastExpense = expenseList.get(expenseList.size() - 1);
            newExpense.setId(lastExpense.getId() + 1);
        }
        expenseList.add(newExpense);
        return newExpense;
    }

    public Expense editExpenseOfUser(Expense newExpense, Long id) {
        Expense foundExpense = getExpenseById(id);
        int index = getExpenses().indexOf(foundExpense);
        expenseList.set(index, newExpense);
        return newExpense;
    }

    public void deleteExpenseOfUser(Long id) {
        Expense foundExpense = getExpenseById(id);
        expenseList.remove(foundExpense);
    }

    public Double getSumOfUserExpensesOfDay(Long userId, Date date) {
        LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        int year = localDate.getYear();
        int month = localDate.getMonthValue();
        int day = localDate.getDayOfMonth();
        List<Expense> currentUsersExpenseList = getExpensesOfUser(userId);
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
        List<Expense> currentUsersExpenseList = getExpensesOfUser(userId);
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
        List<Expense> currentUsersExpenseList = getExpensesOfUser(userId);
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

