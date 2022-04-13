import { createSlice } from '@reduxjs/toolkit';

export const sessionSlice = createSlice({
  name: 'session',
  initialState: {
    uid: null,
    location: null
  },
  reducers: {
    setLocation: (state, action) => {
      state.uid = action.payload.uid
      state.location = action.payload.location;
    },
  },
});

export const { setLocation } = sessionSlice.actions;

export const selectLocation = state => state.session.location;

export default sessionSlice.reducer;
