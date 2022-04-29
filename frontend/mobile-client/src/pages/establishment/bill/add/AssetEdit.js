import { useEffect, useState } from 'react';
import styles from './AssetEdit.module.css';
import ToggableItem from './edit/ToggableItem';

function AssetEdit({ asset }) {
  const [removableIngredients, setRemovableIngredients] = useState(null)
  const [selectedAdditionals, setSelectedAdditionals] = useState(null)

  useEffect(() => {
    setRemovableIngredients(
      asset?.ingredients.map(it => {
        return {
          remove: false,
          value: it
        }
      })
    );
    setSelectedAdditionals(
      asset?.additionals.map(it => {
        return {
          selected: false,
          value: it
        }
      })
    );
  }, [asset])
    
  if (!asset)
    return <></>

  const toggleIngredient = ing => {
    let items = [...removableIngredients];
    const toUpdate = items.find(it => it === ing);
    toUpdate.remove = !toUpdate.remove;
    setRemovableIngredients(items)
  }
  const toggleAdditional = add => {
    let items = [...selectedAdditionals];
    const toUpdate = items.find(it => it === add);
    toUpdate.selected = !toUpdate.selected;
    setSelectedAdditionals(items)
  }

  return (
    <div className={styles.AssetEdit}>
      <div>
        <div>
          {asset.name}
        </div>
        <div>
          {asset.price}
        </div>
      </div>
      <div>
        <div>
          Remove ingredients
        </div>
        <div>
          {removableIngredients?.map(it => <ToggableItem active={it.remove} toggle={() => toggleIngredient(it)}>
            <div className={styles.list}>
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
          {selectedAdditionals?.map(it => <ToggableItem active={it.selected} toggle={() => toggleAdditional(it)}>
            <div className={styles.list}>
              <div>
                {it.value.name}
              </div>
              <div className={styles.additionalValue}>
                {it.value.price}
              </div>
            </div>
          </ToggableItem>)}
        </div>
      </div>
    </div>
  );
}

export default AssetEdit;
