import "./login.css"
import auth from "./auth"
import React, { useState } from "react"
import GoogleLogin from 'react-google-login'

function Login() {
  const [ username, setUsername ] = useState(null)
  const [ password, setPassword ] = useState(null)

  function login() {
    auth.authenticate(username, password)
      .then(res => window.location = window.location)
  }
  function googleLogin(googleResponse) {
    auth.authenticateGoogle(googleResponse)
      .then(res => window.location = window.location)
  }

  return (
    <div className="login-page">
      <div className="login-form">
        <div className="form-input">
            <label>e-mail</label>
            <input type="email" value={username} onChange={e => setUsername(e.target.value)}/>
        </div>
        <div className="form-input">
            <label>Password</label>
            <input type="password" value={password} onChange={e => setPassword(e.target.value)}/>
        </div>
        <div className="form-input">
          <button disabled={!username || !password} onClick={() => login()}>Login</button>
        </div>
        <GoogleLogin
          clientId="595477155318-e8j9fmbikkb6ihdt2iomjhtptpuq3giu.apps.googleusercontent.com"
          buttonText="Login"
          onSuccess={googleLogin}
          onFailure={res => console.log(res)} // TODO improve
          cookiePolicy={'single_host_origin'}
          buttonText="Login with Google"
        />
      </div>
    </div>
  )
}

export default Login