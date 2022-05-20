import { createSlice } from '@reduxjs/toolkit'

const visibleComponent = createSlice({
  name: 'visibleComponent',
  initialState: {
    value: 'bill'
  },
  reducers: {
    setVisibleComponent(state, action) {
      state.value = action.payload
    }
  }
})

export const { setVisibleComponent } = visibleComponent.actions
export default visibleComponent.reducer