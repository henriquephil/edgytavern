import { useContext } from "react"
import EditBillModal from "../pages/establishment/dashboard/modals/EditBillModal"
import { ModalContext } from "./modal/ModalContext"
import style from "./SpotBill.module.css"

function SpotBill({spotBill}) {

  const { openModal } = useContext(ModalContext)

  return (
    <div className={style.SpotBill} onClick={() => openModal(<EditBillModal billId={spotBill.id}/>)}>
      <div className={style.username} style={{ backgroundColor: spotBill.open ? '#ffa100' : '#ccc' }}>
        {spotBill.customerName}
      </div>
      <div className={style.info}>
        <div className={style.row}>{spotBill.started}</div>
      </div>
    </div>
  )
}

export default SpotBill
