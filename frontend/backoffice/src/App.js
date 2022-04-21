import { useEffect } from 'react';
import CreateEstablishment from './pages/CreateEstablishment';
import Establishment from './pages/Establishment';
import { useDispatch, useSelector } from 'react-redux'
import { fetchEstablishment } from './state/actions';
import { withAuthenticator } from '@aws-amplify/ui-react';

import { Auth } from 'aws-amplify';

function App() {  
  const dispatch = useDispatch()
  
  useEffect(() => {
    dispatch(fetchEstablishment);
  }, [])
  let establishment = useSelector(state => state.establishment);

  if (establishment.loading) return 'Loading';
  if (establishment.error) return `Axios Error: ${establishment.error.message}`;
  if (establishment.data) return <Establishment/>;
  return <CreateEstablishment/>;
}

export default withAuthenticator(App);
