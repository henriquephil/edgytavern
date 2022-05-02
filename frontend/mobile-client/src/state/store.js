import { configureStore } from "@reduxjs/toolkit";
import establishmentReducer from './establishmentSlice'
import billReducer from './billSlice'
import ordersReducer from './ordersSlice'
import assetsReducer from './assetsSlice'
import cartReducer from './cartSlice'
import editItemReducer from './editItemSlice'
import billVisibleComponentReducer from './billVisibleComponentSlice'
import globalStateChangeHandler from "./globalStateChangeHandler";

const store = configureStore({
  reducer: {
    establishment: establishmentReducer,
    bill: billReducer,
    orders: ordersReducer,
    assets: assetsReducer,
    cart: cartReducer,
    editItem: editItemReducer,
    billVisibleComponent: billVisibleComponentReducer
  }
});

globalStateChangeHandler(store);

export default store;