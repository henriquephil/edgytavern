import { Fragment, useEffect, useState } from "react"
import api from "../../../../services/api"
import SpotGroup from './SpotGroup'
import AddSpotGroup from './AddSpotGroup'
import styles from "./Spots.module.css"

function Spots() {
  const [loading, setLoading] = useState(true)
  const [spots, setSpots] = useState([])

  function loadSpots() {
    setLoading(true)
    api.get('/api/establishment/managed/spots')
      .then(({ data }) => {
        setSpots(data.groups)
      })
      .finally(() => setLoading(false))
  }

  useEffect(() => loadSpots(), [])

  const addGroup = group => {
    if (group.name && group.amount) {
      setLoading(true)
      api.post('/api/establishment/managed/spots', group)
        .then(_ => {
          loadSpots()
        })
    }
  }

  const emptyMessage = (
    <div className={styles.SpotsEmpty}>
      <span>Start by adding a Group (outside, balcony, pooltable, etc)</span>
    </div>
  )
  return (
    <>
      {spots?.length === 0 ? emptyMessage : <Fragment/>}
      <div className={styles.SpotsPane}>
        { spots.map(spot => <SpotGroup key={spot.id} spot={spot}/> )}
        <AddSpotGroup addGroup={group => addGroup(group)}/>
      </div>
    </>
    )
}

export default Spots
