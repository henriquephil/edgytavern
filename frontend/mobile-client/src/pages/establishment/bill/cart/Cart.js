import { useState } from 'react'
import { useDispatch, useSelector } from 'react-redux'
import LoadBlock from '../../../../components/LoadBlock'
import { postBillOrder } from '../../../../services/apiService'
import styles from './Cart.module.css'
import CartItem from './CartItem'

function Cart() {
  const dispatch = useDispatch()
  const cartState = useSelector(state => state.cart)
  const visibleComponentState = useSelector(state => state.visibleComponent)
  const [postingOrder, setPostingOrder ] = useState(false)

  const componentClassNames = [styles.Cart]
  if (visibleComponentState.value !== 'cart')
    componentClassNames.push(styles.CartHidden)

  function send() {
    setPostingOrder(true)
    dispatch(postBillOrder())
      .finally(() => {
        setPostingOrder(false)
      })
  }
  
  return (
    <div className={componentClassNames.join(' ')}>
      <div className={styles.ItemList}>
        {cartState.items.map((it, idx) => <CartItem key={idx} item={it} index={idx}/>)}
      </div>
      {cartState.items.length === 0 && <span>Go order something!</span>}
      <div className={styles.CartFooter}>
        <span className={styles.CartFooterValue}>
          $ {cartState.finalValue}
        </span>
        <div>
          <span className={styles.CartButton} onClick={() => send()} disabled={!cartState.items.length}>Send!</span>
        </div>
      </div>
      <LoadBlock color="#ffa100" visible={postingOrder}/>
    </div>
  )
}


export default Cart
