import { useSelector } from 'react-redux'
import styles from './EstablishmentHeader.module.css'

function EstablishmentHeader() {
  const establishmentState = useSelector(state => state.establishment)
  const spotState = useSelector(state => state.spot)
  const ordersState = useSelector(state => state.orders)
  
  return (
    <div className={styles.root}>
      <div className={styles.names}>
        <span className={styles.establishmentName}>{establishmentState.data?.name}</span>
        <span className={styles.spotName}>{spotState.data?.name}</span>
      </div>
      <span className={styles.billValue}>$ {ordersState.data?.totalPrice.toFixed(2)}</span>
    </div>
  )
}

export default EstablishmentHeader
