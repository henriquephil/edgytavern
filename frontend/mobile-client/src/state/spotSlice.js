import { createSlice } from '@reduxjs/toolkit'

const spotSlice = createSlice({
  name: 'spot',
  initialState: {
    loading: false,
    data: null,
    error: null
  },
  reducers: {
    setSpotLoading(state, action) {
      state.loading = true;
      state.data = null;
      state.error = null;
    },
    setSpot(state, action) {
      state.loading = false;
      state.data = action.payload;
      state.error = null;
    },
    setSpotError(state, action) {
      state.loading = false;
      state.data = null;
      state.error = action.payload;
    },
    leaveSpot(state, action) {
      state.loading = false;
      state.data = null;
      state.error = null;
    }
  }
})

export const { setSpotLoading, setSpot, setSpotError, leaveSpot } = spotSlice.actions
export default spotSlice.reducer