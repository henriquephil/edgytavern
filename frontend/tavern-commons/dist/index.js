function _interopDefault (ex) { return (ex && (typeof ex === 'object') && 'default' in ex) ? ex['default'] : ex; }

var axios = _interopDefault(require('axios'));
var moment = _interopDefault(require('moment'));
var React = require('react');
var React__default = _interopDefault(React);
var GoogleLogin = _interopDefault(require('react-google-login'));

// A type of promise-like that resolves synchronously and supports only one observer
const _Pact = /*#__PURE__*/(function() {
	function _Pact() {}
	_Pact.prototype.then = function(onFulfilled, onRejected) {
		const result = new _Pact();
		const state = this.s;
		if (state) {
			const callback = state & 1 ? onFulfilled : onRejected;
			if (callback) {
				try {
					_settle(result, 1, callback(this.v));
				} catch (e) {
					_settle(result, 2, e);
				}
				return result;
			} else {
				return this;
			}
		}
		this.o = function(_this) {
			try {
				const value = _this.v;
				if (_this.s & 1) {
					_settle(result, 1, onFulfilled ? onFulfilled(value) : value);
				} else if (onRejected) {
					_settle(result, 1, onRejected(value));
				} else {
					_settle(result, 2, value);
				}
			} catch (e) {
				_settle(result, 2, e);
			}
		};
		return result;
	};
	return _Pact;
})();

// Settles a pact synchronously
function _settle(pact, state, value) {
	if (!pact.s) {
		if (value instanceof _Pact) {
			if (value.s) {
				if (state & 1) {
					state = value.s;
				}
				value = value.v;
			} else {
				value.o = _settle.bind(null, pact, state);
				return;
			}
		}
		if (value && value.then) {
			value.then(_settle.bind(null, pact, state), _settle.bind(null, pact, 2));
			return;
		}
		pact.s = state;
		pact.v = value;
		const observer = pact.o;
		if (observer) {
			observer(pact);
		}
	}
}

function _isSettledPact(thenable) {
	return thenable instanceof _Pact && thenable.s & 1;
}

const _iteratorSymbol = /*#__PURE__*/ typeof Symbol !== "undefined" ? (Symbol.iterator || (Symbol.iterator = Symbol("Symbol.iterator"))) : "@@iterator";

const _asyncIteratorSymbol = /*#__PURE__*/ typeof Symbol !== "undefined" ? (Symbol.asyncIterator || (Symbol.asyncIterator = Symbol("Symbol.asyncIterator"))) : "@@asyncIterator";

// Asynchronously implement a generic for loop
function _for(test, update, body) {
	var stage;
	for (;;) {
		var shouldContinue = test();
		if (_isSettledPact(shouldContinue)) {
			shouldContinue = shouldContinue.v;
		}
		if (!shouldContinue) {
			return result;
		}
		if (shouldContinue.then) {
			stage = 0;
			break;
		}
		var result = body();
		if (result && result.then) {
			if (_isSettledPact(result)) {
				result = result.s;
			} else {
				stage = 1;
				break;
			}
		}
		if (update) {
			var updateValue = update();
			if (updateValue && updateValue.then && !_isSettledPact(updateValue)) {
				stage = 2;
				break;
			}
		}
	}
	var pact = new _Pact();
	var reject = _settle.bind(null, pact, 2);
	(stage === 0 ? shouldContinue.then(_resumeAfterTest) : stage === 1 ? result.then(_resumeAfterBody) : updateValue.then(_resumeAfterUpdate)).then(void 0, reject);
	return pact;
	function _resumeAfterBody(value) {
		result = value;
		do {
			if (update) {
				updateValue = update();
				if (updateValue && updateValue.then && !_isSettledPact(updateValue)) {
					updateValue.then(_resumeAfterUpdate).then(void 0, reject);
					return;
				}
			}
			shouldContinue = test();
			if (!shouldContinue || (_isSettledPact(shouldContinue) && !shouldContinue.v)) {
				_settle(pact, 1, result);
				return;
			}
			if (shouldContinue.then) {
				shouldContinue.then(_resumeAfterTest).then(void 0, reject);
				return;
			}
			result = body();
			if (_isSettledPact(result)) {
				result = result.v;
			}
		} while (!result || !result.then);
		result.then(_resumeAfterBody).then(void 0, reject);
	}
	function _resumeAfterTest(shouldContinue) {
		if (shouldContinue) {
			result = body();
			if (result && result.then) {
				result.then(_resumeAfterBody).then(void 0, reject);
			} else {
				_resumeAfterBody(result);
			}
		} else {
			_settle(pact, 1, result);
		}
	}
	function _resumeAfterUpdate() {
		if (shouldContinue = test()) {
			if (shouldContinue.then) {
				shouldContinue.then(_resumeAfterTest).then(void 0, reject);
			} else {
				_resumeAfterTest(shouldContinue);
			}
		} else {
			_settle(pact, 1, result);
		}
	}
}

