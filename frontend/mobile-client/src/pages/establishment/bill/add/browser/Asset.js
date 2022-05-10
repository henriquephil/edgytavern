import styles from './Asset.module.css';
import NumberFormat from 'react-number-format';
import { useDispatch } from 'react-redux';
import { newItem } from '../../../../../state/editItemSlice';

function Asset({ asset }) {
  const dispatch = useDispatch();

  const onSelect = () => {
    dispatch(newItem(asset));
  }

  return (
    <div className={styles.Asset}>
      <div className={styles.name}>
        <span className="limited-text-length">{asset.name}</span>
      </div>
      <div className={styles.price}><NumberFormat value={asset.price} displayType="text" thousandSeparator="." prefix={'$'} decimalScale="2" fixedDecimalScale={true} decimalSeparator=","/></div>
      <div className={styles.select}>
        <button onClick={() => onSelect()}>S</button>
      </div>
      <div className={styles.description}>
        <span className="limited-text-length">{asset.description}</span>
      </div>
    </div>
  );
}

export default Asset