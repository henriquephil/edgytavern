import { useSelector } from "react-redux"
import SpotBill from '../../../components/SpotBill'
import style from './SpotsBills.module.css'

function SpotBills() {
  let { loading, data, error } = useSelector(state => state.openBills)

  if (loading) return 'Loading bills'
  if (error) return JSON.stringify(error)
  
  return (
    <div className={style.SpotBills}>
      {data?.bills.map((sb, idx) => (
        <div className={style.billHolder} key={idx}>
          <SpotBill spotBill={sb} />
        </div>))}
    </div>
  )
}

export default SpotBills
