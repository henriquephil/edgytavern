import { useSelector } from 'react-redux'
import styles from './EstablishmentHeader.module.css'

function EstablishmentHeader() {
  const establishmentState = useSelector(state => state.establishment)
  const ordersState = useSelector(state => state.orders)
  
  return (
    <div className={styles.EstablishmentHeader}>
      <span>{establishmentState.data?.name}</span>
      <span>$ {ordersState.data?.totalPrice.toFixed(2)}</span>
    </div>
  )
}

export default EstablishmentHeader
