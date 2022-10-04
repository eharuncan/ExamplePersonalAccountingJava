import { Meteor } from 'meteor/meteor';
import { Migrations } from 'meteor/percolate:migrations';
import './db/migrations';
import './expenses/expenses.methods';
import './expenses/expenses.publications';

/**
 * This is the server-side entry point
 */
Meteor.startup(() => {
  Migrations.migrateTo('latest');
});
