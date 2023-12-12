import { useContext, useState } from "react"
import { useList } from "react-use"
import api from '../../../../../services/api'
import { ModalContext } from "../../../../../components/modal/ModalContext"
import styles from "./EditAssetModal.module.css"
import FormInput from "../../../../../components/FormInput"
import Button from "../../../../../components/Button"

// TODO break into components
function EditAssetModal({asset}) {
  if (!asset.category) throw new Error("Asset must have a predetermined category")

  const [ name, setName ] = useState(asset.name)
  const [ active, setActive ] = useState(asset.active)
  const [ price, setPrice ] = useState(asset.price)
  const [ description, setDescription ] = useState(asset.description)
  const [ ingredients, ingredientsActions ] = useList(asset.ingredients)
  const [ ingredientName, setIngredientName ] = useState('')
  const [ ingredientRemovable, setIngredientRemovable ] = useState(false)
  const [ additionals, additionalsActions ] = useList(asset.additionals)
  const [ additionalName, setadditionalName ] = useState('')
  const [ additionalPrice, setAdditionalPrice ] = useState('')
  const { closeModal } = useContext(ModalContext)

  const [ saveLoading, setSaveLoading ] = useState(false)

  function save() {
    const body = {
      name, active, categoryId: asset.category.id, price, description, ingredients, additionals
    }
    if (body.name && body.categoryId && body.price) {
      setSaveLoading(true)
      const promise = asset.id ? 
        api.put(`/api/establishment/managed/assets/${asset.id}`, body) :
        api.post('/api/establishment/managed/assets', body)
      promise
        .then(() => closeModal(true))
        .finally(() => setSaveLoading(false))
    }
  }

  const renderIngredient = ingredient => {
    return <div key={ingredient.id} className={styles.ingredient}>
      <FormInput grow="1">
        <span readOnly>{ingredient.name}</span>
      </FormInput>
      <Button>
        Edit
      </Button>
    </div>
  }

  function renderNewIngredient() {
    return (
      <div className={styles.ingredient}>
        <FormInput grow="1">
          <input type="text" placeholder="Name" value={ingredientName} onChange={e => setIngredientName(e.target.value)}/>
        </FormInput>
        <Button onClick={() => setIngredientRemovable(!ingredientRemovable)} variant="ghost">
          {ingredientRemovable ? "U" /*nlock*/ : "L"/*ock*/}
        </Button>
        <Button onClick={() => {
          if (ingredientName && (typeof ingredientRemovable === 'boolean'))
            ingredientsActions.push({ name: ingredientName, removable: ingredientRemovable })
        }}>
          Add
        </Button>
      </div>
    )
  }

  function renderAdditional(additional) {
    return (
      <div className={styles.additional}>
        <FormInput grow="1">
          <span>{additional.name}</span>
        </FormInput>
        <FormInput grow="1">
          <span>{additional.price}</span>
        </FormInput>
        <Button>
          Edit
        </Button>
      </div>
    )
  }

  function renderNewAdditional() {
    return (
    <div className={styles.additional}>
      <FormInput grow="1">
        <input type="text" placeholder="Name" value={additionalName} onChange={e => setadditionalName(e.target.value)}/>
      </FormInput>
      <FormInput grow="1">
        <input type="number" placeholder="Price" value={additionalPrice} onChange={e => setAdditionalPrice(e.target.value)}/>
      </FormInput>
      <Button onClick={() => {
        if (additionalName && additionalPrice)
          additionalsActions.push({ name: additionalName, price: additionalPrice })
      }}>
        Add
      </Button>
    </div>
    )
  }

  return (
    <div className={styles.EditAssetModal}>
      <FormInput w="50" grow="1" label="Name">
          <input type="text" value={name} onChange={e => setName(e.target.value)}/>
      </FormInput>
      {asset.id ? (
        <FormInput grow="-1" label="Active">
          {/* <Checkbox isChecked={active} onChange={e => setActive(e.target.checked)} colorScheme="whiteAlpha">Active</Checkbox> */}
          <input type="checkbox" onChange={e => setActive(e.target.checked)} />
        </FormInput>) : null}
      <FormInput w="70" label="Category">
          <input type="text" value={asset.category.name} readOnly tabIndex="-1" />
      </FormInput>
      <FormInput w="30" label="Price">
          <input type="number" value={price} onChange={e => setPrice(e.target.value)}/>
      </FormInput>
      <FormInput w="100" label="Detailed description">
          <input type="text" value={description} onChange={e => setDescription(e.target.value)}/>
      </FormInput>

      <FormInput w="50" label="Ingredients" top>
        {ingredients.map(renderIngredient)}
        {renderNewIngredient()}
      </FormInput>

      <FormInput w="50" label="Additionals" top>
        {additionals.map(renderAdditional)}
        {renderNewAdditional()}
      </FormInput>

      <div className={styles.controls}>
        <div><Button onClick={() => closeModal()}>Cancel</Button></div>
        <div><Button onClick={() => save()} isLoading={saveLoading} primary>Save</Button></div>
      </div>
    </div>
  )
}

export default EditAssetModal