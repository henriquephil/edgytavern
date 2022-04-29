import { useState, useEffect } from "react";
import { useSelector } from "react-redux";
import styles from './AddAsset.module.css';
import AssetBrowser from './AssetBrowser';
import AssetEdit from './AssetEdit';

function AddAsset() {
  const visibleComponentState = useSelector(state => state.billVisibleComponent);
  const [asset, setAsset] = useState(null);
  const [componentClassNames, setComponentClassNames] = useState([styles.AddAsset]);
  const [contentClassNames, setContentClassNames] = useState([styles.AddAssetContent]);
  
  useEffect(() => {
    const set = new Set(contentClassNames);
    if (asset)
      set.add(styles.AddAssetContentTranslated);
    else
      set.delete(styles.AddAssetContentTranslated);
    setContentClassNames([...set]);
  }, [asset]);
  
  useEffect(() => {
    const set = new Set(componentClassNames);
    if (visibleComponentState.value === 'addAsset') {
      set.delete(styles.AddAssetHidden);
    } else {
      set.add(styles.AddAssetHidden);
      setAsset(null);
    }
    setComponentClassNames([...set]);
  }, [visibleComponentState]);
  
  const cancelEditAsset = () => {
    setAsset(null);
  }

  return (
    <div className={componentClassNames.join(' ')}>
      <div className={contentClassNames.join(' ')}>
        <div className={styles.AddAssetContentScroll}>
          <AssetBrowser onSelected={asset => setAsset(asset)}/>
        </div>
        <div className={styles.AddAssetContentScroll}>
          <AssetEdit asset={asset} cancel={() => cancelEditAsset()}/>
        </div>
      </div>
    </div>
  );
}

export default AddAsset;
