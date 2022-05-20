import { useSelector } from "react-redux"
import Establishment from "./establishment/Establishment"
import EstablishmentLoader from "./EstablishmentLoader"

function EstablishmentRedirector() {
  const { loading, data, error} = useSelector(state => state.establishment)

  if (loading) return 'Loading establishment'
  if (error) return JSON.stringify(error)
  if (data) return <Establishment/>
  return <EstablishmentLoader/>
}

export default EstablishmentRedirector
