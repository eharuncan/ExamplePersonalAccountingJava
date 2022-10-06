import { Migrations } from 'meteor/percolate:migrations';
import { Accounts } from 'meteor/accounts-base';
import { ExpensesCollection } from '../expenses/expenses.collection';

Migrations.add({
  version: 1,
  name: 'Add a seed username and password.',
  up() {
    Accounts.createUser({
      username: 'fredmaia',
      password: 'abc123',
    });
  },
});

Migrations.add({
  version: 2,
  name: 'Add a few sample expenses.',
  up() {
    const createdAt = new Date();
    const { _id: userId } = Accounts.findUserByUsername('fredmaia');
    ExpensesCollection.insert({
      description: 'Meteor Hakkında Kitap',
      amount: "100",
      date: new Date(),
      userId,
      createdAt,
    });
    ExpensesCollection.insert({
      description: 'Telefona Kontör',
      amount: "150",
      date: new Date(),
      userId,
      createdAt,
    });
    ExpensesCollection.insert({
      description: 'Çanta',
      amount: "200",
      date: new Date(),
      userId,
      createdAt,
    });
  },
});
