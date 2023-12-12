import MyScroll from "../../../components/MyScroll"
import RecentOrders from "./RecentOrders"
import SpotBills from './SpotBills'
import styles from './Dashboard.module.css'
import { useDispatch, useSelector } from "react-redux"
import OpenRegister from "./OpenRegister"
import { closeRegister, fetchOpenBills } from "../../../services/apiService"
import { useEffect } from "react"

function Dashboard() {
  const { loading, data, error } = useSelector(state => state.register)
  const dispatch = useDispatch()
  
  function clickCloseRegister() {
    dispatch(closeRegister())
      .catch(err => console.log(err)) // TODO
  }

  useEffect(() => {
    dispatch(fetchOpenBills())
  }, [dispatch])

  if (loading) return 'Loading'
  if (error) return JSON.stringify(error)
  if (!data) return <OpenRegister/>

  return (
    <div className={styles.Dashboard}>
      <div className={styles.DashboardRegister}>
        Register running: {JSON.stringify(data)}
        <button onClick={() => clickCloseRegister()}>Close register</button>
      </div>
      <div className={styles.DashboardPanel}>
        <div className={styles.DashboardBills}>
          <MyScroll>
            <SpotBills />
          </MyScroll>
        </div>
        <div className={styles.RecentOrdersContainer}>
          <MyScroll>
            <RecentOrders/>
          </MyScroll>
        </div>
      </div>
    </div>
  )
}

export default Dashboard
