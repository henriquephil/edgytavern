import styles from './ToggableItem.module.css'

function ToggableItem({ active, children, onChange }) {
  return (
    <div className={styles.ToggableItem} onClick={() => onChange(!active)}>
      <div style={{width: '30px'}}>
        {active ? '[X]' : '[]'}
      </div>
      { children }
    </div>
  )
}

export default ToggableItem