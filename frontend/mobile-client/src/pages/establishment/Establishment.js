import styles from './Establishment.module.css'
import EstablishmentHeader from './header/EstablishmentHeader'
import NavButtons from './buttons/NavButtons'
import Frames from './frames/Frames'
import Overlay from './overlays/Overlay'

function Establishment() {
  
  return (
    <div className={styles.Establishment}>
      <EstablishmentHeader/>
      <Frames/>
    </div>
  )
}

export default Establishment
