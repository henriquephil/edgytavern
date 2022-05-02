import styles from './Group.module.css';
import Asset from './Asset'

function Group({ group }) {

  return (
    <div className={styles.Group}>
      <div className={styles.GroupHeader}>{group.name}</div>
      <div className={styles.AssetList}>
        {group.assets.map(it => <Asset key={it.hashId} asset={it}/>)}
      </div>
    </div>
  );
}

export default Group