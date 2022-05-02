import { fetchAssets, getOrOpenBill } from "../services/apiActions";
import { setVisibleComponent } from "./billVisibleComponentSlice";
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

    // go to addItem page everytime we set an item to edit
    if (!previousState.editItem.item && currState.editItem.item) {
      store.dispatch(setVisibleComponent('addItem'));
    }

    // // clear the editing item when leaving addItem page
    if (previousState.billVisibleComponent.value === 'addItem' && currState.billVisibleComponent.value !== 'addItem' ) {
      store.dispatch(clearItem())
    }
  })
}

export default globalStateChangeHandler;