import { useSelector } from "react-redux";

function SpotHeader() {
  const { loading, data, error} = useSelector(state => state.spot);

  if (loading)
    return 'Loading spot';
  if (error)
    return JSON.stringify(error);

  return (
    <div>
      <span>Spot:</span>
      <span>{data?.name}</span>
      <span>.</span>
      <span>{data?.number}</span>
      <button>switch</button>
    </div>
  );
}

export default SpotHeader;
