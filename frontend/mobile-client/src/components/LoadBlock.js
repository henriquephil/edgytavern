import styles from './LoadBlock.module.css';

function LoadBlock({ color, visible }) {
  const style = {
    background: color
  }

  const classes = [styles.LoadBlock];
  if (visible)
    classes.push(styles.visible)
  
  return (
    <div className={classes.join(' ')} style={style}>
      Loading animation...
    </div>
  )
}

export default LoadBlock;