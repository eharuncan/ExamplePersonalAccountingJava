import { Meteor } from 'meteor/meteor';
import React from 'react';
import {
  FormControl,
  Input,
  Select,
  Button,
  FormErrorMessage,
  Box,
  InputGroup,
  InputRightElement,
} from '@chakra-ui/react';
import { object, string } from 'yup';
import { useFormik } from 'formik';
import { ErrorStatus } from '../common/ErrorStatus';

export const ExpenseForm = () => {
  const validationSchema = object({
    description: string('Lütfen Harcama adı giriniz.').required(
      'Harcama adı gereklidir.'
    ),
    amount: string('Lütfen Harcama miktarı giriniz.').required(
        'Harcama miktarı gereklidir.'
    ),
    date: string('Lütfen Harcama tarihi giriniz.').required(
        'Harcama tarihi gereklidir.'
    ),
  });

  const onSubmit = (values, actions) => {
    const description = values.description.trim();
    const amount = values.amount;
    const date = values.date;
    const category = values.category;
    Meteor.call('insertExpense', { description, amount, date, category }, err => {
      if (err) {
        const errorMessage = err?.reason || 'Hata, Lütfen yeniden deneyiniz...';
        actions.setStatus(errorMessage);
      } else {
        actions.resetForm();
      }
      actions.setSubmitting(false);
    });
  };

  var newDate = new Date().toString();

  const formik = useFormik({
    initialValues: { description: '', amount: '', date: newDate, category: ''
    },
    initialStatus: null,
    validationSchema,
    onSubmit,
  });

  return (
    <Box>
      <ErrorStatus status={formik.status} />
      <form onSubmit={formik.handleSubmit}>
        <InputGroup size="md">
          <FormControl
            isInvalid={formik.errors.description && formik.touched.description}
          >
            <label>
              Harcama adı:
            <Input
              h="2.6rem"
              pr="6rem"
              name="description"
              onChange={formik.handleChange}
              value={formik.values.description}
              placeholder="Harcama adı giriniz."
            />
            </label>
            <label>
              Miktarı (TL):
            <Input
                h="2.6rem"
                pr="6rem"
                name="amount"
                onChange={formik.handleChange}
                value={formik.values.amount}
                placeholder="Harcama miktarı giriniz. Örneğin: 500"
            />
            </label>
            <label>
              Tarihi:
            <Input
                h="2.6rem"
                pr="6rem"
                name="date"
                onChange={formik.handleChange}
                value={formik.values.date}
                placeholder="Harcama tarihini istenilen formatta giriniz."
            />
            </label>
            <label>
              Kategorisi:
              <Select
                  h="2.6rem"
                  pr="6rem"
                  name="category"
                  onChange={formik.handleChange}
                  value={formik.values.category}
                  placeholder="Kategori seçebilirsiniz.">
                <option value='guvenlik'>Güvenlik</option>
                <option value='otomotiv'>Otomotiv</option>
                <option value='saglik'>Sağlık</option>
                <option value='ulasim'>Ulaşım</option>
              </Select>
            </label>
            <br></br>
            <Button
                h="2.5rem"
                size="sm"
                bg="blue.600"
                color="white"
                type="submit"
                isLoading={formik.isSubmitting}
                colorScheme="blue"
            >
              Harcama Ekle
            </Button>

            <FormErrorMessage>{formik.errors.description}</FormErrorMessage>
            <FormErrorMessage>{formik.errors.amount}</FormErrorMessage>
            <FormErrorMessage>{formik.errors.date}</FormErrorMessage>
            <FormErrorMessage>{formik.errors.category}</FormErrorMessage>
          </FormControl>
        </InputGroup>
      </form>
    </Box>
  );
};
