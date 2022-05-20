function _interopDefault (ex) { return (ex && (typeof ex === 'object') && 'default' in ex) ? ex['default'] : ex; }

var axios = _interopDefault(require('axios'));
var moment = _interopDefault(require('moment'));
var React = require('react');
var React__default = _interopDefault(React);
var GoogleLogin = _interopDefault(require('react-google-login'));

var SESSION_INFO = 'sessionInfo';

var Auth = /*#__PURE__*/function () {
  function Auth() {}

  var _proto = Auth.prototype;

  _proto.authenticate = function authenticate(username, password) {
    try {
      var _this2 = this;

      var sessionInfo = _this2._getStorageSessionInfo();

      if (sessionInfo) throw 'already authenticated';
      return Promise.resolve(_this2._authenticate(username, password)).then(function () {});
    } catch (e) {
      return Promise.reject(e);
    }
  };

  _proto.authenticateGoogle = function authenticateGoogle(googleResponse) {
    try {
      var _this4 = this;

      var sessionInfo = _this4._getStorageSessionInfo();

      if (sessionInfo) throw 'already authenticated';
      return Promise.resolve(_this4._authenticateGoogle(googleResponse.accessToken)).then(function () {
        return googleResponse;
      });
    } catch (e) {
      return Promise.reject(e);
    }
  };

  _proto.refreshAndGetSession = function refreshAndGetSession() {
    try {
      var _this6 = this;

      var sessionInfo = _this6._getStorageSessionInfo();

      if (!sessionInfo) throw 'not authenticated';

      if (moment().isBefore(sessionInfo.expiresAt)) {
        return Promise.resolve(sessionInfo);
      }

      return Promise.resolve(_this6._refreshToken(sessionInfo));
    } catch (e) {
      return Promise.reject(e);
    }
  };

  _proto.signOut = function signOut() {
    try {
      var _this8 = this;

      var sessionInfo = _this8._getStorageSessionInfo();

      if (!sessionInfo) throw 'not authenticated';
      axios.post("/api/security/auth/logout", null, {
        headers: {
          'Authorization': sessionInfo.mountedToken
        }
      });
      localStorage.removeItem(SESSION_INFO);
      window.location = '/';
      return Promise.resolve();
    } catch (e) {
      return Promise.reject(e);
    }
  };

  _proto._authenticate = function _authenticate(username, password) {
    try {
      var _this10 = this;

      var authRequest = new URLSearchParams();
      authRequest.append('client_id', 'client');
      authRequest.append('client_secret', 'secret');
      authRequest.append('grant_type', 'password');
      authRequest.append('username', username);
      authRequest.append('password', password);
      return Promise.resolve(axios.post("/api/security/auth/token", authRequest)).then(function (_axios$post) {
        var tokenInfo = _axios$post.data;
        return Promise.resolve(_this10._persistTokenInfo(tokenInfo)).then(function () {});
      });
    } catch (e) {
      return Promise.reject(e);
    }
  };

  _proto._authenticateGoogle = function _authenticateGoogle(accessToken) {
    try {
      var _this12 = this;

      return Promise.resolve(axios.post("/api/security/auth/google", {
        clientId: 'client',
        accessToken: accessToken
      })).then(function (_axios$post2) {
        var tokenInfo = _axios$post2.data;
        return Promise.resolve(_this12._persistTokenInfo(tokenInfo)).then(function () {});
      });
    } catch (e) {
      return Promise.reject(e);
    }
  };

  _proto._refreshToken = function _refreshToken(sessionInfo) {
    try {
      var _this14 = this;

      var refreshRequest = new URLSearchParams();
      refreshRequest.append('client_id', 'client');
      refreshRequest.append('client_secret', 'secret');
      refreshRequest.append('grant_type', 'refresh_token');
      refreshRequest.append('refresh_token', sessionInfo.tokenInfo.refresh_token);
      return Promise.resolve(axios.post("/api/security/auth/token", refreshRequest)).then(function (_axios$post3) {
        var tokenInfo = _axios$post3.data;
        return Promise.resolve(_this14._persistTokenInfo(tokenInfo));
      });
    } catch (e) {
      return Promise.reject(e);
    }
  };

  _proto._persistTokenInfo = function _persistTokenInfo(tokenInfo) {
    try {
      var _temp2 = function _temp2(_axios$get) {
        var sessionInfo = _this15$_getStorageSe2 || {
          userInfo: _axios$get.data
        };
        sessionInfo.tokenInfo = tokenInfo;
        sessionInfo.mountedToken = authAccessToken;
        sessionInfo.expiresAt = moment().add(tokenInfo.expires_in, 's');
        localStorage.setItem(SESSION_INFO, JSON.stringify(sessionInfo));
        return sessionInfo;
      };

      var _this16 = this;

      var authAccessToken = tokenInfo.token_type + " " + tokenInfo.access_token;

      var _this15$_getStorageSe2 = _this16._getStorageSessionInfo();

      return Promise.resolve(_this15$_getStorageSe2 ? _temp2(_this15$_getStorageSe2) : Promise.resolve(axios.get("/api/security/auth/userInfo", {
        headers: {
          'Authorization': authAccessToken
        }
      })).then(_temp2));
    } catch (e) {
      return Promise.reject(e);
    }
  };

  _proto._getStorageSessionInfo = function _getStorageSessionInfo() {
    var storageSessionInfo = localStorage.getItem(SESSION_INFO);
    return JSON.parse(storageSessionInfo);
  };

  return Auth;
}();

var auth = new Auth();

function Login() {
  var _React$createElement;

  var _useState = React.useState(null),
      username = _useState[0],
      setUsername = _useState[1];

  var _useState2 = React.useState(null),
      password = _useState2[0],
      setPassword = _useState2[1];

  function login() {
    auth.authenticate(username, password).then(function (res) {
      return window.location = window.location;
    });
  }

  function googleLogin(googleResponse) {
    auth.authenticateGoogle(googleResponse).then(function (res) {
      return window.location = window.location;
    });
  }

  return /*#__PURE__*/React__default.createElement("div", {
    className: "login-page"
  }, /*#__PURE__*/React__default.createElement("div", {
    className: "login-form"
  }, /*#__PURE__*/React__default.createElement("div", {
    className: "form-input"
  }, /*#__PURE__*/React__default.createElement("label", null, "e-mail"), /*#__PURE__*/React__default.createElement("input", {
    type: "email",
    value: username,
    onChange: function onChange(e) {
      return setUsername(e.target.value);
    }
  })), /*#__PURE__*/React__default.createElement("div", {
    className: "form-input"
  }, /*#__PURE__*/React__default.createElement("label", null, "Password"), /*#__PURE__*/React__default.createElement("input", {
    type: "password",
    value: password,
    onChange: function onChange(e) {
      return setPassword(e.target.value);
    }
  })), /*#__PURE__*/React__default.createElement("div", {
    className: "form-input"
  }, /*#__PURE__*/React__default.createElement("button", {
    disabled: !username || !password,
    onClick: function onClick() {
      return login();
    }
  }, "Login")), /*#__PURE__*/React__default.createElement(GoogleLogin, (_React$createElement = {
    clientId: "595477155318-e8j9fmbikkb6ihdt2iomjhtptpuq3giu.apps.googleusercontent.com",
    buttonText: "Login",
    onSuccess: googleLogin,
    onFailure: function onFailure(res) {
      return console.log(res);
    },
    cookiePolicy: 'single_host_origin'
  }, _React$createElement["buttonText"] = "Login with Google", _React$createElement))));
}

exports.Login = Login;
exports.auth = auth;
//# sourceMappingURL=index.js.map
