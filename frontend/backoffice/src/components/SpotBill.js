import { Box, Flex } from "@chakra-ui/react";
import { useContext } from "react";
import EditBillModal from "../pages/establishment/dashboard/modals/EditBillModal";
import CutBorder from "./CutBorder";
import { ModalContext } from "./modal/ModalContext";

function SpotBill({spotBill}) {

  const { openModal } = useContext(ModalContext)

  const headerBg = spotBill.open ? '#ffa100' : '#ccc';

  return (
    <CutBorder borderAt='left'>
      <Flex direction="column" w="100%" minW="260px" p="0 0 4px 0" backgroundColor="#333" color="#ccc" cursor="pointer" onClick={() => openModal(<EditBillModal billId={spotBill.id}/>)}>
        <Flex p="2px 0 2px 8px" background={headerBg} color="#222" fontWeight="bold">
          {spotBill.customerName}
        </Flex>
        <Flex p="2px 0 2px 8px">
          <Box flexGrow="1">{spotBill.started}</Box>
        </Flex>
      </Flex>
    </CutBorder>
  );
}

export default SpotBill;
