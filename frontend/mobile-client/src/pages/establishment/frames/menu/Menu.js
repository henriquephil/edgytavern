import styles from './menu.module.css'
import Group from "./Group"
import { useSelector } from "react-redux"

function Menu() {
  const assetsState = useSelector(state => state.assets)
  /* I could get rid of the state for the asset list
   *
   * Do a request everytime this page opens and use some
   * caching (https://www.npmjs.com/package/axios-cache-adapter)
   * to avoid too many hits in backend
   */

  return (
    <div className={styles.menu}>
      {assetsState.data?.categories.map(it => <Group key={it.hashId} group={it}/>)}
    </div>
  )
}

export default Menu
