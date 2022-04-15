import styles from './Locate.module.css'
import QrReader from 'react-qr-reader'
import { useNavigate } from 'react-router-dom';

function Locate() {
  const navigate = useNavigate();

  const handleScan = data => {
    if (data) {
      const url = new URL(data);
      console.log(url);
      navigate('/load', {
        establishment: url.searchParams.get('establishment'),
        location: url.searchParams.get('location')
      })
    }
  }
  const handleError = err => {
    console.error(err)
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
        <button onClick={() => handleScan('https://waiter.hphil.com/load?establishment=xxxx&location=yyyy')}>backdoor</button>
    </div>
  );
}

export default Locate;
