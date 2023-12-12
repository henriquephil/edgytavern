import { useState } from "react"
import Button from "../../../../components/Button"
import FormInput from "../../../../components/FormInput"
import styles from "./Spots.module.css"

function AddSpotGroup({ addGroup }) {
  const [name, setName] = useState('')
  const [amount, setAmount] = useState('')

  return (
    <div className={styles.AddSpotGroup}>
      <FormInput grow="1" label="New group name">
        <input type="text" value={name} onChange={e => setName(e.target.value)}/>
      </FormInput>
      <FormInput label="Amount of spots">
        <input type="text" value={amount} onChange={e => setAmount(e.target.value)}/>
      </FormInput>
      <FormInput>
        <Button disabled={!name || !amount} onClick={() => {
          addGroup({ name, amount })
          setName('')
          setAmount('')
        }}>
          Add
        </Button>
      </FormInput>
    </div>
  )
}

export default AddSpotGroup
