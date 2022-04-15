import JwtDecode from 'jwt-decode';

class TokenService {
  withGoogle(response) {
    localStorage.setItem('Authorization', `Bearer ${response.accessToken}`);
    this.login()
  }

  login(provider, token, email, name, data) {
    
  }

  getAccessToken() {
    let token = localStorage.getItem('Authorization');
    if (token) {
      token = token.split(' ');
      if (token.length > 1) {
        return token[1];
      }
    }
    return null;
  }

  getDecodedToken() {
    const token = this.getAccessToken();
    if (token) {
      return JwtDecode(token);
    }
    return null;
  }

  expired() {
    return !this.valid();
  }

  valid() {
    const decoded = this.getDecodedToken();
    if (decoded && decoded.exp) {
      return new Date(decoded.exp) < new Date();
    }
    return false;
  }
};

export default TokenService;