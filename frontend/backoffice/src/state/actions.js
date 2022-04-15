import { api } from '../api';

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
  api.get('/managed')
    .then(res => {
      dispatch(setEstablishment(res.data));
    })
    .catch(err => {
      dispatch(errorLoadingEstablishment(err || { message: 'error loading establishment' }));
    })
  // try {
  //   const { data } = await api.get('/managed')
  //   dispatch(setEstablishment(data));
  // } catch (err) {
  //   dispatch(errorLoadingEstablishment(err));
  // }
}