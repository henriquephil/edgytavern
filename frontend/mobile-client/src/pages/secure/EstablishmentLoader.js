import { useDispatch, useSelector } from 'react-redux';
import styles from './UserHeader.module.css';
import { Flex } from "@chakra-ui/react"
import SecurityService from '../../services/SecurityService';
import { useState } from 'react';
import QrReader from 'react-qr-reader'
import { fetchEstablishment } from '../../state/ApiEstablishmentActions';

function EstablishmentLoader() {
  const loading = useState(false);
  const dispatch = useDispatch();

  const handleScan = data => {
    if (data) {
      const url = new URL(data);
      dispatch(fetchEstablishment(url.searchParams.get('e')));
    }
  }
  const handleError = err => {
    console.error(err)
  }

  if (loading) {
    <div>
      <p>Loading</p>
    </div>
  }
  return (
    <div className={styles.Locate}>
      <p>Where are you at?</p>
      <QrReader
          delay={300}
          onError={handleError}
          onScan={handleScan}
          style={{ width: '100%' }}
        />
        <p>Scan your location's QR code to access the menu</p>
        <button onClick={() => handleScan('https://asdasdsa.hphil.com/load?e=xkXG8Z&s=yyyy')}>backdoor</button>
    </div>
  );
}

export default EstablishmentLoader;
