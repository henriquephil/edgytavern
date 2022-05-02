import { useEffect, useState } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import { setVisibleComponent } from '../../../../../state/billVisibleComponentSlice';
import { saveCartItem } from '../../../../../state/cartSlice';
import styles from './ItemEdit.module.css';
import ToggableItem from './ToggableItem';

function ItemEdit() {
  const dispatch = useDispatch();
  const editItemState = useSelector(state => state.editItem);
  const [ingredients, setIngredients] = useState(null)
  const [additionals, setAdditionals] = useState(null)

  useEffect(() => {
    setIngredients(
      editItemState.item?.asset.ingredients.map(it => {
        return {
          remove: editItemState.item.removedIngredients.findIndex(i => i === it) >= 0,
          value: it
        }
      })
    );
    setAdditionals(
      editItemState.item?.asset.additionals.map(it => {
        return {
          selected: editItemState.item.selectedAdditionals?.findIndex(a => a === it) >= 0,
          value: it
        }
      })
    );
  }, [editItemState])

  if (!editItemState.item)
    return <></>;

  const changedIngredient = (ing, value) => {
    setIngredients(ingredients.map(it => {
      if (it === ing) {
        return {
          remove: value,
          value: it.value
        }
      }
      return it;
    }));
  }
  const changedAdditional = (add, value) => {
    setAdditionals(additionals.map(it => {
      if (it === add) {
        return {
          selected: value,
          value: it.value
        }
      }
      return it;
    }));
  }

  const confirm = () => {
    dispatch(saveCartItem(
      {
        index: editItemState.index,
        item: {
          asset: editItemState.item.asset,
          removedIngredients: ingredients.filter(it => it.remove).map(it => it.value),
          selectedAdditionals: additionals.filter(it => it.selected).map(it => it.value)
        }
      }
    ));
    dispatch(setVisibleComponent('cart'));
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
      </div>
      <div>
        <div>
          Remove ingredients
        </div>
        <div>
          {ingredients?.map(it =>
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
          {additionals?.map(it =>
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
  );
}

export default ItemEdit;
