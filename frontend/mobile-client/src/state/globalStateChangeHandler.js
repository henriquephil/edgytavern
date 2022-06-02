import { fetchAssets, getOrOpenBill } from "../services/apiService"
import { clearItem } from "./editItemSlice"

function globalStateChangeHandler(store) {
  let currState = store.getState()
  store.subscribe(() => {
    const previousState = currState
    currState = store.getState()

    // update bill and assets everytime the establishment is updated
    if (previousState.establishment.data !== currState.establishment.data) {
      store.dispatch(getOrOpenBill())
      store.dispatch(fetchAssets())  
    }

    // clear the editing item when leaving editItem page
    if (previousState.visibleFrame.value === 'editItem' && currState.visibleFrame.value !== 'editItem' ) {
      store.dispatch(clearItem())
    }
  })
}

export default globalStateChangeHandler