import { useSelector } from 'react-redux';
import styles from './UserHeader.module.css';
import { Flex } from "@chakra-ui/react"
import SecurityService from '../../services/SecurityService';
import { useEffect, useState } from 'react';

function UserHeader() {
  const [user, setUser] = useState(null)

  useEffect(() => {
    SecurityService.getUserInfo()
      .then(u => {
        setUser(u)
      });
  }, []);
  
  const logout = () => {
    SecurityService.logout();
  }

  return (
    <Flex bg="#333" w="100%" h="60px" align="center" justify="space-between" p="8px" color="#f0f0f0" shrink="0">
      <span>{user?.given_name}</span>
      <div className={styles.user} onClick={logout}>
      </div>
    </Flex>
  );
}

export default UserHeader;
