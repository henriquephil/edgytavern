import { Box, Flex } from "@chakra-ui/react"
import CutBorder from "./CutBorder"

function OrderStatus({ item }) {
  const delivered = item.status === 'DELIVERED'

  return (
    <Box w="100%" p="4px" opacity={delivered ? '0.5' : '1'}>
      <CutBorder borderAt='left'>
        <Flex direction="column" w="100%" paddingLeft="8px" background="#333" color="#fff">
          <Flex w="100%" justify="space-between" p="4px">
            <span>{ item.asset.name }</span>
            <span>{ item.quantity }</span>
          </Flex>
          <Flex w="100%" justify="space-between" p="4px">
            <span>{ `${item.spotName} - ${item.spotNumber}` }</span>
            <span>{ item.status }</span>
            <span>{ item.createdAt.format() }</span>
          </Flex>
        </Flex>
      </CutBorder>
    </Box>)
}

export default OrderStatus
