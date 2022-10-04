import { Meteor } from 'meteor/meteor';
import { ExpensesCollection } from './expenses.collection';

Meteor.publish('expensesByLoggedUser', function publishExpenses() {
  return ExpensesCollection.find({ userId: this.userId });
});
