import { useEffect, useState } from 'react';
import Keycloak from 'keycloak-js';
import { useNavigate } from 'react-router-dom';
import axiosInstanceBuilder from '../api/AxiosInstanceBuilder';
// import AxiosContext from '../api/AxiosContext';
import { setRequestInterceptor } from '../api';

function KeycloakSecured({ children }) {
  const [keycloak, setKeycloak] = useState(null);
  const [authenticated, setAuthenticated] = useState(false);
  const navigate = useNavigate();

  useEffect(() => {
    const keycloak = Keycloak('/keycloak.json');
    
    keycloak.init({onLoad: 'login-required'})
    // keycloak.init({ onLoad: 'check-sso', silentCheckSsoRedirectUri: window.location.origin + '/silent-check-sso.html' })
    // keycloak.init({onLoad: 'login-required', flow: 'implicit'})
        .then(authenticated => {
          setKeycloak(keycloak);
          setAuthenticated(authenticated);
        }).catch(() => navigate('/'))
  }, []);
  
  const reqFulfilled = (config) => {
    if (keycloak)
      return keycloak.updateToken(60 * 5)
        .then(refreshed => {
          config.headers.Authorization = `Bearer ${keycloak.token}`;
          return Promise.resolve(config);
        })
  }

  const axiosInstance = axiosInstanceBuilder({reqFulfilled})
  setRequestInterceptor(reqFulfilled)

  if (keycloak) {
    if (authenticated)
      return <AxiosContext.Provider value={axiosInstance}>{children}</AxiosContext.Provider>;
    else 
      return <div>Unable to authenticate</div>;
  }
  return <div>Initializing</div>;
}
export default KeycloakSecured;