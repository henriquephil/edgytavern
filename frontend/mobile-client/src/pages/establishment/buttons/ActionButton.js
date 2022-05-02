import { useEffect, useState } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import { setVisibleComponent } from '../../../state/billVisibleComponentSlice';
import styles from './ActionButton.module.css';

function ActionButton({ component, color, text, visibleWhen }) {
  const dispatch = useDispatch();
  const [componentClassNames, setComponentClassNames] = useState([styles.ActionButton]);
  const billVisibleComponentState = useSelector(state => state.billVisibleComponent)
  
  const clickEvent = () => {
    dispatch(setVisibleComponent(component));
  }

  useEffect(() => {
    const set = new Set(componentClassNames);
    if (visibleWhen.indexOf(billVisibleComponentState.value) >= 0) {
      set.add(styles.ActionButtonVisible);
    } else {
      set.delete(styles.ActionButtonVisible);
    }
    setComponentClassNames([...set]);
  }, [billVisibleComponentState]);

  return (
    <div className={componentClassNames.join(' ')} style={{ background: color, 'box-shadow': `0 0 3px 2px ${color}` }} onClick={() => clickEvent()}>
      {text}
    </div>
  );
}


export default ActionButton;
