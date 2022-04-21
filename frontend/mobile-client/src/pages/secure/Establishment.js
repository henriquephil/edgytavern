import { useDispatch, useSelector } from 'react-redux';
import styles from './UserHeader.module.css';
import { Flex } from "@chakra-ui/react"
import SecurityService from '../../services/SecurityService';
import { useState } from 'react';
import EstablishmentHeader from './EstablishmentHeader';
import { Outlet, Route, Routes } from 'react-router-dom';
import Bill from './Bill';
import { fetchBill } from '../../state/ApiBillActions';

function Establishment() {
  const dispatch = useDispatch();
  dispatch(fetchBill())

  return (
    <Flex direction="column" h="100%" grow="1">
      <EstablishmentHeader/>
      <Bill/>
    </Flex>
  );
}

export default Establishment;
