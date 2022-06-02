import { createSlice } from '@reduxjs/toolkit'

const FRAME_WAITER = 0
const FRAME_BILL = 1
const FRAME_MENU = 2
const FRAME_CART = 3

const visibleFrame = createSlice({
  name: 'visibleFrame',
  initialState: {
    value: FRAME_BILL
  },
  reducers: {
    setVisibleFrame(state, action) {
      state.value = action.payload
    }
  }
})


export const { setVisibleFrame } = visibleFrame.actions
export { FRAME_WAITER, FRAME_BILL, FRAME_MENU, FRAME_CART }
export default visibleFrame.reducer