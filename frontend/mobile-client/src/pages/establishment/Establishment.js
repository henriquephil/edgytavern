import { useDispatch } from 'react-redux';
import styles from './Establishment.module.css';
import EstablishmentHeader from './EstablishmentHeader';
import Bill from './bill/Bill';
import { fetchAssets, fetchBill } from '../../services/apiActions';
import { useEffect } from 'react';

function Establishment() {
  const dispatch = useDispatch();

  useEffect(() => {
    dispatch(fetchBill())
    dispatch(fetchAssets())
  }, [dispatch])

  return (
    <div className={styles.Establishment}>
      <EstablishmentHeader/>
      <Bill/>
    </div>
  );
}

export default Establishment;
