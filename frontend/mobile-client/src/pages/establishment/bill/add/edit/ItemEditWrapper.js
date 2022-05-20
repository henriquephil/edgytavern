import { useSelector } from "react-redux"
import ItemEdit from "./ItemEdit"

function ItemEditWrapper() {
  const editItemState = useSelector(state => state.editItem)

  if (!editItemState.item)
    return <></>

  return <ItemEdit/>
}

export default ItemEditWrapper