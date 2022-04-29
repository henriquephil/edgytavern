import { useEffect, useState } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import { setVisibleComponent } from '../../../../state/billVisibleComponentSlice';
import styles from './BillButton.module.css';

function BillButton({ component, color, text, visibleWhen }) {
  const dispatch = useDispatch();
  const [componentClassNames, setComponentClassNames] = useState([styles.BillButton]);
  const billVisibleComponentState = useSelector(state => state.billVisibleComponent)
  
  const clickEvent = () => {
    dispatch(setVisibleComponent(component));
  }

  useEffect(() => {
    const set = new Set(componentClassNames);
    if (visibleWhen.indexOf(billVisibleComponentState.value) >= 0) {
      set.add(styles.BillButtonVisible);
    } else {
      set.delete(styles.BillButtonVisible);
    }
    setComponentClassNames([...set]);
  }, [billVisibleComponentState]);

  return (
    <div className={componentClassNames.join(' ')} style={{ background: color }} onClick={() => clickEvent()}>
      {text}
    </div>
  );
}


export default BillButton;
