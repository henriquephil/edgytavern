import { createSlice } from '@reduxjs/toolkit'

const registerSlice = createSlice({
  name: 'register',
  initialState: {
    loading: false,
    data: null,
    error: null
  },
  reducers: {
    setRegisterLoading(state, action) {
      state.loading = true
      state.data = null
      state.error = null
    },
    setRegister(state, action) {
      state.loading = false
      state.data = action.payload
      state.error = null
    },
    setRegisterError(state, action) {
      state.loading = false
      state.data = null
      state.error = action.payload
    }
  }
})

export const { setRegisterLoading, setRegister, setRegisterError } = registerSlice.actions
export default registerSlice.reducer