import styles from './Asset.module.css';
import NumberFormat from 'react-number-format';

function Asset({ asset, onSelect }) {

  return (
    <div className={styles.Asset}>
      <div className={styles.name}>
        <span class="limited-text-length">{asset.name}</span>
      </div>
      <div className={styles.price}><NumberFormat value={asset.price} displayType="text" thousandSeparator="." prefix={'$'} decimalScale="2" fixedDecimalScale={true} decimalSeparator=","/></div>
      <div className={styles.select}>
        <button onClick={() => onSelect()}>>></button>
      </div>
      <div className={styles.description}>
        <span class="limited-text-length">{asset.description}</span>
      </div>
    </div>
  );
}

export default Asset