import { useEffect, useRef, useState } from 'react'
import { useSelector } from 'react-redux'
import { useList } from 'react-use'
import ItemEdit from './itemEdit/ItemEdit'
import styles from './overlay.module.css'
import ScanSpot from './scanSpot/ScanSpot'

function Overlay() {
  const spotState = useSelector(state => state.spot)
  const editItemState = useSelector(state => state.editItem)

  const classes = [styles.overlay]
  if (spotState.scanNeeded)
    var child = <ScanSpot/>
  else if (editItemState.item)
    var child = <ItemEdit/>
  else
    classes.push(styles.hidden)

  return (
    <div className={classes.join(' ')}>
      <div className={styles.content}>
        {child}
      </div>
    </div>
  )
}

export default Overlay
