import UserHeader from './secure/UserHeader';
import { Flex } from "@chakra-ui/react"
import { useSelector } from 'react-redux';
import Establishment from './secure/Establishment';
import EstablishmentLoader from './secure/EstablishmentLoader';

function Authenticated() {
  // const billState = useSelector(state => state.bill);
  const establishmentState = useSelector(state => state.establishment);
  

  const renderSwitch = () => {
    // if (billState.loading)
    //   return 'Loading';
    // if (billState.error)
    //   return billState.error;
    
    if (establishmentState.data)
      return <Establishment/>;
    if (establishmentState.error)
      return establishmentState.error;

    return <EstablishmentLoader/>;
  }

  return (
    <Flex direction="column" w="100%">
      <UserHeader/>
      {renderSwitch()}
    </Flex>);
}

export default Authenticated;
