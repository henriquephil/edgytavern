import { selectLocation } from '../../state/sessionSlice';
import { useSelector } from 'react-redux';
import styles from './UserHeader.module.css';
import { Flex } from "@chakra-ui/react"
import SecurityService from '../../services/SecurityService';

function UserHeader() {
  const location = useSelector(selectLocation);
  
  const logout = () => {
    SecurityService.logout();
  }

  return (
    <Flex bg="#333" w="100%" h="60px" align="center" justify="space-between" p="8px" color="#f0f0f0" shrink="0">
      <span>{location?.establishment?.name}</span>
      <div className={styles.user} onClick={logout}>
      </div>
    </Flex>
  );
}

export default UserHeader;
