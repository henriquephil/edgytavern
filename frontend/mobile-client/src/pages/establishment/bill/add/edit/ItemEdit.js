import { useEffect, useState } from 'react'
import { useDispatch, useSelector } from 'react-redux'
import { setVisibleComponent } from '../../../../../state/visibleComponentSlice'
import { saveCartItem } from '../../../../../state/cartSlice'
import styles from './ItemEdit.module.css'
import ToggableItem from './ToggableItem'

function ItemEdit() {
  const dispatch = useDispatch()
  const editItemState = useSelector(state => state.editItem)
  const [ingredients, setIngredients] = useState([])
  const [additionals, setAdditionals] = useState([])
  const [finalValue, setFinalValue] = useState(0)

  useEffect(() => {
    setIngredients(
      editItemState.item.asset.ingredients.map(it => {
        return {
          remove: editItemState.item.removedIngredients.findIndex(hashId => hashId === it.hashId) >= 0,
          value: it
        }
      }) || []
    )
    setAdditionals(
      editItemState.item.asset.additionals.map(it => {
        return {
          selected: editItemState.item.selectedAdditionals.findIndex(hashId => hashId === it.hashId) >= 0,
          value: it
        }
      }) || []
    )
  }, [editItemState, editItemState.item])

  useEffect(() => {
    if (editItemState.item)
    {
      setFinalValue(additionals.filter(it => it.selected)
        .reduce((sum, actual) => sum + actual.value.price, editItemState.item.asset.price))
    } else{
      setFinalValue(0)
    }
  }, [additionals, editItemState.item])

  function changedIngredient(ing, value) {
    setIngredients(ingredients.map(it => {
      if (it === ing) {
        return {
          remove: value,
          value: it.value
        }
      }
      return it
    }))
  }
  function changedAdditional(add, value) {
    setAdditionals(additionals.map(it => {
      if (it === add) {
        return {
          selected: value,
          value: it.value
        }
      }
      return it
    }))
  }

  function confirm() {
    dispatch(saveCartItem(
      {
        index: editItemState.index,
        item: {
          asset: editItemState.item.asset,
          removedIngredients: ingredients.filter(it => it.remove).map(it => it.value.hashId),
          selectedAdditionals: additionals.filter(it => it.selected).map(it => it.value.hashId),
          finalValue
        }
      }
    ))
    dispatch(setVisibleComponent('cart'))
  }

  return (
    <div className={styles.ItemEdit}>
      <div>
        <div>
          {editItemState.item.asset.name}
        </div>
        <div>
          {editItemState.item.asset.price}
        </div>
        <div>
          {finalValue}
        </div>
      </div>
      <div>
        <div>
          Remove ingredients
        </div>
        <div>
          {ingredients.map(it =>
          <ToggableItem active={it.remove} onChange={value => changedIngredient(it, value)}>
            <div>
              {it.value.name}
            </div>
          </ToggableItem>)}
        </div>
      </div>
      <div>
        <div>
          Request additional items
        </div>
        <div>
          {additionals.map(it =>
          <ToggableItem active={it.selected} onChange={value => changedAdditional(it, value)}>
            <div>
              {it.value.name}
            </div>
            <div className={styles.additionalValue}>
              {it.value.price}
            </div>
          </ToggableItem>)}
        </div>
      </div>
      <div>
        <button onClick={() => confirm()}>Confirm</button>
      </div>
    </div>
  )
}

export default ItemEdit
