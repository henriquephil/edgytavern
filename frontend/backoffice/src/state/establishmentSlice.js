import { createSlice } from '@reduxjs/toolkit'

const establishmentSlice = createSlice({
  name: 'establishment',
  initialState: {
    loading: false,
    data: null,
    error: null,
    dispatched: false
  },
  reducers: {
    setEstablishmentLoading(state, action) {
      state.loading = true
      state.data = null
      state.error = null
      state.dispatched = true
    },
    setEstablishment(state, action) {
      state.loading = false
      state.data = action.payload
      state.error = null
      state.dispatched = !!action.payload
    },
    setEstablishmentError(state, action) {
      state.loading = false
      state.data = null
      state.error = action.payload
    }
  }
})

export const { setEstablishmentLoading, setEstablishment, setEstablishmentError } = establishmentSlice.actions
export default establishmentSlice.reducer