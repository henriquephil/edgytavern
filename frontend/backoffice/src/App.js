import CreateEstablishment from './pages/CreateEstablishment'
import Establishment from './pages/establishment/Establishment'
import { useDispatch, useSelector } from 'react-redux'
import { fetchEstablishment } from './services/apiService'
import { useLocalStorage } from 'react-use'
import { Login } from 'tavern-commons'

function App() {
  const [ session, _ ] = useLocalStorage("sessionInfo")
  let { loading, data, error, dispatched } = useSelector(state => state.establishment)
  const dispatch = useDispatch()
  
  if (!session) return <Login/>// TODO extract
  if (loading) return 'Loading establishment'
  if (error) return `Axios Error: ${JSON.stringify(error)}`
  if (data) return <Establishment/>
  if (dispatched) {
    return <CreateEstablishment/>
  }
  dispatch(fetchEstablishment())
}

export default App
