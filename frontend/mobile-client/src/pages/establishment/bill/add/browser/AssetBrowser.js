import styles from './AssetBrowser.module.css';
import Group from "./Group";
import { useSelector } from "react-redux";

function AssetBrowser() {
  const assetsState = useSelector(state => state.assets);
  /* I could get rid of the state for the asset list
   *
   * Do a request everytime this page opens and use some
   * caching (https://www.npmjs.com/package/axios-cache-adapter)
   * to avoid too many hits in backend
   *
   * But for that I MAY need to think of some solution 
   * to not fetch this when I am just editing a cart item
   */
  const editItemState = useSelector(state => state.editItem);

  if (editItemState.item)
    return <></>;

  return (
    <div className={styles.AssetBrowser}>
      {assetsState.data?.categories.map(it => <Group key={it.hashId} group={it}/>)}
    </div>
  );
}

export default AssetBrowser;
