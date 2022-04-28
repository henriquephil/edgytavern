import { Fragment } from "react";
import { useDispatch, useSelector } from "react-redux";
import styles from './Bill.module.css';
import { openBill } from '../../state/ApiBillActions';
import AddOrder from './add/AddOrder';

function Bill() {
  const billState = useSelector(state => state.bill);
  const ordersState = useSelector(state => state.orders);
  const dispatch = useDispatch();

  if (billState.loading)
    return <p>Loading</p>
  if (billState.error)
    return JSON.stringify(billState.error);

  const openBillDispatcher = () => {
    dispatch(openBill());
  }

  return (
    <div className={styles.Bill}>
      { billState.data ? renderBill(ordersState) : renderOpenBill(openBillDispatcher) }
    </div>
  );
}

function renderBill(ordersState) {
  return (
    <>
      <div className={styles.BillCard}>
        <div className={styles.BillGrid}>
          {ordersState.data?.map(i => 
            <Fragment key={i.hashId}>
              <div>{i.assetName}</div>
              <div className="text-right">{i.quantity}</div>
              <div className="text-right">{i.finalPrice}</div>
            </Fragment>
          )}
        </div>
      </div>
      <AddOrder/>
    </>
  );
}

function renderOpenBill(openBill) {
  return (
    <div className={styles.BillCard}>
      <button onClick={() => openBill()}>Begin a new bill and order someting!</button>
    </div>
  );
}

export default Bill;
