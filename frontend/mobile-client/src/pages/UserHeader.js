import { useLocalStorage } from 'react-use'
import { auth } from 'tavern-commons'
import styles from './UserHeader.module.css'

function UserHeader() {
  const [ sessionInfo ] = useLocalStorage('sessionInfo')
  
  return (
    <div className={styles.UserHeader}>
      <span>{sessionInfo.userInfo.displayName}</span>
      <div className={styles.user} onClick={() => auth.signOut()}>
      </div>
    </div>
  )
}

export default UserHeader
