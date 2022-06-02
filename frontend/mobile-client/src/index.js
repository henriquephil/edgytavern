import React from 'react'
import './index.css'
import App from './App'
import { createRoot } from 'react-dom/client'
import { PersistGate } from 'redux-persist/integration/react'
import { persistStore } from 'redux-persist'
import store from './state/store'
import { Provider } from 'react-redux'
import * as serviceWorker from './serviceWorker'
import 'tavern-commons/dist/index.css'

let persistor = persistStore(store)

createRoot(document.getElementById('root')).render(
  <React.StrictMode>
    <Provider store={store}>
      <PersistGate loading={null} persistor={persistor}>
        <App />
      </PersistGate>
    </Provider>
  </React.StrictMode>
)

serviceWorker.unregister()
