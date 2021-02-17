import { Box, Flex } from "@chakra-ui/react";
import CutBorder from "../../../components/CutBorder";

function SeatBill({seatBill}) {

  return (
    <CutBorder borderAt='left'>
      <Flex direction="column" w="100%" minW="193px" p="0 0 4px 0" borderLeft="3px solid #ffa100" backgroundColor="#333" color="#ccc" cursor="pointer">
        <Flex p="2px 0 2px 8px" background  ="#ffa100" color="#222" fontWeight="bold">
          <Box flexGrow="1">{seatBill.seat.name}</Box>
          <Box flexBasis="50px">{seatBill.bills.reduce((acc, cur) => acc + cur.total, 0)}</Box>
        </Flex>
        {seatBill.bills.map(b => 
          <Flex p="2px 0 2px 8px">
            <Box flexGrow="1">{b.customerName}</Box>
            <Box flexBasis="50px">{b.total}</Box>
          </Flex>
        )}
      </Flex>
    </CutBorder>
  );
}

export default SeatBill;
