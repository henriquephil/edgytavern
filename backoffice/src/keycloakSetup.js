import Keycloak from 'keycloak-js';
import { setRequestInterceptor } from './api';

export async function initKeycloak() {
  const keycloak = Keycloak('/keycloak.json');
  if (!keycloak) {
    console.error('keycloak file not found')
    return;
  }
  
  const authenticated = await keycloak.init({ onLoad: 'login-required' })
  // keycloak.init({ onLoad: 'check-sso', silentCheckSsoRedirectUri: window.location.origin + '/silent-check-sso.html' })
  // keycloak.init({onLoad: 'login-required', flow: 'implicit'})
  if (authenticated) {       
    const userInfo = await keycloak.loadUserInfo();
  } else {
    // redirect?
  }
  // .catch(() => console.log("navigate('/')"));
  
  const authTokenHeaderInterceptor = (config) => {
    if (keycloak) {
      keycloak.updateToken(60 * 5);
        // .then(refreshed => {
        //   return Promise.resolve(config);
        // })
    }
    config.headers.Authorization = `Bearer ${keycloak.token}`;
    return config;
  }
  setRequestInterceptor(authTokenHeaderInterceptor)

  return Promise.resolve();
}
