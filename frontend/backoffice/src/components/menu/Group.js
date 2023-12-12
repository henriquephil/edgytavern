import styles from './menu.module.css'
import Asset from './Asset'

function Group({ group }) {

  return (
    <div className={styles.Group}>
      <div className={styles.groupHeader}>{group.name}</div>
      <div className={styles.assetList}>
        {group.assets.map(it => <Asset key={it.hashId} asset={it}/>)}
      </div>
    </div>
  )
}

export default Group