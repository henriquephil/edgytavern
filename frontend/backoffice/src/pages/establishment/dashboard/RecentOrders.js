import { Flex } from "@chakra-ui/react"
import OrderStatus from "../../../components/OrderStatus"

function RecentOrders() {

  return (

    <Flex direction="column" w="100%" color="#222" p="0 2px">
      {[...Array(25).keys()].map(i => <OrderStatus key={i} delivered={i > 5}/>)}
    </Flex>)
}

export default RecentOrders
