import styles from './Establishment.module.css'
import EstablishmentHeader from './header/EstablishmentHeader'
import Frames from './frames/Frames'

function Establishment() {
  
  return (
    <div className={styles.Establishment}>
      <EstablishmentHeader/>
      <Frames/>
    </div>
  )
}

export default Establishment
