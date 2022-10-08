package org.example.app.domain;

import org.example.db.Database;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class ExpenseService {

    private final Database database;
    private final UserService userService;

    public ExpenseService(Database database) {
        this.database = database;
        userService = new UserService(database);
    }

    public List<Expense> getAllExpenses() {

        return database.getExpenseList();

    }

    public List<Expense> getAllExpensesOfCurrentUser() {

        return database.getExpenseList().stream().filter(expense -> Objects.equals(expense.getUserId(), userService.getCurrentUser().getId())).collect(Collectors.toList());

    }

    public boolean addExpense(Expense newExpense) {

        if (validateExpense(newExpense)) {
            newExpense.setUserId(userService.getCurrentUser().getId());
            newExpense.setId(getAllExpensesOfCurrentUser().size());
            database.getExpenseList().add(newExpense);
            return true;
        } else {
            return false;
        }

    }

    public boolean editExpense(Integer expenseId, Expense editedExpense) {

        System.out.println("buraya girdi. ExpenseId: " + expenseId + " editedExpense.name: " + editedExpense.getName() );

        if (validateExpense(editedExpense)) {
            int index = getAllExpenses().indexOf(getExpenseById(expenseId));
            database.getExpenseList().set(index, editedExpense);
            return true;
        } else {
            return false;
        }

    }

    public Expense getExpenseById(Integer expenseId) {

        List<Expense> expenseList = database.getExpenseList();

        return expenseList.stream().filter(expense -> Objects.equals(expense.getUserId(), userService.getCurrentUser().getId()) && Objects.equals(expense.getId(), expenseId)).findFirst().get();

    }

    public boolean deleteExpense(Integer expenseId) {

        Expense foundExpense = getExpenseById(expenseId);
        database.getExpenseList().remove(foundExpense);
        return true;

    }

    public boolean validateExpense(Expense expense) {

        //todo: burası yazılacak
        return true;

    }
}

