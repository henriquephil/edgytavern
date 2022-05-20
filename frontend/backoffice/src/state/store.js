import { configureStore } from "@reduxjs/toolkit"
import establishmentReducer from './establishmentSlice'
import registerReducer from './registerSlice'
import globalStateChangeHandler from "./globalStateChangeHandler"
import openBillsReducer from './openBillsSlice'

const store = configureStore ({
  reducer: {
    establishment: establishmentReducer,
    register: registerReducer,
    openBills: openBillsReducer,
  }
})

globalStateChangeHandler(store)

export default store
