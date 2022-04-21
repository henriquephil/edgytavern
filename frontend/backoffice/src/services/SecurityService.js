import Keycloak from 'keycloak-js';

const keycloak = new Keycloak('/keycloak.json');

const login = keycloak.login;

const logout = keycloak.logout;

const register = keycloak.register;

const getToken = () => keycloak.token;

const updateToken = () => {
    return keycloak.updateToken(7200);
        // .catch(login({
        //     redirectUri: "/keycloak"
        // }));
}

const getUserInfo = keycloak.loadUserInfo;

const getUsername = () => keycloak.idTokenParsed ? keycloak.idTokenParsed.preferred_username : '';

const getRole = () => keycloak.tokenParsed ? keycloak.tokenParsed.realm_access.roles : '';

// const getUserData = () => {
//     const username = getUsername();
//     Request.get(username, Endpoints.Users.getByUsername);
// }

export async function initKeycloak() {
    let success = false;
    setTimeout(() => {
      if (!success){
        throw "keycloak timeout"
      }
    }, 3000);

    const isAuthenticated = await keycloak.init({
        // onLoad: 'check-sso',
        // silentCheckSsoRedirectUri: window.location.origin + '/silent-check-sso.html',
        // pkceMethod: 'S256',
        onLoad: 'login-required'
    })
    success = true;
    
    if(!isAuthenticated) {
        // login({
        //     redirectUri: window.location.href
        // });
        throw "not authenticated"
    }
}

const SecurityService = {
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
  getUserInfo
};

export default SecurityService;