import MyScroll from "../../../components/MyScroll"
import styles from "./Manage.module.css"
import { NavLink, Outlet } from "react-router-dom"

function Manage() {
  return (
    <div className={styles.Manage}>
      <div className={styles.links}>
        <div className={styles.linkBox}>
          <NavLink to="/manage/categories-assets" className={({ isActive }) => isActive ? styles.active : ""}>Assets</NavLink>
        </div>
        <div className={styles.linkBox}>
          <NavLink to="/manage/spots" className={({ isActive }) => isActive ? styles.active : ""}>Spots</NavLink>
        </div>
      </div>
      <div className={styles.panel}>
        <MyScroll>
          <Outlet />
        </MyScroll>
      </div>
    </div>
  )
}

export default Manage
