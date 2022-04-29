import { useState, useEffect } from "react";
import styles from './AddOrder.module.css';
import AssetBrowser from './AssetBrowser';
import AssetEdit from './AssetEdit';

function AddOrder() {
  const [visible, setVisible] = useState(false);
  const [asset, setAsset] = useState(null);
  const [componentClassNames, setComponentClassNames] = useState([styles.AddOrder]);
  const [contentClassNames, setContentClassNames] = useState([styles.AddOrderContent]);
  
  useEffect(() => {
    console.log(asset)
    const set = new Set(contentClassNames);
    if (asset)
      set.add(styles.AddOrderContentTranslated);
    else
      set.delete(styles.AddOrderContentTranslated);
    setContentClassNames([...set]);
  }, [asset]);
  
  useEffect(() => {
    const set = new Set(componentClassNames);
    if (visible) {
      set.delete(styles.AddOrderHidden);
    } else {
      set.add(styles.AddOrderHidden);
      setAsset(null);
    }
    setComponentClassNames([...set]);
  }, [visible]);
    
  const toggle = () => {
    setVisible(!visible);
  }

  const cancelEditAsset = () => {
    setAsset(null);
  }

  return (
    <div className={componentClassNames.join(' ')}>
      <div className={styles.AddOrderHeader}>
        <div>
          {asset && <button onClick={() => setAsset(null)}>Back</button>}
        </div>
        <div>
          <button onClick={() => toggle()}>{visible? 'Cancel' : 'Add something'}</button>
        </div>
      </div>
      <div className={contentClassNames.join(' ')}>
        <div className={styles.AddOrderContentScroll}>
          <AssetBrowser onSelected={asset => setAsset(asset)}/>
        </div>
        <div className={styles.AddOrderContentScroll}>
          <AssetEdit asset={asset} cancel={() => cancelEditAsset()}/>
        </div>
      </div>
    </div>
  );
}

export default AddOrder;
