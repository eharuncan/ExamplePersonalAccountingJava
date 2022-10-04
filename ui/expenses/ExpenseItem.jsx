import React, { memo } from 'react';
import { Box, Button, HStack, Stack, Checkbox } from '@chakra-ui/react';

export const ExpenseItem = memo(({ expense, onMarkAsDone, onDelete }) => (
  <HStack mt={4}>
    <Box w="80%">
      <Checkbox
        colorScheme="green"
        isChecked={expense.done}
        onChange={() => onMarkAsDone(expense._id)}
      >
        {expense.description}
      </Checkbox>
    </Box>
    <Stack w="20%" justify="flex-end" direction="row">
      <Button
        colorScheme="red"
        variant="outline"
        size="xs"
        onClick={() => onDelete(expense._id)}
      >
        Remove
      </Button>
    </Stack>
  </HStack>
));
