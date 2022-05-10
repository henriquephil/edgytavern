import { useSelector } from "react-redux";
import styles from './AddItem.module.css';
import AssetBrowser from './browser/AssetBrowser';
import ItemEditWrapper from "./edit/ItemEditWrapper";

function AddItem() {
  const visibleComponentState = useSelector(state => state.visibleComponent);

  const classNames = [styles.AddItem];
  if (!['menu', 'editItem'].includes(visibleComponentState.value))
    classNames.push(styles.AddItemHidden)

  const classNamesContent = [styles.AddItemContent];
  if (visibleComponentState.value === 'editItem')
    classNamesContent.push(styles.AddItemContentTranslated)

  return (
    <div className={classNames.join(' ')}>
      <div className={classNamesContent.join(' ')}>
        <div className={styles.AddItemContentScroll}>
          <AssetBrowser/>
        </div>
        <div className={styles.AddItemContentScroll}>
          <ItemEditWrapper/>
        </div>
      </div>
    </div>
  );
}

export default AddItem;
