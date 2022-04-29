import { configureStore } from "@reduxjs/toolkit";
import establishmentReducer from './establishmentSlice'
import billReducer from './billSlice'
import ordersReducer from './ordersSlice'
import assetsReducer from './assetsSlice'

const store = configureStore({
  reducer: {
    establishment: establishmentReducer,
    bill: billReducer,
    orders: ordersReducer,
    assets: assetsReducer
  }
});

export default store;