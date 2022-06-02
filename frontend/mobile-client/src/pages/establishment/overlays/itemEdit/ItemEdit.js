import { useEffect, useState } from 'react'
import { useDispatch, useSelector } from 'react-redux'
import { FRAME_CART, setVisibleFrame } from '../../../../state/visibleFrameSlice'
import { saveCartItem } from '../../../../state/cartSlice'
import styles from './ItemEdit.module.css'
import ToggableItem from './ToggableItem'
import { clearItem } from "../../../../state/editItemSlice"
import { useList } from 'react-use'

function ItemEdit() {
  const dispatch = useDispatch()
  const editItemState = useSelector(state => state.editItem)
  const [ingredients, ingredientsActions] = useList([])
  const [additionals, additionalsActions] = useList([])
  const [finalValue, setFinalValue] = useState(0)

  useEffect(() => {
    ingredientsActions.set(
      (editItemState.item?.asset.ingredients || []).map(it => {
        return {
          remove: editItemState.item.removedIngredients.findIndex(hashId => hashId === it.hashId) >= 0,
          value: it
        }
      })
    )
    additionalsActions.set(
      (editItemState.item?.asset.additionals || []).map(it => {
        return {
          selected: editItemState.item.selectedAdditionals.findIndex(hashId => hashId === it.hashId) >= 0,
          value: it
        }
      })
    )
  }, [editItemState.item?.asset])

  useEffect(() => {
    if (editItemState.item)
    {
      setFinalValue(additionals.filter(it => it.selected)
        .reduce((sum, actual) => sum + actual.value.price, editItemState.item.asset.price))
    } else{
      setFinalValue(0)
    }
  }, [additionals, editItemState.item])

  function changedIngredient(item, value) {  
    additionalsActions.update(it => it === item, {
      selected: !item.selected,
      value: it
    })
  }
  function changedAdditional(item, value) {    
    additionalsActions.update(it => it === item, {
      selected: !item.selected,
      value: it
    })
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
    dispatch(clearItem())
    dispatch(setVisibleFrame(FRAME_CART))
  }

  function cancel() {
    dispatch(clearItem())
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
        <button onClick={() => cancel()}>Cancel</button>
      </div>
    </div>
  )
}

export default ItemEdit
