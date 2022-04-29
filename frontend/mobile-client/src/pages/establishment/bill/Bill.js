import styles from './Bill.module.css';
import BillCard from "./BillCard";
import AddAsset from "./add/AddAsset";
import Cart from './cart/Cart';
import BillButtons from './buttons/BillButtons';

function Bill() {
  return (
    <div className={styles.Bill}>
      <BillCard/>
      <AddAsset/>
      <Cart/>
      <BillButtons/>
    </div>
  );
}


export default Bill;
