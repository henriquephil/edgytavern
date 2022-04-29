import { useEffect, useState } from 'react';
import { useSelector } from 'react-redux';
import styles from './Cart.module.css';

function Cart() {
  const visibleComponentState = useSelector(state => state.billVisibleComponent);
  const [componentClassNames, setComponentClassNames] = useState([styles.Cart]);

  useEffect(() => {
    const set = new Set(componentClassNames);
    if (visibleComponentState.value === 'cart') {
      set.delete(styles.CartHidden);
    } else {
      set.add(styles.CartHidden);
    }
    setComponentClassNames([...set]);
  }, [visibleComponentState]);
  
  return (
    <div className={componentClassNames.join(' ')}>
      asdasdasd
    </div>
  );
}


export default Cart;
