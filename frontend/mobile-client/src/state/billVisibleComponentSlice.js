import { createSlice } from '@reduxjs/toolkit'

const billVisibleComponent = createSlice({
  name: 'billVisibleComponent',
  initialState: {
    value: 'none'
  },
  reducers: {
    setVisibleComponent(state, action) {
      state.value = action.payload
    }
  }
})

export const { setVisibleComponent } = billVisibleComponent.actions
export default billVisibleComponent.reducer