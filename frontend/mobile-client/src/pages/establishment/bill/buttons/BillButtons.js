import styles from './BillButtons.module.css';
import BillButton from './BillButton';

function BillButtons() {
  return (
    <div className={styles.BillButtons}>
      <div className={styles.BillButonHolder}>
        <BillButton component="cart" color="#ffa100" text="Cart" visibleWhen={['none', 'addAsset']}/>
      </div>
      <div className={styles.BillButonHolder}>
        <BillButton component="callWaiter" color="green" text="Call Waiter" visibleWhen={['none']}/>
        <BillButton component="none" color="red" text="Close" visibleWhen={['cart', 'callWaiter', 'addAsset']}/>
      </div>
      <div className={styles.BillButonHolder}>
        <BillButton component="addAsset" color="blue" text="Add Item" visibleWhen={['none', 'cart']}/>
      </div>
    </div>
  );
}


export default BillButtons;
