import { createSlice } from '@reduxjs/toolkit'

const openBillsSlice = createSlice({
  name: 'openBills',
  initialState: {
    loading: false,
    data: null,
    error: null
  },
  reducers: {
    setOpenBillsLoading(state, action) {
      state.loading = true
      state.data = null
      state.error = null
    },
    setOpenBills(state, action) {
      state.loading = false
      state.data = action.payload
      state.error = null
    },
    setOpenBillsError(state, action) {
      state.loading = false
      state.data = null
      state.error = action.payload
    }
  }
})

export const { setOpenBillsLoading, setOpenBills, setOpenBillsError } = openBillsSlice.actions
export default openBillsSlice.reducer