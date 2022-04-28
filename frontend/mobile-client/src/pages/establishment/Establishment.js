import { useDispatch } from 'react-redux';
import styles from './Establishment.module.css';
import EstablishmentHeader from './EstablishmentHeader';
import Bill from './Bill';
import { fetchBill } from '../../state/ApiBillActions';

function Establishment() {
  const dispatch = useDispatch();
  dispatch(fetchBill())

  return (
    <div className={styles.Establishment}>
      <EstablishmentHeader/>
      <Bill/>
    </div>
  );
}

export default Establishment;
