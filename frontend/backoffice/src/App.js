import { useEffect } from 'react';
import CreateEstablishment from './pages/CreateEstablishment';
import Establishment from './pages/establishment/Establishment';
import { useDispatch, useSelector } from 'react-redux'
import { fetchEstablishment } from './services/apiService';
import { withAuthenticator } from '@aws-amplify/ui-react';
import '@aws-amplify/ui-react/styles.css';

function App() {  
  let { loading, data, error } = useSelector(state => state.establishment);
  const dispatch = useDispatch();
  
  useEffect(() => {
    dispatch(fetchEstablishment());
  }, [])

  if (loading) return 'Loading establishment';
  if (error) return `Axios Error: ${JSON.stringify(error)}`;
  if (data) return <Establishment/>;
  return <CreateEstablishment/>;
}

export default withAuthenticator(App);
