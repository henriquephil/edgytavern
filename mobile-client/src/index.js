import React from 'react';
import ReactDOM from 'react-dom';
import './index.css';
import App from './App';
import { store } from './state';
import { Provider } from 'react-redux';
import * as serviceWorker from './serviceWorker';
import SecurityService from './services/SecurityService'

const render = () => {
  ReactDOM.render(
    <React.StrictMode>
      <Provider store={store}>
        <App />
      </Provider>
    </React.StrictMode>,
    document.getElementById('root')
  );

  // Request.configureAxiosDefault();
};

const renderError = () => ReactDOM.render(
  <React.StrictMode>
    <div><h1 className="TextCenter">Error communicating with Keycloak OAuth2 client</h1></div>
  </React.StrictMode>,
  document.getElementById('root')
);

SecurityService.initKeycloak(render, renderError);

// If you want your app to work offline and load faster, you can change
// unregister() to register() below. Note this comes with some pitfalls.
// Learn more about service workers: https://bit.ly/CRA-PWA
serviceWorker.unregister();
