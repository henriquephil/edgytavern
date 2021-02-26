import { Box, Flex } from "@chakra-ui/react";
import CutBorder from "../../../components/CutBorder";

function OrderStatus(props) {
  return (
    <Box w="100%" p="4px" opacity={props.delivered ? '0.5' : '1'}>
      <CutBorder borderAt='left'>
        <Flex direction="column" w="100%" paddingLeft="8px" background="#333" color="#fff">
          <Flex w="100%" justify="space-between" p="4px">
            <span>asset</span>
            <span>qtd</span>
          </Flex>
          <Flex w="100%" justify="space-between" p="4px">
            <span>spot</span>
            <span>status</span>
            <span>time</span>
          </Flex>
        </Flex>
      </CutBorder>
    </Box>);
}

export default OrderStatus;
