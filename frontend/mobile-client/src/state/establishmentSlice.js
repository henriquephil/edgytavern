import { createSlice } from '@reduxjs/toolkit'

const establishmentSlice = createSlice({
  name: 'establishment',
  initialState: {
    loading: false,
    data: null,
    error: null
  },
  reducers: {
    setEstablishmentLoading(state, action) {
      state.loading = true;
      state.data = null;
      state.error = null;
    },
    setEstablishment(state, action) {
      state.loading = false;
      state.data = action.payload;
      state.error = null;
    },
    setEstablishmentError(state, action) {
      state.loading = false;
      state.data = null;
      state.error = action.payload;
    },
    leaveEstablishment(state, action) {
      state.loading = false;
      state.data = null;
      state.error = null;
    }
  }
})

export const { setEstablishmentLoading, setEstablishment, setEstablishmentError, leaveEstablishment } = establishmentSlice.actions
export default establishmentSlice.reducer