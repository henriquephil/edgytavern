import { useDispatch } from 'react-redux';
import styles from './Establishment.module.css';
import EstablishmentHeader from './EstablishmentHeader';
import Bill from './Bill';
import { fetchAssets, fetchBill } from '../../services/apiActions';
import { useEffect } from 'react';

function Establishment() {
  const dispatch = useDispatch();

    dispatch(fetchBill())
    dispatch(fetchAssets())

  return (
    <div className={styles.Establishment}>
      <EstablishmentHeader/>
      <Bill/>
    </div>
  );
}

export default Establishment;
