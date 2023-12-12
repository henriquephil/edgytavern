import styles from "./OrderStatus.module.css"

function OrderStatus({ item }) {
  const delivered = item.status === 'DELIVERED'

  return (
    <div className={styles.OrderStatus} style={{opacity: (delivered ? '0.5' : '1')}}>
      <div className={styles.OrderCard}>
        <div className={styles.row}>
          <span>{ item.asset.name }</span>
          <span>{ item.quantity }</span>
        </div>
        <div className={styles.row}>
          <span>{ `${item.spotName} - ${item.spotNumber}` }</span>
          <span>{ item.status }</span>
          <span>{ item.createdAt.format() }</span>
        </div>
      </div>
    </div>)
}

export default OrderStatus
