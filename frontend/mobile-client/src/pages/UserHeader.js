import styles from './UserHeader.module.css';
import { Auth } from 'aws-amplify';

function UserHeader() {
  
  const logout = () => {
    Auth.signOut();
  }

  return (
    <div className={styles.UserHeader}>
      <span>{Auth.user.attributes.name}</span>
      <div className={styles.user} onClick={logout}>
      </div>
    </div>
  );
}

export default UserHeader;
