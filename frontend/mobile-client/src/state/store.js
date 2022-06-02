import { combineReducers, configureStore } from "@reduxjs/toolkit"
import { persistReducer } from 'redux-persist'
import storage from 'redux-persist/lib/storage'
import establishmentReducer from './establishmentSlice'
import spotReducer from './spotSlice'
import billReducer from './billSlice'
import ordersReducer from './ordersSlice'
import assetsReducer from './assetsSlice'
import cartReducer from './cartSlice'
import editItemReducer from './editItemSlice'
import visibleFrameReducer from './visibleFrameSlice'
import globalStateChangeHandler from "./globalStateChangeHandler"


const reducers = combineReducers({
  establishment: establishmentReducer,
  spot: spotReducer,
  bill: billReducer,
  orders: ordersReducer,
  assets: assetsReducer,
  cart: cartReducer,
  editItem: editItemReducer,
  visibleFrame: visibleFrameReducer
 });
 
 const persistConfig = {
     key: 'root',
     storage
 };
 
 const persistedReducer = persistReducer(persistConfig, reducers);

 
const store = configureStore({
  reducer: persistedReducer
})

globalStateChangeHandler(store)

export default store