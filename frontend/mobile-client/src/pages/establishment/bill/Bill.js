import styles from './Bill.module.css'
import BillCard from "./BillCard"
import AddItem from "./add/AddItem"
import Cart from './cart/Cart'

function Bill() {
  return (
    <div className={styles.Bill}>
      <BillCard/>
      <AddItem/>
      <Cart/>
    </div>
  )
}


export default Bill
