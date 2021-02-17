import { Box, Flex, Grid } from "@chakra-ui/react";
import SeatBill from './SeatBill';
import style from './SeatBills.module.css';

function SeatBills() {
  
  let id = 1;
  let cid = 1;
  const seatBills = [
    { seat: { id: id++, name: 'asdas' }, bills: [ { id: cid++, customerName: 'asdasd', total: 42 }, { id: cid++, customerName: 'qwewqe', total: 42 } ] },
    { seat: { id: id++, name: 'zxczxc' }, bills: [ { id: cid++, customerName: 'asdasd', total: 42 }, { id: cid++, customerName: 'qwewqe', total: 42 } ] },
    { seat: { id: id++, name: 'gsdfsd' }, bills: [ { id: cid++, customerName: 'asdasd', total: 42 }, { id: cid++, customerName: 'qwewqe', total: 42 } ] },
    { seat: { id: id++, name: 'hgfgh' }, bills: [ { id: cid++, customerName: 'asdasd', total: 42 }, { id: cid++, customerName: 'qwewqe', total: 42 } ] },
    { seat: { id: id++, name: 'vbnvbn' }, bills: [ { id: cid++, customerName: 'asdasd', total: 42 }, { id: cid++, customerName: 'qwewqe', total: 42 } ] },
    { seat: { id: id++, name: 'ytutyu' }, bills: [ { id: cid++, customerName: 'asdasd', total: 42 }, { id: cid++, customerName: 'qwewqe', total: 42 } ] },
    { seat: { id: id++, name: 'ertert' }, bills: [ { id: cid++, customerName: 'asdasd', total: 42 }, { id: cid++, customerName: 'qwewqe', total: 42 } ] },
    { seat: { id: id++, name: '3re23r' }, bills: [ { id: cid++, customerName: 'asdasd', total: 42 }, { id: cid++, customerName: 'qwewqe', total: 42 } ] },
    { seat: { id: id++, name: 'fvvce' }, bills: [ { id: cid++, customerName: 'asdasd', total: 42 }, { id: cid++, customerName: 'qwewqe', total: 42 } ] },
    { seat: { id: id++, name: '3re23r' }, bills: [ { id: cid++, customerName: 'asdasd', total: 42 }, { id: cid++, customerName: 'qwewqe', total: 42 } ] },
    { seat: { id: id++, name: 'fvvce' }, bills: [ { id: cid++, customerName: 'asdasd', total: 42 }, { id: cid++, customerName: 'qwewqe', total: 42 } ] },
  ]

  return (
    <Grid w="100%" maxW="100%" className={style.SeatBills}>
      {seatBills.map(sb => (
        <Box p="4px">
          <SeatBill seatBill={sb} />
        </Box>))}
    </Grid>
  );
}

export default SeatBills;
