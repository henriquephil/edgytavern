import { Flex } from "@chakra-ui/react"
import OrderStatus from "../../../components/OrderStatus"
import { useList } from "react-use"
import { useEffect, useState } from "react"
import api from "../../../services/api"

function RecentOrders() {
  const [ orders, orderActions ] = useList([])
  const [ lastUpdate, setLastUpdate ] = useState(null)

  function update() {
    console.log(lastUpdate)
    api.get('/api/bills/managed/recent-orders', { params: { since: lastUpdate } })
      .then(res => {
        res.data.items.forEach(item => {
          orderActions.upsert(it => it.id === item.id, item)
        })
        setLastUpdate(res.data.timestamp)
      })
    // TODO listen event
    setTimeout(() => {
      update()
    }, 10 * 1000);
  }

  useEffect(() => {
    api.get('/api/bills/managed/recent-orders')
      .then(res => {
        console.log(res.data)
        orderActions.set(res.data.items)
        setLastUpdate(res.data.timestamp)
        // TODO listen event
        setTimeout(() => {
          update()
        }, 10 * 1000);
      })
  }, [])

  return (
    <Flex direction="column" w="100%" color="#222" p="0 2px">
      {JSON.stringify(orders)}
      {orders.map((i, idx) => <OrderStatus key={idx} item={i}/>)}
    </Flex>)
}

export default RecentOrders
