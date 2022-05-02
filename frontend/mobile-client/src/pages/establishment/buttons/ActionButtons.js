import styles from './ActionButtons.module.css';
import ActionButton from './ActionButton';

function ActionButtons() {
  return (
    <div className={styles.ActionButtons}>
      <div className={styles.BillButonHolder}>
        <ActionButton component="cart" color="#ffa100" text="Cart" visibleWhen={['none', 'addItem']}/>
      </div>
      <div className={styles.BillButonHolder}>
        <ActionButton component="callWaiter" color="green" text="Call Waiter" visibleWhen={['none']}/>
        <ActionButton component="none" color="red" text="Close" visibleWhen={['cart', 'callWaiter', 'addItem']}/>
      </div>
      <div className={styles.BillButonHolder}>
        <ActionButton component="addItem" color="blue" text="Add Item" visibleWhen={['none', 'cart']}/>
      </div>
    </div>
  );
}


export default ActionButtons;
