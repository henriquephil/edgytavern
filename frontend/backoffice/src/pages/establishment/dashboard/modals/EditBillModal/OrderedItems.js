import { Fragment } from "react"
import MyScroll from "../../../../../components/MyScroll"
import styles from './index.module.scss'

function OrderedItems({ orders }) {
  
  function showAddOrder() {

  }

  const statusColors = {
    'RECEIVED': 'red',
    'PREPARATION': '#ffa100',
    'DISPATCHED': 'blue',
    'DELIVERED': 'white'
  }

  return (
    <div className={styles.OrderedItems}>
      <MyScroll>
      {orders.map((o, idx) =>
        <div key={idx} className={styles.order}>
          <div className={styles.name}>
            {o.spotName}
          </div>
          <div className={styles.time}>
            {o.createdAt}
          </div>
          <div className={styles.items}>
            {o.items.map(i => 
            <Fragment key={i.id}>
              <div style={{ background: statusColors[i.status] }}>
              </div>
              <div>
                {i.assetName}
              </div>
              <div>
                {i.quantity}
              </div>
              <div>
                {i.totalPrice}
              </div>
            </Fragment>
          )}
          </div>
        </div>
      )}
      </MyScroll>
      <button onClick={() => showAddOrder()}>Add order</button>
    </div>    
  )
}

export default OrderedItems