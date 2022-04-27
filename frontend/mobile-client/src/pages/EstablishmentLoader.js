import { useDispatch } from 'react-redux';
import QrReader from 'react-qr-reader'
import { fetchEstablishment } from '../state/ApiEstablishmentActions';
import styles from './EstablishmentLoader.module.css';

function EstablishmentLoader() {
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

  return (
    <div className={styles.EstablishmentLoader}>
      <QrReader
          delay={300}
          onError={handleError}
          onScan={handleScan}
          style={{ width: '100%' }}
        />
      <p>Where are you at?</p>
      <p>Scan your location's QR code to access the menu</p>
      <button onClick={() => handleScan('https://asdasdsa.hphil.com/load?e=r4J0ay&s=ZLbXoN')}>test</button>
      <p><strong>// TODO improve this page layout</strong></p>
      <p><strong>// TODO what to show/control if users doesn't allow camera?</strong></p>
    </div>
  );
}

export default EstablishmentLoader;
