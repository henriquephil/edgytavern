import { useDispatch, useSelector } from 'react-redux';
import { emptyCart } from '../../../../state/cartSlice';
import styles from './Cart.module.css';
import CartItem from './CartItem';

function Cart() {
  const dispatch = useDispatch();
  const cartState = useSelector(state => state.cart);
  const visibleComponentState = useSelector(state => state.billVisibleComponent);

  const componentClassNames = [styles.Cart];
  if (visibleComponentState.value !== 'cart')
    componentClassNames.push(styles.CartHidden)

  const send = () => {
    // TODO scan spot QR code
    console.log(cartState);
    dispatch(emptyCart());
  }
  
  return (
    <div className={componentClassNames.join(' ')}>
      {cartState.items.map((it, idx) => <CartItem key={idx} item={it} index={idx}/>)}
      <div>
        <button onClick={() => send()} disabled={!cartState.items.length}>Send!</button>
      </div>
    </div>
  );
}


export default Cart;
