import Establishment from './pages/establishment/Establishment';
import EstablishmentLoader from './pages/EstablishmentLoader';
import UserHeader from './pages/UserHeader';
import { useSelector } from 'react-redux';
import { withAuthenticator } from '@aws-amplify/ui-react';
import '@aws-amplify/ui-react/styles.css';
import styles from './App.module.css';

function App() {
  const establishmentState = useSelector(state => state.establishment);
  
  const renderSwitch = () => {
    if (establishmentState.data)
      return <Establishment/>;
    if (establishmentState.error)
      return JSON.stringify(establishmentState); // TODO
    return <EstablishmentLoader/>;
  }

  return (
    <div className={styles.App}>
      <UserHeader/>
      {renderSwitch()}
    </div>);
}

export default withAuthenticator(App);
