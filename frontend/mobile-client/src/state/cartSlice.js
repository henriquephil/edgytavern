import { createSlice } from '@reduxjs/toolkit'
import { updateStateArrayItem } from '../utils/stateHelper'

function updateFinalValue(state) {
  state.finalValue = state.items.reduce((sum, item) => sum + (item.finalValue * item.quantity || 0), 0)
}

const cartSlice = createSlice({
  name: 'cart',
  initialState: {
    items: [],
    finalValue: 0
  },
  reducers: {
    saveCartItem(state, action) {
      if (state.items[action.payload.index]) {
        state.items = updateStateArrayItem(state.items, action.payload.index,
          (newItem, currentItem) => {
            newItem.removedIngredients = action.payload.item.removedIngredients
            newItem.selectedAdditionals = action.payload.item.selectedAdditionals
            newItem.finalValue = action.payload.item.finalValue
        })
      } else {
        state.items.push({
          ...action.payload.item,
          quantity: 1
        })
      }
      updateFinalValue(state)
    },
    removeFromCart(state, action) {
      state.items = state.items.filter((_, idx) => idx !== action.payload)
      updateFinalValue(state)
    },
    emptyCart(state, action) {
      state.items = []
      updateFinalValue(state)
    },
    increaseCartItemQuantity(state, action) {
      state.items = updateStateArrayItem(state.items, action.payload,
        (newItem, currentItem) => {
          newItem.quantity = currentItem.quantity + 1
      })
      updateFinalValue(state)
    },
    decreaseCartItemQuantity(state, action) {
      state.items = updateStateArrayItem(state.items, action.payload,
        (newItem, currentItem) => {
          if (currentItem.quantity > 1) {
            newItem.quantity = currentItem.quantity - 1
          }
      })
      updateFinalValue(state)
    }
  }
})

export const { saveCartItem, removeFromCart, emptyCart, increaseCartItemQuantity, decreaseCartItemQuantity } = cartSlice.actions
export default cartSlice.reducer