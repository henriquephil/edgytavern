import { useRef } from 'react'
import { useDispatch, useSelector } from 'react-redux'
import { fetchEstablishment } from '../services/apiService'
import QrVideo from './establishment/overlays/scanSpot/QrVideo'
import styles from './EstablishmentLoader.module.css'

function EstablishmentLoader() {
  const dispatch = useDispatch()
  const videoRef = useRef(null)
  const establishmentState = useSelector(state => state.establishment)

  if (establishmentState.loading || establishmentState.data)
    return <></>

  const handleScan = data => {
    if (data) {
      const url = new URL(data)
      dispatch(fetchEstablishment(url.searchParams.get('e'), url.searchParams.get('s')))
    }
  }
  const handleError = err => {
    console.error(err)
  }

  const href = new URL(window.location.href)
  if (href.searchParams.get('e') && href.searchParams.get('s')) {
    handleScan(href)
    window.history.replaceState({}, document.title, "/")
    return <></>
  }

  return (
    <div className={styles.EstablishmentLoader}>
      <QrVideo onResult={res => handleScan(res)}/>
      <p>Where are you at?</p>
      <p>Scan your location's QR code to access the menu</p>
      <button onClick={() => handleScan('http://localhost:3001/?e=9aMron&s=ZLbXoN')}>test</button>
      <p><strong>TODO improve this page layout</strong></p>
      <p><strong>TODO what to show/control if users doesn't allow camera?</strong></p>
    </div>
  )
}

export default EstablishmentLoader
