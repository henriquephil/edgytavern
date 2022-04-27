import { Fragment } from "react";
import { useSelector } from "react-redux";
import styles from './Bill.module.css'

function Bill() {
  const billState = useSelector(state => state.bill);

  if (billState.loading)
    return <p>Loading</p>
  if (billState.error)
    return JSON.stringify(billState.error);

  return (
    <div className={styles.Bill}>
      { billState.data ? renderBill(billState) : renderOpenBill() }
    </div>
  );
}

function renderBill(billState) {
  return (
    <div className={styles.BillCard}>
      <div className={styles.BillGrid}>
        {billState.data?.orderedItems.map(i => 
          <Fragment key={i.hashId}>
            <div>{i.assetName}</div>
            <div className="text-right">{i.quantity}</div>
            <div className="text-right">{i.finalPrice}</div>
          </Fragment>
        )}
      </div>
    </div>
  );
}

function renderOpenBill(billState) {
  return (
    <div className={styles.BillCard}>
      <button>Begin a new bill and order someting!</button>
    </div>
  );
}

export default Bill;
