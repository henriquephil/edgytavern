import Menu from "../../../../../components/menu/Menu"
import styles from './index.module.scss'

function AddOrder({ orders }) {
  
  return (
    <div className={styles.OrderedItems}>
      <Menu/>
    </div>    
  )
}

export default AddOrder