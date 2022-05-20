import styles from './Establishment.module.css'
import EstablishmentHeader from './EstablishmentHeader'
import ActionButtons from './buttons/ActionButtons'
import BillWrapper from './bill/BillWrapper'

function Establishment() {
  
  return (
    <div className={styles.Establishment}>
      <EstablishmentHeader/>
      <BillWrapper/>
      <ActionButtons/>
    </div>
  )
}

export default Establishment
