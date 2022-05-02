import styles from './AssetBrowser.module.css';
import Group from "./Group";
import { useSelector } from "react-redux";

function AssetBrowser() {
  const assetsState = useSelector(state => state.assets);

  return (
    <div className={styles.AssetBrowser}>
      {assetsState.data?.categories.map(it => <Group key={it.hashId} group={it}/>)}
    </div>
  );
}

export default AssetBrowser;
