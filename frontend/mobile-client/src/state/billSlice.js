import { createSlice } from '@reduxjs/toolkit'

const billSlice = createSlice({
  name: 'bill',
  initialState: {
    loading: true,
    data: null,
    error: null
  },
  reducers: {
    setBill(state, action) {
      state.loading = false;
      state.data = action.payload;
      state.error = null;
    },
    setBillError(state, action) {
      state.loading = false;
      state.data = null;
      state.error = action.payload;
    },
    billLoading(state, action) {
      state.loading = true;
      state.data = null;
      state.error = null;
    }
  }
})

export const { setBill, setBillError, billLoading } = billSlice.actions
export default billSlice.reducer