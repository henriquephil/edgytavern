import { Fragment } from "react";
import { useDispatch, useSelector } from "react-redux";
import styles from './Bill.module.css';
import { openBill } from '../../services/apiActions';
import AddOrder from './add/AddOrder';

function Bill() {
  const dispatch = useDispatch();
  const establishmentState = useSelector(state => state.establishment);
  const billState = useSelector(state => state.bill);
  const ordersState = useSelector(state => state.orders);

  if (billState.loading)
    return <p>Loading</p>
  if (billState.error)
    return JSON.stringify(billState.error);

  const dispatchOpenBill = () => {
    dispatch(openBill());
  }

  return (
    <div className={styles.Bill}>
      { billState.data ? renderBill(ordersState) : renderOpenBill(dispatchOpenBill) }
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

function renderOpenBill(dispatchOpenBill) {
  return (
    <div className={styles.BillCard}>
      <button onClick={() => dispatchOpenBill()}>Begin a new bill and order someting!</button>
    </div>
  );
}

export default Bill;
