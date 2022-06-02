
import UserHeader from './pages/UserHeader'
import { useLocalStorage } from 'react-use'
import styles from './App.module.css'
import EstablishmentRedirector from './pages/EstablishmentRedirector'
import { Login } from 'tavern-commons'

function App() {
  const [ session ] = useLocalStorage('sessionInfo')

  if (!session) return <Login/>// TODO extract
  return (
    <div className={styles.App}>
      <UserHeader/>
      <EstablishmentRedirector/>
    </div>)
}

export default App
