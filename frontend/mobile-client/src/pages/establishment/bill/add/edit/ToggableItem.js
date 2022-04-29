import styles from './ToggableItem.module.css';

function ToggableItem({ active, children, toggle }) {
  return (
    <div className={styles.ToggableItem} onClick={() => toggle()}>
      { children }
    </div>
  )
};

export default ToggableItem;