import { createSlice } from '@reduxjs/toolkit'

const billVisibleComponent = createSlice({
  name: 'billVisibleComponent',
  initialState: {
    value: 'none'
  },
  reducers: {
    setVisibleComponent(state, action) {
      state.value = action.payload;
    },
    closeOpenBillComponent(state, action) {
      state.value = 'none';
    },
  }
})

export const { setVisibleComponent, closeOpenBillComponent } = billVisibleComponent.actions
export default billVisibleComponent.reducer