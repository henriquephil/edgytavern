import { establishmentApi } from '../services/api';

export function loadingEstablishment() {
  return {
    type: 'establishment.loading'
  };
}

export function setEstablishment(establishment) {
  return {
    type: 'establishment.set',
    payload: establishment
  };
}

export function errorLoadingEstablishment(err) {
  return {
    type: 'establishment.error',
    payload: err
  };
}

export async function fetchEstablishment(dispatch, getState) {
  dispatch(loadingEstablishment());
  establishmentApi.get('/managed')
    .then(res => {
      dispatch(setEstablishment(res.data));
    })
    .catch(err => {
      dispatch(errorLoadingEstablishment(err || { message: 'error loading establishment' }));
    })
}