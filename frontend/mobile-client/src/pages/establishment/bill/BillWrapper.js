import { useDispatch, useSelector } from "react-redux";
import { openBill } from '../../../services/apiActions';
import Bill from "./Bill";

function BillWrapper() {
  const dispatch = useDispatch();
  const billState = useSelector(state => state.bill);

  if (billState.loading)
    return <p>Loading</p>
  if (billState.error)
    return JSON.stringify(billState.error);

  const dispatchOpenBill = () => {
    dispatch(openBill());
  }

  if (billState.data)
    return <Bill/>
  return (
    <button onClick={() => dispatchOpenBill()}>Begin a new bill and order someting!</button>
  )
}


export default BillWrapper;
