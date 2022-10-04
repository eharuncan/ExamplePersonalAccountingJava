import {
  Box,
  Stack,
  Text,
  ButtonGroup,
  IconButton,
  Divider,
  useColorModeValue,
} from '@chakra-ui/react';
import React from 'react';
import { FaGithub } from '@react-icons/all-files/fa/FaGithub';
import { FaLinkedin } from '@react-icons/all-files/fa/FaLinkedin';
import { FaTwitter } from '@react-icons/all-files/fa/FaTwitter';

export const Footer = () => (
  <Box
    as="footer"
    role="contentinfo"
    mx="auto"
    py="12"
    px={{
      base: '4',
      md: '8',
    }}
  >
    <Stack>
      <Divider my="5" borderColor={useColorModeValue('gray.400', 'gray.400')} />
      <Text
        fontSize="xs"
        alignSelf={{
          base: 'center',
          sm: 'start',
        }}
      >
        &copy; {new Date().getFullYear()} Şahsi Muhasebem {' '}

      </Text>
    </Stack>
  </Box>
);
