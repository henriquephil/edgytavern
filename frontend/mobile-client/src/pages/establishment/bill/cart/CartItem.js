import { useDispatch } from 'react-redux';
import { updateItem } from '../../../../state/editItemSlice';
import { removeFromCart, decreaseCartItemQuantity, increaseCartItemQuantity } from '../../../../state/cartSlice';
import styles from './Cart.module.css';

function CartItem({ index, item }) {
  const dispatch = useDispatch();

  const editItem = () => {
    dispatch(updateItem({ index, item: structuredClone(item) }));
  }

  const reduce = () => {
    dispatch(decreaseCartItemQuantity(index))
  }

  const add = () => {
    dispatch(increaseCartItemQuantity(index))
  }

  const remove = () => {
    dispatch(removeFromCart(index))
  }

  return (
    <div className={styles.CartItem}>
      <span className={styles.CartButton} onClick={() => reduce()}>-</span>
      <span className={styles.CartButton} onClick={() => add()}>+</span>
      <div>
        <span>{item.quantity}</span>-
        <span>{item.asset.name}</span>-
        <span>{item.asset.price}</span>-
        <span>{item.removedIngredients.length}</span>-
        <span>{item.selectedAdditionals.length}</span>-
        <span>{item.finalValue}</span>
      </div>
      <span className={styles.CartButton} onClick={() => editItem()}>E</span>
      <span className={styles.CartButton} onClick={() => remove()}>R</span>
    </div>
  );
}


export default CartItem;
