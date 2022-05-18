import axios from "axios";
import moment from "moment";

const SESSION_INFO = 'sessionInfo';

class Auth {
  async authenticate(username, password) {
    const sessionInfo = this._getStorageSessionInfo();
    if (sessionInfo) {
      throw 'already authenticated'
    }
    this._authenticate(username, password)
  }

  async refreshAndGetSession() {
    const sessionInfo = this._getStorageSessionInfo();
    if (!sessionInfo) {
      throw 'not authenticated'
    }
    if (moment().isBefore(sessionInfo.expiresAt)) {
      return sessionInfo
    }
    return await this._refreshToken(sessionInfo)
  }

  async signOut() {
    await axios.post(`/api/security/auth/logout`, null, this._buildHeader())
  }

  // privates

  async _authenticate(username, password) {
    const authRequest = new URLSearchParams();
    authRequest.append('client_id', 'client');
    authRequest.append('client_secret', 'secret');
    authRequest.append('grant_type', 'password');
    authRequest.append('username', username);
    authRequest.append('password', password);
    const tokenInfo = (await axios.post(`/api/security/auth/token`, authRequest)).data
    const userInfo = (await axios.get(`/api/security/auth/userInfo`, { headers: { 'Authorization': `${tokenInfo.token_type} ${tokenInfo.access_token}` } })).data
    this._persistSessionInfo(tokenInfo, userInfo)
  }

  async _refreshToken(sessionInfo) {
    const refreshRequest = new URLSearchParams();
    refreshRequest.append('client_id', 'client');
    refreshRequest.append('client_secret', 'secret');
    refreshRequest.append('grant_type', 'refresh_token');
    refreshRequest.append('refresh_token', sessionInfo.tokenInfo.refresh_token);
    const tokenInfo = (await axios.post(`/api/security/auth/token`, refreshRequest)).data
    return this._persistSessionInfo(tokenInfo)
  }

  _persistSessionInfo(tokenInfo, userInfo) {
    let sessionInfo
    if (!userInfo) {
      sessionInfo = this._getStorageSessionInfo()
    } else {
      sessionInfo = {
        userInfo,
      }
    }
    sessionInfo.tokenInfo = tokenInfo
    sessionInfo.mountedToken = `${tokenInfo.token_type} ${tokenInfo.access_token}`
    sessionInfo.expiresAt = moment().add(tokenInfo.expires_in, 'm')
    console.log('setting SESSION_INFO')
    localStorage.setItem(SESSION_INFO, JSON.stringify(sessionInfo))
  }

  _buildHeader(sessionInfo) {
    sessionInfo = sessionInfo || localStorage.getItem(SESSION_INFO)
    return { headers: { 'Authorization': sessionInfo.mountedToken } }
  }

  _getStorageSessionInfo() {
    const storageSessionInfo = localStorage.getItem(SESSION_INFO)
    return JSON.parse(storageSessionInfo)
  }
}

const auth = new Auth();
export default auth;