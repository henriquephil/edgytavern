import { useContext } from "react"
import { ModalContext } from "../../../../../components/modal/ModalContext"
import api from "../../../../../services/api"
import { useFetchBill } from "../../../../../services/apiHooks"
import styles from './index.module.scss'
import OrderedItems from "./OrderedItems"

function EditBillModal({ billId }) {
  const { closeModal } = useContext(ModalContext)
  const { billLoading, billData, billError } = useFetchBill(billId)
  
  function paymentReceived() {
    api.post(`/api/counter/admin/bills/${billId}/close`)
    .then(res => {
      closeModal()
    })
  }
  
  if (billLoading) return 'loading'
  if (billError) return JSON.stringify(billError)
  if (!billData) return 'WTF no bill data?'

  return (
    <div className={styles.EditBillModal}>
      <OrderedItems orders={billData.orders}/>
      <div>
        <div>
          # {billData.id}
        </div>
        <div>
          {billData.started}
        </div>
        <div>
          {billData.customerName}
        </div>
        <div>
          R$ {billData.total}
        </div>
        {billData.open && <div>
          <button onClick={() => paymentReceived()}>Payment received</button>
        </div>}
      </div>
    </div>    
  )
}

export default EditBillModal