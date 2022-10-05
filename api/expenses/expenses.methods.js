import { check } from 'meteor/check';
import { ExpensesCollection } from './expenses.collection';
import { Meteor } from 'meteor/meteor';
import { checkLoggedIn } from '../common/auth';
import {isInteger} from "formik";

/**
 * Insert a expense for the logged user.
 * @param {{ description: String, amount: String, date: Date, category: String }}
 * @throws Will throw an error if user is not logged in.
 */
const insertExpense = ({ description, amount, date, category }) => {
  check(description, String);
  // check(amount, String);
  // check(date, Date );
  // check(category, String );
  checkLoggedIn();
  ExpensesCollection.insert({
    description,
    amount,
    date,
    category,
    userId: Meteor.userId(),
    createdAt: new Date(),
  });
};

/**
 * Check if user is logged in and is the expense owner.
 * @param {{ expenseId: String }}
 * @throws Will throw an error if user is not logged in or is not the expense owner.
 */
const checkExpenseOwner = ({ expenseId }) => {
  check(expenseId, String);
  checkLoggedIn();
  const expense = ExpensesCollection.findOne({
    _id: expenseId,
    userId: Meteor.userId(),
  });
  if (!expense) {
    throw new Meteor.Error('Error', 'Access denied.');
  }
};

/**
 * Remove a expense.
 * @param {{ expenseId: String }}
 * @throws Will throw an error if user is not logged in or is not the expense owner.
 */
export const removeExpense = ({ expenseId }) => {
  checkExpenseOwner({ expenseId });
  ExpensesCollection.remove(expenseId);
};

/**
 * Toggle expense as done or not.
 * @param {{ expenseId: String }}
 * @throws Will throw an error if user is not logged in or is not the expense owner.
 */
const toggleExpenseDone = ({ expenseId }) => {
  checkExpenseOwner({ expenseId });
  const expense = ExpensesCollection.findOne(expenseId);
  ExpensesCollection.update({ _id: expenseId }, { $set: { done: !expense.done } });
};

/**
 * Register expense API methods.
 */
Meteor.methods({
  insertExpense,
  removeExpense,
  toggleExpenseDone,
});
