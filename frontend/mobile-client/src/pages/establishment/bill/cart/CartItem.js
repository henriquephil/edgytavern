import { useDispatch } from 'react-redux';
import { updateItem } from '../../../../state/editItemSlice';
import { removeFromCart } from '../../../../state/cartSlice';
import styles from './Cart.module.css';

function CartItem({ index, item }) {
  const dispatch = useDispatch();

  const editItem = () => {
    dispatch(updateItem({ index, item: structuredClone(item) }));
  }

  const remove = () => {
    dispatch(removeFromCart(index))
  }

  return (
    <div className={styles.CartItem}>
      <div onClick={() => editItem()}>
        <span>{item.asset.name}</span>
        <span>{item.asset.price}</span>
        <span>{item.removedIngredients.length}</span>
        <span>{item.selectedAdditionals.length}</span>
      </div>
      <button onClick={() => remove()}>R</button>
    </div>
  );
}


export default CartItem;
