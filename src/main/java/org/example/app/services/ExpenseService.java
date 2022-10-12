package org.example.app.services;

import org.example.app.domain.Expense;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static org.example.app.utils.Date.dateFormatter;

public class ExpenseService {

    private final List <Expense> expenseListDB;

    public ExpenseService(List <Expense> expenseListDB) {
        this.expenseListDB = expenseListDB;
    }

    public List<Expense> getExpenses() {
        return expenseListDB;
    }

    public List<Expense> getExpensesByUserId(Integer userId) {
        return expenseListDB.stream()
                .filter(expense -> Objects.equals(expense.getUserId(), userId))
                .collect(Collectors.toList());
    }

    public Expense getExpenseByUserIdAndExpenseId(Integer userId, Integer expenseId) {
        return expenseListDB.stream()
                .filter(expense -> Objects.equals(expense.getUserId(), userId) && Objects.equals(expense.getId(), expenseId))
                .findFirst()
                .get();
    }

    public boolean addExpense(Integer userId, Expense newExpense) {
        if (validateExpense(newExpense)) {
            newExpense.setUserId(userId);

            int newExpenseId;
            List <Expense> expenseList = expenseListDB;
            if (expenseList.size() == 0){
                newExpenseId = 1;
            }else {
                Expense lastExpense =  expenseList.get(expenseList.size()-1);
                newExpenseId = lastExpense.getId() + 1;
            }
            newExpense.setId(newExpenseId);

            expenseListDB.add(newExpense);
            return true;
        } else {
            return false;
        }
    }

    public boolean editExpense(Integer userId, Integer expenseId, Expense editedExpense) {
        if (validateExpense(editedExpense)) {
            int index = getExpenses().indexOf(getExpenseByUserIdAndExpenseId(userId, expenseId));
            expenseListDB.set(index, editedExpense);
            return true;
        } else {
            return false;
        }
    }

    public boolean deleteExpense(Integer userId, Integer expenseId) {
        Expense foundExpense = getExpenseByUserIdAndExpenseId(userId, expenseId);
        expenseListDB.remove(foundExpense);
        return true;
    }

    public boolean validateExpense(Expense expense) {
        //todo: burası yazılacak
        return true;
    }

    public Double getSumOfUserExpensesOfDay(Integer userId, Date date) {
        LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        Integer year = localDate.getYear();
        Integer month = localDate.getMonthValue();
        Integer day = localDate.getDayOfMonth();

        List <Expense> currentUsersExpenseList = getExpensesByUserId(userId);
        List <Expense> resultList = currentUsersExpenseList.stream()
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

    public Double getSumOfUserExpensesOfMonth(Integer userId, java.util.Date date) {
        LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        Integer year = localDate.getYear();
        Integer month = localDate.getMonthValue();
        Integer day = localDate.getDayOfMonth();

        List <Expense> currentUsersExpenseList = getExpensesByUserId(userId);
        List <Expense> resultList = currentUsersExpenseList.stream()
                .filter(expense ->
                                Objects.equals(expense.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate().getYear(), year)
                                        && Objects.equals(expense.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate().getMonthValue(), month)
                )
                .collect(Collectors.toList());
        return resultList.stream()
                .mapToDouble(Expense::getAmount)
                .sum();
    }

    public Double getSumOfUserExpensesOfYear(Integer userId, java.util.Date date) {
        LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        Integer year = localDate.getYear();

        List <Expense> currentUsersExpenseList = getExpensesByUserId(userId);
        List <Expense> resultList = currentUsersExpenseList.stream()
                .filter(expense ->
                        Objects.equals(expense.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate().getYear(), year)
                )
                .collect(Collectors.toList());
        return resultList.stream()
                .mapToDouble(Expense::getAmount)
                .sum();
    }
}

