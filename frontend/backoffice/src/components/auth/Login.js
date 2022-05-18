import styles from "./Login.module.css";
import { useLocalStorage } from "react-use";
import api from "../../services/api";
import auth from "./Auth";
import { useState } from "react";

function Login() {
  const [ username, setUsername ] = useState(null)
  const [ password, setPassword ] = useState(null)

  function login() {
    auth.authenticate(username, password);
  }

  return (
    <div class={styles.Login}>
      <div class={styles.loginForm}>
        <div className="form-input">
            <label>e-mail</label>
            <input type="email" value={username} onChange={e => setUsername(e.target.value)}/>
        </div>
        <div className="form-input">
            <label>Password</label>
            <input type="password" value={password} onChange={e => setPassword(e.target.value)}/>
        </div>
        <div className="form-input">
          <button disabled={!username || !password} onClick={() => login()}>Log in</button>
        </div>
      </div>
    </div>
  )
}

export default Login;