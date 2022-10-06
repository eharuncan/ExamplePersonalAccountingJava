import {
  Box,
  Button, Checkbox, Grid, GridItem,
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
      <Box w="100%">
        <Text
          as="span"
          color={useColorModeValue('gray.600', 'gray.400')}
          fontSize="xs"
        >
          Toplamda {expenses.length} adet harcamanız harcamanız var.
        </Text>
      </Box>
    </HStack>
    {isLoading() ? (
      <Spinner />
    ) : (
      <>

        <Grid templateColumns='repeat(5, 1fr)' gap={6} mt={4}>
          <GridItem w="100%">
            Harcama adı:
          </GridItem>
          <GridItem w="100%">
            Miktarı:
          </GridItem>
          <GridItem w="100%">
            Tarihi:
          </GridItem>
          <GridItem w="100%">
            Kategori
          </GridItem>
          <GridItem w="100%">
            İşlem:
          </GridItem>
        </Grid>

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
