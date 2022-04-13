import { combineReducers, configureStore } from '@reduxjs/toolkit';
import { persistStore, persistReducer } from 'redux-persist';
import storage from 'redux-persist/lib/storage';
import sessionSlice from './sessionSlice';

const persistConfig = {
  key: 'root',
  storage,
};

const reducers = combineReducers({ session: sessionSlice });

const persistedReducer = persistReducer(persistConfig, reducers);

const store = configureStore({
  reducer: persistedReducer
});

let persistor = persistStore(store);

export { store, persistor }
