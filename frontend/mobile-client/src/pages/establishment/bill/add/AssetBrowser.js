import styles from './AssetBrowser.module.css';
import Group from "./browser/Group";
import { useSelector } from "react-redux";

function AssetBrowser({ onSelected }) {
  const assetsState = useSelector(state => state.assets);

  return (
    <div className={styles.AssetBrowser}>
      {assetsState.data?.categories.map(it => <Group key={it.hashId} group={it} onSelect={asset => onSelected(asset)}/>)}
    </div>
  );
}

export default AssetBrowser;
