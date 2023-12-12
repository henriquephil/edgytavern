import { useState } from 'react'
import { useDispatch } from 'react-redux'
import { createEstablishment } from '../services/apiService'
import styles from "./CreateEstablishment.module.css"

function CreateEstablishment() {
  const [ name, setName ] = useState('')
  const dispatch = useDispatch()
  
  function nameEstablishment() {
    dispatch(createEstablishment(name))
  }

  return (
    <div className={styles.CreateEstablishment}>
      <div className={styles.container}>
        <p>You haven't named your establishment yet</p>
        <input type="text" value={name} onChange={e => setName(e.target.value)} />
        <button disabled={name.length < 1} onClick={nameEstablishment}>Submit</button>
      </div>
    </div>
  )
}

export default CreateEstablishment
