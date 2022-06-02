import { useDispatch } from "react-redux"
import { requestScan } from '../../../../state/spotSlice'

function Waiter() {
  const dispatch = useDispatch()

  function requestSpotChange() {
    dispatch(requestScan())
  }

  return (
    <div>
      <button>Call waiter</button>
      <button onClick={() => requestSpotChange()}>Change spot</button>
      <button>Close bill</button>
    </div>
  )
}

export default Waiter