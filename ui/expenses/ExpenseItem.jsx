import React, {memo} from 'react';
import {Box, Button, HStack, Stack, Checkbox} from '@chakra-ui/react';
import {Grid, GridItem} from '@chakra-ui/react'

export const ExpenseItem = memo(({expense, onMarkAsDone, onDelete}) => (
    <HStack mt={4}>
        <Grid templateColumns='repeat(5, 1fr)' gap={6}>
            <GridItem w="100%">
                {expense.description}
            </GridItem>
            <GridItem w="100%">
                {expense.amount}
            </GridItem>
            <GridItem w="100%">
                {expense.date}
            </GridItem>
            <GridItem w="100%">
                {expense.category}
            </GridItem>
            <GridItem w="100%" justify="flex-end" direction="row">
                <Button
                    colorScheme="red"
                    variant="outline"
                    size="xs"
                    onClick={() => onDelete(expense._id)}
                >
                    Sil
                </Button>
            </GridItem>
        </Grid>
    </HStack>
));
