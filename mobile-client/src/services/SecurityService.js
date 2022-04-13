import Keycloak from 'keycloak-js';
// import { Endpoints, Request } from '../request/Requests';

const keycloak = new Keycloak('/keycloak.json');

const initKeycloak = (onSuccessCallback, onFailureCallback) => {
    let success = false;

    setTimeout(() => {
      if(!success){
          onFailureCallback();
      }
    }, 3000);
    keycloak.init({
        // onLoad: 'check-sso',
        // silentCheckSsoRedirectUri: window.location.origin + '/silent-check-sso.html',
        // pkceMethod: 'S256',
        onLoad: 'login-required'
    }).then(isAuthenticated => {
        success = true;
        if(isAuthenticated) {
            onSuccessCallback();
        } else {
            login({
                redirectUri: window.location.href
            });
        }
    });
}

const login = keycloak.login;

const logout = keycloak.logout;

const register = keycloak.register;

const getToken = () => keycloak.token;

const updateToken = (successCallback) => {
    return keycloak.updateToken(7200)
        .then(successCallback())
        .catch(login({
            redirectUri: "/keycloak"
        }));
}

const getUsername = () => keycloak.idTokenParsed ? keycloak.idTokenParsed.preferred_username : '';

const getRole = () => keycloak.tokenParsed ? keycloak.tokenParsed.realm_access.roles : '';

// const getUserData = () => {
//     const username = getUsername();
//     Request.get(username, Endpoints.Users.getByUsername);
// }

const service = {
  initKeycloak,
  login,
  register,
  logout,
  getToken,
  updateToken,
  getUsername,
  keycloak,
  // getUserData,
  getRole,
};

export default service;