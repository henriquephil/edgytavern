import { createSlice } from '@reduxjs/toolkit'

const spotSlice = createSlice({
  name: 'spot',
  initialState: {
    loading: false,
    data: null,
    error: null,
    scanNeeded: false
  },
  reducers: {
    setSpotLoading(state, action) {
      state.loading = true
      state.data = null
      state.error = null
      state.scanNeeded = false
    },
    setSpot(state, action) {
      state.loading = false
      state.data = action.payload
      state.error = null
      state.scanNeeded = false
    },
    setSpotError(state, action) {
      state.loading = false
      state.data = null
      state.error = action.payload
      state.scanNeeded = false
    },
    leaveSpot(state, action) {
      state.loading = false
      state.data = null
      state.error = null
      state.scanNeeded = false
    },
    requestScan(state, action) {
      state.scanNeeded = true
    },
    cancelRequestScan(state, action) {
      state.scanNeeded = false
    }
  }
})

export const { setSpotLoading, setSpot, setSpotError, leaveSpot, requestScan, cancelRequestScan } = spotSlice.actions
export default spotSlice.reducer