import { useState } from 'react'
import { useDispatch, useSelector } from 'react-redux'
import LoadBlock from '../../../../components/LoadBlock'
import { postBillOrder } from '../../../../services/apiService'
import { requestScan } from '../../../../state/spotSlice'
import styles from './Cart.module.css'
import CartItem from './CartItem'

function Cart() {
  const dispatch = useDispatch()
  const cartState = useSelector(state => state.cart)
  const spotState = useSelector(state => state.spot)
  const [postingOrder, setPostingOrder ] = useState(false)

  function send() {
    if (!spotState.data) {
      dispatch(requestScan())
      return;
    }
    setPostingOrder(true)
    dispatch(postBillOrder())
      .finally(() => {
        setPostingOrder(false)
      })
  }
  
  return (
    <div className={styles.Cart}>
      <div className={styles.ItemList}>
        {cartState.items.map((it, idx) => <CartItem key={idx} item={it} index={idx}/>)}
      </div>
      {cartState.items.length === 0 && <span>Go order something!</span>}
      <div className={styles.CartFooter}>
        <span>
          {spotState.data && `Deliver at ${spotState.data.name}-${spotState.data.number}`}
        </span>
        <span className={styles.CartFooterValue}>
          $ {cartState.finalValue}
        </span>
        <div>
          <a className={styles.CartButton} onClick={() => send()} disabled={!cartState.items.length || !spotState.data}>Send!</a>
        </div>
      </div>
      <LoadBlock color="#ffa100" visible={postingOrder}/>
    </div>
  )
}


export default Cart
