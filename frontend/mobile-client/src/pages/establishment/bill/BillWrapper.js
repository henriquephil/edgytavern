import { useSelector } from "react-redux";
import Bill from "./Bill";

function BillWrapper() {
  const { loading, data, error } = useSelector(state => state.bill);

  if (loading)
    return 'Loading bill'
  if (error)
    return JSON.stringify(error);
  if (data)
    return <Bill/>
  return 'Opening a new Bill. Just a sec'
}

export default BillWrapper;
