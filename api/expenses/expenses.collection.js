import { Mongo } from 'meteor/mongo';
import SimpleSchema from 'simpl-schema';

export const ExpensesCollection = new Mongo.Collection('expenses');

const ExpensesSchema = new SimpleSchema({
  description: String,
  amount: String,
  date: String,
  category: String,
  done: {
    type: Boolean,
    defaultValue: false,
  },
  userId: {
    type: String,
    regEx: SimpleSchema.RegEx.Id,
  },
  createdAt: Date,
});

ExpensesCollection.attachSchema(ExpensesSchema);
