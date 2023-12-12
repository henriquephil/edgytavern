import { useState } from "react"
import Button from '../../../../components/Button'
import FormInput from "../../../../components/FormInput"
import styles from "./AddCategory.module.css"

function AddCategory({ addCategory }) {
  const [name, setName] = useState('')

  return (
    <div className={styles.AddCategory}>
      <FormInput grow="1" label="New category name">
        <input type="text" value={name} onChange={e => setName(e.target.value)}/>
      </FormInput>
      <FormInput grow="-1">
        <Button disabled={!name} onClick={() => {
          addCategory({ name })
          setName('')
        }}>
          Add
        </Button>
      </FormInput>
    </div>)
}

export default AddCategory
