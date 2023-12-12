import OrderStatus from "../../../components/OrderStatus"
import { useList } from "react-use"
import { useEffect, useState } from "react"
import api from "../../../services/api"
import styles from './RecentOrders.module.css'

function RecentOrders() {
  const [ orders, orderActions ] = useList([])
  const [ lastUpdate, setLastUpdate ] = useState(null)

  function update() {
    console.log(lastUpdate)
    api.get('/api/counter/admin/recent-orders', { params: { since: lastUpdate } })
      .then(res => {
        res.data.items.forEach(item => {
          orderActions.upsert(it => it.id === item.id, item)
        })
        setLastUpdate(res.data.timestamp.toISOString())
      })
  }

  useEffect(() => {
    api.get('/api/counter/admin/recent-orders')
      .then(res => {
        orderActions.set(res.data.items)
        setLastUpdate(res.data.timestamp.toISOString())
        // TODO listen event        
        const interval = setInterval(() => {
          update(lastUpdate)
        }, 100 * 1000);
        return () => clearInterval(interval);
      })
  }, [])

  return (
    <div className={styles.RecentOrders}>
      <span style={{color: 'white'}}>{JSON.stringify(lastUpdate)}</span>
      {orders.map((i, idx) => <OrderStatus key={idx} item={i}/>)}
    </div>
  )
}

export default RecentOrders
