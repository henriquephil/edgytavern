import styles from './NavButtons.module.css'
import { useDispatch, useSelector } from 'react-redux'
import { FRAME_BILL, FRAME_CART, FRAME_MENU, FRAME_WAITER, setVisibleFrame } from '../../../state/visibleFrameSlice'

function NavButtons() {
  const dispatch = useDispatch()
  const visibleFrameState = useSelector(state => state.visibleFrame)
  
  function setVisible(frame) {
    dispatch(setVisibleFrame(frame))
  }

  const translateAmount = {
    0: 0,
    1: 100,
    2: 200,
    3: 300
  }[visibleFrameState.value]
  const translate = {
    transform: `translateX(${translateAmount}%)`
  }
  
  return (
    <div className={styles.NavButtons}>
      <div className={styles.Selected} style={translate}></div>
      <div className={styles.ButtonPanel}>
        <div className={styles.Button} onClick={() => setVisible(FRAME_WAITER)}>
          <span>Waiter</span>
        </div>
        <div className={styles.Button} onClick={() => setVisible(FRAME_BILL)}>
          <span>Bill</span>
        </div>
        <div className={styles.Button} onClick={() => setVisible(FRAME_MENU)}>
          <span>Menu</span>
        </div>
        <div className={styles.Button} onClick={() => setVisible(FRAME_CART)}>
          <span>Cart</span>
        </div>
      </div>
    </div>
  )
}


export default NavButtons
