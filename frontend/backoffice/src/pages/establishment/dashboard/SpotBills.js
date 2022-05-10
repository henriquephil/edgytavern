import { Box, Flex, Grid } from "@chakra-ui/react";
import { useSelector } from "react-redux";
import SpotBill from '../../../components/SpotBill';
import style from './SpotsBills.module.css';

function SpotBills() {
  let { loading, data, error } = useSelector(state => state.openBills);

  if (loading) return 'Loading bills';
  if (error) return JSON.stringify(error);
  if (!data) return <></>

  return (
    <Grid w="100%" maxW="100%" className={style.SpotBills}>
      {data.bills.map((sb, idx) => (
        <Box key={idx} p="4px">
          <SpotBill spotBill={sb} />
        </Box>))}
    </Grid>
  );
}

export default SpotBills;
