import styles from './Bill.module.css'
import BillCard from "./BillCard"

function Bill() {
  return (
    <div className={styles.Bill}>
      <BillCard/>
    </div>
  )
}


export default Bill
