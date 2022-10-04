import React, { Suspense } from 'react';
import { Meteor } from 'meteor/meteor';
import { render } from 'react-dom';
import { Spinner } from '@chakra-ui/react';

import '../api/expenses/expenses.methods';
import { Routes } from './Routes';

/**
 * This is the client-side entry point
 */
Meteor.startup(() => {
  document.documentElement.setAttribute('lang', 'tr');
  const rootElement = document.getElementById('react-target');
  render(
    <Suspense fallback={<Spinner />}>
      <Routes />
    </Suspense>,
    rootElement
  );
});
