import { Box, Flex, Grid } from "@chakra-ui/react";
import SpotBill from './SpotBill';
import style from './SpotsBills.module.css';

function SpotBills() {
  
  let id = 1;
  let cid = 1;
  const spotBills = [
    { spot: { id: id++, name: 'asdas' }, bills: [ { id: cid++, customerName: 'asdasd', total: 42 }, { id: cid++, customerName: 'qwewqe', total: 42 } ] },
    { spot: { id: id++, name: 'zxczxc' }, bills: [ { id: cid++, customerName: 'asdasd', total: 42 }, { id: cid++, customerName: 'qwewqe', total: 42 } ] },
    { spot: { id: id++, name: 'gsdfsd' }, bills: [ { id: cid++, customerName: 'asdasd', total: 42 }, { id: cid++, customerName: 'qwewqe', total: 42 } ] },
    { spot: { id: id++, name: 'hgfgh' }, bills: [ { id: cid++, customerName: 'asdasd', total: 42 }, { id: cid++, customerName: 'qwewqe', total: 42 } ] },
    { spot: { id: id++, name: 'vbnvbn' }, bills: [ { id: cid++, customerName: 'asdasd', total: 42 }, { id: cid++, customerName: 'qwewqe', total: 42 } ] },
    { spot: { id: id++, name: 'ytutyu' }, bills: [ { id: cid++, customerName: 'asdasd', total: 42 }, { id: cid++, customerName: 'qwewqe', total: 42 } ] },
    { spot: { id: id++, name: 'ertert' }, bills: [ { id: cid++, customerName: 'asdasd', total: 42 }, { id: cid++, customerName: 'qwewqe', total: 42 } ] },
    { spot: { id: id++, name: '3re23r' }, bills: [ { id: cid++, customerName: 'asdasd', total: 42 }, { id: cid++, customerName: 'qwewqe', total: 42 } ] },
    { spot: { id: id++, name: 'fvvce' }, bills: [ { id: cid++, customerName: 'asdasd', total: 42 }, { id: cid++, customerName: 'qwewqe', total: 42 } ] },
    { spot: { id: id++, name: '3re23r' }, bills: [ { id: cid++, customerName: 'asdasd', total: 42 }, { id: cid++, customerName: 'qwewqe', total: 42 } ] },
    { spot: { id: id++, name: 'fvvce' }, bills: [ { id: cid++, customerName: 'asdasd', total: 42 }, { id: cid++, customerName: 'qwewqe', total: 42 } ] },
  ]

  return (
    <Grid w="100%" maxW="100%" className={style.SpotBills}>
      {spotBills.map(sb => (
        <Box p="4px">
          <SpotBill spotBill={sb} />
        </Box>))}
    </Grid>
  );
}

export default SpotBills;
