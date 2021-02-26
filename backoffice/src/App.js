import { useContext, useEffect, useState } from 'react';
import CreateEstablishment from './pages/CreateEstablishment';
import Establishment from './pages/Establishment';
import AxiosContext from './api/AxiosContext';

function App() {
  const axios = useContext(AxiosContext)
  const [ establishment, setEstablishment ] = useState(null);
  const [ notFound, setNotFound ] = useState(null);
  const [ error, setError ] = useState(null);

  useEffect(() => {
    axios.get('/api/establishment/management')
      .then(res => {
        setEstablishment(res.data);
        setNotFound(!res.data);
      }).catch(err => {
        setError(err)
      });
  }, ['axios']);
  if (establishment) return <Establishment establishment={establishment} />;
  if (error) return  `Axios Error: ${error.message}`
  if (notFound) return <CreateEstablishment />;
  return 'Loading';
}

export default App;
