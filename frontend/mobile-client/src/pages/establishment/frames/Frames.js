import styles from './frames.module.css'
import Frame from './Frame'
import Cart from './cart/Cart'
import Menu from './menu/Menu'
import BillWrapper from './bill/BillWrapper'
import NavButtons from '../buttons/NavButtons'
import Overlay from '../overlays/Overlay'
import { FRAME_BILL, FRAME_CART, FRAME_MENU, FRAME_WAITER } from '../../../state/visibleFrameSlice'
import Waiter from './waiter/Waiter'

function Frames() {
  return (
    <div className={styles.root}>
      <div className={styles.frameSpace}>
        <Frame frame={FRAME_WAITER}>
          <Waiter/>
        </Frame>
        <Frame frame={FRAME_BILL}>
          <BillWrapper/>
        </Frame>
        <Frame frame={FRAME_MENU}>
          <Menu/>
        </Frame>
        <Frame frame={FRAME_CART}>
          <Cart/>
        </Frame>
      </div>
      <NavButtons/>
      <Overlay/>
    </div>
  )
}

export default Frames