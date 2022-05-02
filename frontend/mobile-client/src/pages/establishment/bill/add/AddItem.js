import { useSelector } from "react-redux";
import styles from './AddItem.module.css';
import AssetBrowser from './browser/AssetBrowser';
import ItemEdit from './edit/ItemEdit';

function AddItem() {
  const visibleComponentState = useSelector(state => state.billVisibleComponent);
  const editItemState = useSelector(state => state.editItem);

  const classNames = [styles.AddItem];
  if (visibleComponentState.value !== 'addItem')
    classNames.push(styles.AddItemHidden)

  const classNamesContent = [styles.AddItemContent];
  if (editItemState.item?.asset)
    classNamesContent.push(styles.AddItemContentTranslated)

  return (
    <div className={classNames.join(' ')}>
      <div className={classNamesContent.join(' ')}>
        <div className={styles.AddItemContentScroll}>
          <AssetBrowser/>
        </div>
        <div className={styles.AddItemContentScroll}>
          <ItemEdit/>
        </div>
      </div>
    </div>
  );
}

export default AddItem;
