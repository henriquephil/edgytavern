import { Fragment, useContext } from "react";
import { ModalContext } from "../../../../components/modal/ModalContext";
import MyScroll from "../../../../components/MyScroll";
import api from "../../../../services/api";
import { useFetchBill } from "../../../../services/apiHooks";
import styles from './EditBillModal.module.scss';

function EditBillModal({ billId }) {
  const { closeModal } = useContext(ModalContext)
  const { billLoading, billData, billError } = useFetchBill(billId);
  
  function paymentReceived() {
    api.post(`/api/bills/managed/bills/${billId}/close`)
    .then(res => {
      closeModal();
    })
  }
  
  if (billLoading) return 'loading';
  if (billError) return JSON.stringify(billError);
  if (!billData) return 'WTF no bill data?';
  
  const statusColors = {
    'RECEIVED': 'red',
    'PREPARATION': '#ffa100',
    'DISPATCHED': 'blue',
    'DELIVERED': 'white'
  }

  return (
    <div className={styles.EditBillModal}>
      <MyScroll>
      {billData.orders.map((o, idx) =>
        <div key={idx} className={styles.order}>
          <div className={styles.name}>
            {o.spotName}
          </div>
          <div className={styles.time}>
            {o.createdAt}
          </div>
          <div className={styles.items}>
            {o.items.map(i => 
            <Fragment key={i.id}>
              <div style={{ background: statusColors[i.status] }}>
              </div>
              <div>
                {i.assetName}
              </div>
              <div>
                {i.quantity}
              </div>
              <div>
                {i.totalPrice}
              </div>
            </Fragment>
          )}
          </div>
        </div>
      )}
      </MyScroll>
      <div>
        <div>
          {billData.id}
        </div>
        <div>
          {billData.customerName}
        </div>
        <div>
          {billData.started}
        </div>
        {billData.open && <div>
          <button onClick={() => paymentReceived()}>Payment received</button>
        </div>}
      </div>
    </div>    
  )
}

export default EditBillModal;