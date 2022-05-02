import { useSelector } from "react-redux";
import Bill from "./Bill";

function BillWrapper() {
  const billState = useSelector(state => state.bill);

  if (billState.loading)
    return <p>Loading</p>
  if (billState.error)
    return JSON.stringify(billState.error);

  if (billState.data)
    return <Bill/>
  return 'Opening a new Bill. Just a sec'
}


export default BillWrapper;
