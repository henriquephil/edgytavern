import { createSlice } from '@reduxjs/toolkit'

// TODO typescript

// interface Asset {
//   hashId: string,
//   name: string,
//   price: number,
//   description?: string
// }

// interface Category {
//   hashId: string,
//   name: string,
//   assets: Array<Asset>
// }

// interface Action {
//   payload: Array<Category>,
//   type: string
// }

const assetsSlice = createSlice({
  name: 'assets',
  initialState: {
    loading: false,
    data: null,
    error: null
  },
  reducers: {
    setAssets(state, action) {
      state.loading = false
      state.data = action.payload
      state.error = null
    },
    setAssetsError(state, action) {
      state.loading = false
      state.data = null
      state.error = action.payload
    },
    assetsLoading(state, action) {
      state.loading = true
      state.data = null
      state.error = null
    }
  }
})

export const { setAssets, setAssetsError, assetsLoading } = assetsSlice.actions
export default assetsSlice.reducer