import { createSlice } from '@reduxjs/toolkit'

const ordersSlice = createSlice({
  name: 'orders',
  initialState: {
    loading: false,
    data: null,
    error: null
  },
  reducers: {
    setOrders(state, action) {
      state.loading = false;
      state.data = action.payload;
      state.error = null;
    },
    setOrdersError(state, action) {
      state.loading = false;
      state.data = null;
      state.error = action.payload;
    },
    ordersLoading(state, action) {
      state.loading = true;
      state.data = null;
      state.error = null;
    }
  }
})

export const { setOrders, setOrdersError, ordersLoading } = ordersSlice.actions
export default ordersSlice.reducer