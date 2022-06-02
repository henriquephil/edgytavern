import { Fragment } from "react"
import { useSelector } from "react-redux"
import styles from './BillCard.module.css'

function BillCard() {
  const {loading, data, error} = useSelector(state => state.orders)

  const statusColors = {
    'RECEIVED': 'red',
    'PREPARATION': '#ffa100',
    'DISPATCHED': 'blue',
    'DELIVERED': 'white'
  }

  if (loading)
    return 'Loading bill'
  if (error)
    return 'Error: ' + JSON.stringify(error)

  return (
    <div className={styles.BillCard}>
      {data?.items && 
      <div className={styles.BillGrid}>
        {data.items.map(it => 
          <Fragment key={it.hashId}>
            <div style={{'background': statusColors[it.status]}}></div>
            <div>{it.assetName}</div>
            <div className="text-right">{it.quantity}</div>
            <div className="text-right">{it.totalPrice}</div>
          </Fragment>
        )}
      </div>}
    </div>
  )
}


export default BillCard
