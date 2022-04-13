import { Box, Flex } from "@chakra-ui/react";
import CutBorder from "./CutBorder";

function SpotBill({spotBill}) {

  return (
    <CutBorder borderAt='left'>
      <Flex direction="column" w="100%" minW="193px" p="0 0 4px 0" backgroundColor="#333" color="#ccc" cursor="pointer">
        <Flex p="2px 0 2px 8px" background="#ffa100" color="#222" fontWeight="bold">
          <Box flexGrow="1">{spotBill.spot.name}</Box>
          <Box flexBasis="50px">{spotBill.bills.reduce((acc, cur) => acc + cur.total, 0)}</Box>
        </Flex>
        {spotBill.bills.map(b => 
          <Flex p="2px 0 2px 8px">
            <Box flexGrow="1">{b.customerName}</Box>
            <Box flexBasis="50px">{b.total}</Box>
          </Flex>
        )}
      </Flex>
    </CutBorder>
  );
}

export default SpotBill;
