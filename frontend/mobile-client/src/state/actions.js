import { ordersApi } from "../services/api";

export function setEstablishment(location) {
  return {
    type: 'establishment.set',
    payload: location
  };
}

export function errorLoadingEstablishment(location) {
  return {
    type: 'establishment.error',
    payload: null
  };
}

export function fetchLocation(establishmentHash) {
  return (dispatch, getState) => {
    ordersApi.post('/orders/bill', { establishmentHash })
      .then(({ data }) => {
        dispatch(setEstablishment(data));
      })
      .catch(err => {
        dispatch(errorLoadingEstablishment(err || { message: 'error loading establishment' }));
      })
    }
}