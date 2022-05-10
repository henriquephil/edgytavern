import styles from './ActionButtons.module.css';
import ActionButton from './ActionButton';

function ActionButtons() {
  return (
    <div className={styles.ActionButtons}>
      <div className={styles.BillButonHolder}>
        <ActionButton component="cart" color="#ffa100" text="Cart" visibleWhen={['bill', 'menu', 'editItem']}/>
      </div>
      <div className={styles.BillButonHolder}>
        <ActionButton component="callWaiter" color="green" text="Call Waiter" visibleWhen={['bill']}/>
        <ActionButton component="bill" color="white" text="Bill" visibleWhen={['cart', 'callWaiter', 'menu', 'editItem']}/>
      </div>
      <div className={styles.BillButonHolder}>
        <ActionButton component="menu" color="blue" text="Menu" visibleWhen={['bill', 'cart']}/>
      </div>
    </div>
  );
}


export default ActionButtons;
