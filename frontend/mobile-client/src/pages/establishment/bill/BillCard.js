import { Fragment } from "react";
import { useSelector } from "react-redux";
import styles from './BillCard.module.css';

function BillCard() {
  const ordersState = useSelector(state => state.orders);

  return (
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
  );
}


export default BillCard;
