import { Box, Flex, Grid, Spacer } from "@chakra-ui/react";
import { Fragment } from "react";
import { useDispatch, useSelector } from "react-redux";
import { fetchBill } from "../../state/ApiBillActions";
import style from './Bill.module.css'

function Bill() {
  const billState = useSelector(state => state.bill);

  return (
    <Box p="8px 0">
      <Flex direction="column" boxShadow="0 2px 4px rgba(0, 0, 0, 0.1)" p="6px">
        <Grid templateColumns="auto auto auto" className={style.itens}>
          {billState.data?.orderedItems.map(i => <Fragment key={i.hashId}>
              <Box>{i.assetName}</Box>
              <Box className="text-right">{i.quantity}</Box>
              <Box className="text-right">{i.finalPrice}</Box>
            </Fragment>)}
        </Grid>
      </Flex>
    </Box>
  );
}

export default Bill;
