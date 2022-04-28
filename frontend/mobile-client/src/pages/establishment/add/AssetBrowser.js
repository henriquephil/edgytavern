import { useState, useEffect } from "react";
import styles from './AssetBrowser.module.css';

import _ from 'lodash';

function randomString() {
  return _.range(Math.floor(Math.random() * 20) + 5)
    .map(i => String.fromCharCode(Math.floor(Math.random() * 26) + 65)).join('')
}

function AssetBrowser({ onSelected }) {
  const [assets, setAssets] = useState([]);
  
  useEffect(() => {
    setAssets(_.range(Math.floor(Math.random() * 30) + 40)
      .map(id => {
        return {
          id,
          name: randomString()
        }
      }));
  }, [])

  return (
    <div className={styles.AssetBrowser}>
      {assets.map(it => <div onClick={() => onSelected(it)}>{it.name}</div>)}
    </div>
  );
}

export default AssetBrowser;
