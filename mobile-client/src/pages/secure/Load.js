import { setLocation } from '../../state/sessionSlice';
import { useDispatch } from 'react-redux';
import ApiService from '../../services/ApiService';
import { useNavigate, useLocation } from 'react-router-dom';

function useQuery() {
  return new URLSearchParams(useLocation().search);
}

function Load() {
  let query = useQuery();
  const navigate = useNavigate();
  const dispatch = useDispatch();
  const apiService = new ApiService();

  apiService.beginSessionAt(query.get('establishment'), query.get('location'))
    .then(res => {
      dispatch(setLocation(res));
      navigate('/');
    });

  return (
    <div>
      <p>Loading</p>
    </div>
  );
}

export default Load;
