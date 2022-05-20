import React from 'react'
import './index.css'
import App from './App'
import { createRoot } from 'react-dom/client'
import store from './state/store'
import { Provider } from 'react-redux'
import * as serviceWorker from './serviceWorker'

createRoot(document.getElementById('root')).render(
  <React.StrictMode>
    <Provider store={store}>
      <App />
    </Provider>
  </React.StrictMode>
)

serviceWorker.unregister()