var SESSION_INFO = 'sessionInfo';

var Auth = /*#__PURE__*/function () {
  function Auth() {
    this.refreshing = false;
  }

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
      var _temp3 = function _temp3() {
        var sessionInfo = _this6._getStorageSessionInfo();

        if (!sessionInfo) throw 'not authenticated';
        return moment().isBefore(sessionInfo.expiresAt) ? sessionInfo : Promise.resolve(_this6._refreshToken(sessionInfo));
      };

      var _this6 = this;

      var _temp4 = _for(function () {
        return !!_this6.refreshing;
      }, void 0, function () {
        return Promise.resolve(new Promise(function (res) {
          return setTimeout(res, 1000);
        })).then(function () {});
      });

      return Promise.resolve(_temp4 && _temp4.then ? _temp4.then(_temp3) : _temp3(_temp4));
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
      window.location = window.location;
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

      _this14.refreshing = true;
      var refreshRequest = new URLSearchParams();
      refreshRequest.append('client_id', 'client');
      refreshRequest.append('client_secret', 'secret');
      refreshRequest.append('grant_type', 'refresh_token');
      refreshRequest.append('refresh_token', sessionInfo.tokenInfo.refresh_token);
      return Promise.resolve(axios.post("/api/security/auth/token", refreshRequest)).then(function (_axios$post3) {
        var tokenInfo = _axios$post3.data;
        return Promise.resolve(_this14._persistTokenInfo(tokenInfo)).then(function (newTokenInfo) {
          _this14.refreshing = false;
          return newTokenInfo;
        });
      });
    } catch (e) {
      return Promise.reject(e);
    }
  };

  _proto._persistTokenInfo = function _persistTokenInfo(tokenInfo) {
    try {
      var _temp6 = function _temp6(_axios$get) {
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

      return Promise.resolve(_this15$_getStorageSe2 ? _temp6(_this15$_getStorageSe2) : Promise.resolve(axios.get("/api/security/auth/userInfo", {
        headers: {
          'Authorization': authAccessToken
        }
      })).then(_temp6));
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

var style = {"loginPage":"_login-module__loginPage__3YO6v","loginForm":"_login-module__loginForm__1p5Tm"};

var formStyle = {"field":"_form-module__field__13sAl"};

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
    className: style.loginPage
  }, /*#__PURE__*/React__default.createElement("div", {
    className: style.loginForm
  }, /*#__PURE__*/React__default.createElement("div", {
    className: formStyle.field
  }, /*#__PURE__*/React__default.createElement("label", null, "e-mail"), /*#__PURE__*/React__default.createElement("input", {
    type: "email",
    value: username,
    onChange: function onChange(e) {
      return setUsername(e.target.value);
    }
  })), /*#__PURE__*/React__default.createElement("div", {
    className: formStyle.field
  }, /*#__PURE__*/React__default.createElement("label", null, "Password"), /*#__PURE__*/React__default.createElement("input", {
    type: "password",
    value: password,
    onChange: function onChange(e) {
      return setPassword(e.target.value);
    }
  })), /*#__PURE__*/React__default.createElement("div", {
    className: formStyle.field
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
