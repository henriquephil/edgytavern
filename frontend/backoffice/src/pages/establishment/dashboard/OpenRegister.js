import { useDispatch } from "react-redux"
import { openRegister } from "../../../services/apiService"

function OpenRegister() {
  const dispatch = useDispatch()

  function clickOpenRegister() {
    dispatch(openRegister())
  }

  return (
    <div>
      <button onClick={() => clickOpenRegister()}>Open your register to be able to receive orders</button>
    </div>
  )
}

export default OpenRegister
