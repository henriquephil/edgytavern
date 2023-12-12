import { NavLink } from "react-router-dom"
import style from './Menubar.module.css'
import { useSelector } from 'react-redux'
import { auth } from 'tavern-commons'

function Menubar() {
  const establishment = useSelector(state => state.establishment.data)

  return (
    <div className={style.Menubar}>
      <div className={style.container}>
        <div className={style.links}>
          <div>
            <NavLink to="/dashboard" className={({ isActive }) => isActive ? style.linkActive : ""}>Dashboard</NavLink>
          </div>
          <div>
            <NavLink to="/manage" className={({ isActive }) => isActive ? style.linkActive : ""}>Manage</NavLink>
          </div>
        </div>
        <div className={style.establishmentName}><span>{establishment.name}</span></div>
        <div onClick={() => auth.signOut()} cursor="pointer">User</div>
      </div>
    </div>)
}

export default Menubar
