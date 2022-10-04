import {
  Box,
  Button,
  HStack,
  Spinner,
  Stack,
  Text,
  useColorModeValue,
} from '@chakra-ui/react';
import { ExpenseItem } from './ExpenseItem';
import React from 'react';
import { Meteor } from 'meteor/meteor';

export const ExpenseItems = ({
  expenses,
  pendingCount,
  hideDone,
  setHideDone,
  isLoading,
}) => (
  <Box
    mt={8}
    py={{ base: 2 }}
    px={{ base: 4 }}
    pb={{ base: 4 }}
    border={1}
    borderStyle="solid"
    borderRadius="md"
    borderColor={useColorModeValue('gray.400', 'gray.700')}
  >
    <HStack mt={2}>
      <Box w="70%">
        <Text
          as="span"
          color={useColorModeValue('gray.600', 'gray.400')}
          fontSize="xs"
        >
          You have {expenses.length} {expenses.length === 1 ? 'expense ' : 'expenses '}
          and {pendingCount || 0} pending.
        </Text>
      </Box>
      <Stack w="30%" justify="flex-end" direction="row">
        <Button
          bg="teal.600"
          color="white"
          colorScheme="teal"
          size="xs"
          onClick={() => setHideDone(!hideDone)}
        >
          {hideDone ? 'Show All Expenses' : 'Show Pending'}
        </Button>
      </Stack>
    </HStack>
    {isLoading() ? (
      <Spinner />
    ) : (
      <>
        {expenses.map(expense => (
          <ExpenseItem
            key={expense._id}
            expense={expense}
            onMarkAsDone={expenseId => Meteor.call('toggleExpenseDone', { expenseId })}
            onDelete={expenseId => Meteor.call('removeExpense', { expenseId })}
          />
        ))}
      </>
    )}
  </Box>
);
