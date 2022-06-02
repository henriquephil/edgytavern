import { useSelector } from 'react-redux'
import styles from './frames.module.css'

function CartFrame({ frame, children }) {
  const visibleFrameState = useSelector(state => state.visibleFrame)

  const classes = [styles.Frame]
  if (visibleFrameState.value !== frame) {
    classes.push(styles.hidden)
    if (visibleFrameState.value < frame)
      classes.push(styles.right)
    else
      classes.push(styles.left)
  }
  return (
    <div className={classes.join(' ')}>
      { children }
    </div>
  )
}

export default CartFrame