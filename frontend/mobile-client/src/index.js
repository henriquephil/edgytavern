import React from 'react';
import ReactDOM from 'react-dom';
import './index.css';
import App from './App';
import { createRoot } from 'react-dom/client';
import store from './state/store';
import { Provider } from 'react-redux';
import * as serviceWorker from './serviceWorker';
import SecurityService from './services/SecurityService';
import { BrowserRouter } from 'react-router-dom';

SecurityService.initKeycloak().then(() => {
  createRoot(document.getElementById('root')).render(
    <React.StrictMode>
      <Provider store={store}>
        <BrowserRouter>
          <App />
        </BrowserRouter>
      </Provider>
    </React.StrictMode>
  );
})
.catch(err => {
  createRoot(document.getElementById('root')).render(
    <React.StrictMode>
      <div><h1 className="TextCenter">Error communicating with Keycloak OAuth2 client</h1></div>
    </React.StrictMode>
  );
});

serviceWorker.unregister();
