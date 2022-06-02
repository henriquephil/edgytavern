import axios from 'axios';
import moment from 'moment';
import React, { useState } from 'react';
import GoogleLogin from 'react-google-login';

const SESSION_INFO = 'sessionInfo';

class Auth {
  constructor() {
    this.refreshing = false;
  }

  async authenticate(username, password) {
    const sessionInfo = this._getStorageSessionInfo();

    if (sessionInfo) throw 'already authenticated';
    await this._authenticate(username, password);
  }

  async authenticateGoogle(googleResponse) {
    const sessionInfo = this._getStorageSessionInfo();

    if (sessionInfo) throw 'already authenticated';
    await this._authenticateGoogle(googleResponse.accessToken);
    return googleResponse;
  }

  async refreshAndGetSession() {
    while (this.refreshing) {
      await new Promise(res => setTimeout(res, 1000));
    }

    const sessionInfo = this._getStorageSessionInfo();

    if (!sessionInfo) throw 'not authenticated';

    if (moment().isBefore(sessionInfo.expiresAt)) {
      return sessionInfo;
    }

    return await this._refreshToken(sessionInfo);
  }

  async signOut() {
    const sessionInfo = this._getStorageSessionInfo();

    if (!sessionInfo) throw 'not authenticated';
    axios.post(`/api/security/auth/logout`, null, {
      headers: {
        'Authorization': sessionInfo.mountedToken
      }
    });
    localStorage.removeItem(SESSION_INFO);
    window.location = window.location;
  }

  async _authenticate(username, password) {
    const authRequest = new URLSearchParams();
    authRequest.append('client_id', 'client');
    authRequest.append('client_secret', 'secret');
    authRequest.append('grant_type', 'password');
    authRequest.append('username', username);
    authRequest.append('password', password);
    const tokenInfo = (await axios.post(`/api/security/auth/token`, authRequest)).data;
    await this._persistTokenInfo(tokenInfo);
  }

  async _authenticateGoogle(accessToken) {
    const tokenInfo = (await axios.post("/api/security/auth/google", {
      clientId: 'client',
      accessToken
    })).data;
    await this._persistTokenInfo(tokenInfo);
  }

  async _refreshToken(sessionInfo) {
    this.refreshing = true;
    const refreshRequest = new URLSearchParams();
    refreshRequest.append('client_id', 'client');
    refreshRequest.append('client_secret', 'secret');
    refreshRequest.append('grant_type', 'refresh_token');
    refreshRequest.append('refresh_token', sessionInfo.tokenInfo.refresh_token);
    const tokenInfo = (await axios.post(`/api/security/auth/token`, refreshRequest)).data;
    const newTokenInfo = await this._persistTokenInfo(tokenInfo);
    this.refreshing = false;
    return newTokenInfo;
  }

  async _persistTokenInfo(tokenInfo) {
    const authAccessToken = `${tokenInfo.token_type} ${tokenInfo.access_token}`;
    const sessionInfo = this._getStorageSessionInfo() || {
      userInfo: (await axios.get(`/api/security/auth/userInfo`, {
        headers: {
          'Authorization': authAccessToken
        }
      })).data
    };
    sessionInfo.tokenInfo = tokenInfo;
    sessionInfo.mountedToken = authAccessToken;
    sessionInfo.expiresAt = moment().add(tokenInfo.expires_in, 's');
    localStorage.setItem(SESSION_INFO, JSON.stringify(sessionInfo));
    return sessionInfo;
  }

  _getStorageSessionInfo() {
    const storageSessionInfo = localStorage.getItem(SESSION_INFO);
    return JSON.parse(storageSessionInfo);
  }

}

var auth = new Auth();

var style = {"loginPage":"_login-module__loginPage__3YO6v","loginForm":"_login-module__loginForm__1p5Tm"};

var formStyle = {"field":"_form-module__field__13sAl"};

function Login() {
  const [username, setUsername] = useState(null);
  const [password, setPassword] = useState(null);

  function login() {
    auth.authenticate(username, password).then(res => window.location = window.location);
  }

  function googleLogin(googleResponse) {
    auth.authenticateGoogle(googleResponse).then(res => window.location = window.location);
  }

  return /*#__PURE__*/React.createElement("div", {
    className: style.loginPage
  }, /*#__PURE__*/React.createElement("div", {
    className: style.loginForm
  }, /*#__PURE__*/React.createElement("div", {
    className: formStyle.field
  }, /*#__PURE__*/React.createElement("label", null, "e-mail"), /*#__PURE__*/React.createElement("input", {
    type: "email",
    value: username,
    onChange: e => setUsername(e.target.value)
  })), /*#__PURE__*/React.createElement("div", {
    className: formStyle.field
  }, /*#__PURE__*/React.createElement("label", null, "Password"), /*#__PURE__*/React.createElement("input", {
    type: "password",
    value: password,
    onChange: e => setPassword(e.target.value)
  })), /*#__PURE__*/React.createElement("div", {
    className: formStyle.field
  }, /*#__PURE__*/React.createElement("button", {
    disabled: !username || !password,
    onClick: () => login()
  }, "Login")), /*#__PURE__*/React.createElement(GoogleLogin, {
    clientId: "595477155318-e8j9fmbikkb6ihdt2iomjhtptpuq3giu.apps.googleusercontent.com",
    buttonText: "Login",
    onSuccess: googleLogin,
    onFailure: res => console.log(res),
    cookiePolicy: 'single_host_origin',
    buttonText: "Login with Google"
  })));
}

export { Login, auth };
//# sourceMappingURL=index.modern.js.map
