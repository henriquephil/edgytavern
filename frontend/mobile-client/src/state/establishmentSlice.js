import { createSlice } from '@reduxjs/toolkit'

const establishmentSlice = createSlice({
  name: 'establishment',
  initialState: {
    data: null,
    error: null
  },
  reducers: {
    setEstablishment(state, action) {
      state.data = action.payload;
      state.error = null;
    },
    setEstablishmentError(state, action) {
      state.data = null;
      state.error = action.payload;
    },
    leaveStablishment(state, action) {
      state.data = null;
      state.error = null;
    }
  }
})

export const { setEstablishment, setEstablishmentError, leaveStablishment } = establishmentSlice.actions
export default establishmentSlice.reducer