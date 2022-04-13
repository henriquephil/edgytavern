import { Box } from "@chakra-ui/react";
import Add from "../Add";
import Bill from "./Bill";

function Bills({ bills }) {

  return (
  <Box p="8px" className="scrollable">
    {bills.map(bill => <Bill key={bill.uid} {...bill} />)}
    <Add />
  </Box>);
}

export default Bills;