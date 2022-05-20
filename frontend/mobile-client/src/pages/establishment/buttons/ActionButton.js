import { useDispatch, useSelector } from 'react-redux'
import { setVisibleComponent } from '../../../state/visibleComponentSlice'
import styles from './ActionButton.module.css'

function ActionButton({ component, color, text, visibleWhen }) {
  const dispatch = useDispatch()
  const visibleComponentState = useSelector(state => state.visibleComponent)
  
  function clickEvent() {
    dispatch(setVisibleComponent(component))
  }
  
  const componentClassNames = [styles.ActionButton]
  if (visibleWhen.indexOf(visibleComponentState.value) >= 0)
    componentClassNames.push(styles.ActionButtonVisible)

  return (
    <div className={componentClassNames.join(' ')} style={{ 'box-shadow': `0 0 2px 3px ${color}` }} onClick={() => clickEvent()}>
      {text}
    </div>
  )
}


export default ActionButton
