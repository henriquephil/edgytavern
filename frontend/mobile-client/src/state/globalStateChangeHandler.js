import { fetchAssets, getOrOpenBill } from "../services/apiService";
import { setVisibleComponent } from "./visibleComponentSlice";
import { clearItem } from "./editItemSlice";

function globalStateChangeHandler(store) {
  let currState = store.getState();
  store.subscribe(() => {
    const previousState = currState;
    currState = store.getState();

    // update bill and assets everytime the establishment is updated
    if (previousState.establishment.data !== currState.establishment.data) {
      store.dispatch(getOrOpenBill())
      store.dispatch(fetchAssets())  
    }

    // go to editItem page everytime we set an item to edit
    if (!previousState.editItem.item && currState.editItem.item) {
      store.dispatch(setVisibleComponent('editItem'));
    }

    // // clear the editing item when leaving editItem page
    if (previousState.visibleComponent.value === 'editItem' && currState.visibleComponent.value !== 'editItem' ) {
      store.dispatch(clearItem())
    }
  })
}

export default globalStateChangeHandler;