import { createSlice } from '@reduxjs/toolkit'

const cartSlice = createSlice({
  name: 'cart',
  initialState: {
    items: []
  },
  reducers: {
    saveCartItem(state, action) {
      if (action.payload.index === 0 || action.payload.index > 0) {
        state.items = state.items.map((item, idx) => idx === action.payload.index ? action.payload.item : item);
      } else {
        state.items.push(action.payload.item);
      }
    },
    removeFromCart(state, action) {
      state.items = state.items.filter((_, idx) => idx !== action.payload);
    },
    emptyCart(state, action) {
      state.items = [];
    }
  }
})

export const { saveCartItem, removeFromCart, emptyCart } = cartSlice.actions
export default cartSlice.reducer