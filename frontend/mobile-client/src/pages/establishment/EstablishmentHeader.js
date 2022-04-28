import { useSelector } from 'react-redux';
import styles from './EstablishmentHeader.module.css'

function EstablishmentHeader() {
  const establishmentState = useSelector(state => state.establishment);
  const ordersState = useSelector(state => state.orders);

  const sum = ordersState.data?.map(b => b.finalPrice)
    .reduce((prev, i) => prev += i, 0);
  
  return (
    <div className={styles.EstablishmentHeader}>
      <span>{establishmentState.data?.name}</span>
      <span>$ {(sum || 0).toFixed(2)}</span>
    </div>
  );
}

export default EstablishmentHeader;
