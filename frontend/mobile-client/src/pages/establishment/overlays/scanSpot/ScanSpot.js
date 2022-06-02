import styles from './scanSpot.module.css'
import { cancelRequestScan } from '../../../../state/spotSlice'
import { useDispatch } from 'react-redux'
import { fetchSpot } from '../../../../services/apiService'
import QrVideo from './QrVideo'

function ScanSpot() {
  const dispatch = useDispatch()
  
  function onResult(data) {
    const url = new URL(data)
    dispatch(fetchSpot(url.searchParams.get('s')))
  }

  function cancel() {
    dispatch(cancelRequestScan())
  }

  return(
    <div className={styles.scanSpot}>
      <span>Scan QR code</span><button onClick={() => cancel()}>cancel</button>
      <QrVideo onResult={res => onResult(res)}/>
    </div>
  )
}

export default ScanSpot