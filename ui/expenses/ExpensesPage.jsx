import { Meteor } from 'meteor/meteor';
import React, { useState } from 'react';
import { ExpenseForm } from './ExpenseForm';
import { useTracker, useFind, useSubscribe } from 'meteor/react-meteor-data';
import { ExpensesHeader } from './ExpensesHeader';
import { ExpenseItems } from './ExpenseItems';
import { ExpensesCollection } from '../../api/expenses/expenses.collection';

/* eslint-disable import/no-default-export */
export default function ExpensesPage() {
  const [hideDone, setHideDone] = useState(false);
  const isLoading = useSubscribe('expensesByLoggedUser');
  const userId = useTracker(() => Meteor.userId());
  const filter = hideDone ? { done: { $ne: true }, userId } : { userId };
  const expenses = useFind(
    () => ExpensesCollection.find(filter, { sort: { createdAt: -1 } }),
    [hideDone]
  );
  const pendingCount = ExpensesCollection.find({
    done: { $ne: true },
    userId,
  }).count();

  return (
    <>
      <ExpensesHeader />
      <ExpenseForm />
      <ExpenseItems
        isLoading={isLoading}
        expenses={expenses}
        pendingCount={pendingCount}
        hideDone={hideDone}
        setHideDone={setHideDone}
      />
    </>
  );
}
