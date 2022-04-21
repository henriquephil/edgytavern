import { Box, Flex, Grid } from "@chakra-ui/react";
import { useSelector } from "react-redux";
import SpotBill from '../../../components/SpotBill';
import style from './SpotsBills.module.css';

function SpotBills() {
  let openBills = useSelector(state => state.openBills);

  return (
    <Grid w="100%" maxW="100%" className={style.SpotBills}>
      {openBills.map((sb, idx) => (
        <Box key={idx} p="4px">
          <SpotBill spotBill={sb} />
        </Box>))}
    </Grid>
  );
}

export default SpotBills;
