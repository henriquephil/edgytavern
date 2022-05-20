import { createSlice } from '@reduxjs/toolkit'

const editItemSlice = createSlice({
  name: 'editItem',
  initialState: {
    index: null,
    item: null
  },
  reducers: {
    updateItem(state, action) {
      state.index = action.payload.index
      state.item = action.payload.item
    },
    newItem(state, action) {
      state.index = null
      state.item = {
        asset: action.payload,
        removedIngredients: [],
        selectedAdditionals: []
      }
    },
    clearItem(state, action) {
      state.index = null
      state.item = null
    }
  }
})

export const { updateItem, newItem, clearItem } = editItemSlice.actions
export default editItemSlice.reducer