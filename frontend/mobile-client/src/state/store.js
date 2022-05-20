import { configureStore } from "@reduxjs/toolkit"
import establishmentReducer from './establishmentSlice'
import spotReducer from './spotSlice'
import billReducer from './billSlice'
import ordersReducer from './ordersSlice'
import assetsReducer from './assetsSlice'
import cartReducer from './cartSlice'
import editItemReducer from './editItemSlice'
import visibleComponentReducer from './visibleComponentSlice'
import globalStateChangeHandler from "./globalStateChangeHandler"

const store = configureStore({
  reducer: {
    establishment: establishmentReducer,
    spot: spotReducer,
    bill: billReducer,
    orders: ordersReducer,
    assets: assetsReducer,
    cart: cartReducer,
    editItem: editItemReducer,
    visibleComponent: visibleComponentReducer
  }
})

globalStateChangeHandler(store)

export default store