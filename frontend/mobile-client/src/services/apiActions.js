import { establishmentApi } from './api';
import { setEstablishment, setEstablishmentError } from '../state/establishmentSlice';
import { billsApi } from './api';
import { billLoading, setBill, setBillError } from '../state/billSlice';
import { setOrders, setOrdersError } from '../state/ordersSlice';
import { assetsLoading, setAssets, setAssetsError } from '../state/assetsSlice';
 
function _getEstablishmentHashId(getState) {
  return getState().establishment?.data?.hashId;
}

export function fetchEstablishment(establishmentHash) {
  return (dispatch, getState) => {
    establishmentApi.get(`${establishmentHash}`)
      .then(res => {
        dispatch(setEstablishment(res.data));
      })
      .catch(err => {
        dispatch(setEstablishmentError(err || { message: 'error loading establishment' }));
      })
  };
}

export function fetchAssets() {
  return (dispatch, getState) => {
    dispatch(assetsLoading());
    establishmentApi.get(`${_getEstablishmentHashId(getState)}/assets`)
      .then(res => {
        dispatch(setAssets(res.data));
      })
      .catch(err => {
        dispatch(setAssetsError(err || { message: 'error loading establishment' }));
      })
  }
}

export function openBill() {
  return (dispatch, getState) => {
    dispatch(billLoading());
    billsApi.post('/', { establishmentHash: _getEstablishmentHashId(getState) })
      .then(res => {
        dispatch(setBill(res.data));
      })
      .catch(err => {
        dispatch(setBillError(err || { message: 'error opening bill' }));
      })
  }
}

export function fetchBill() {
  return (dispatch, getState) => {
    dispatch(billLoading());
    billsApi.get('/')
      .then(res => {
        dispatch(setBill(res.data));
      })
      .catch(err => {
        dispatch(setBillError(err || { message: 'error loading bill' }));
      })
  }
}

export function fetchBillOrders() {
  return (dispatch, getState) => {
    billsApi.get('/orders')
      .then(res => {
        dispatch(setOrders(res.data));
      })
      .catch(err => {
        dispatch(setOrdersError(err || { message: 'error loading establishment' }));
      })
  }
}

export function postBillOrder(order) {
  return (dispatch, getState) => {
    billsApi.post(`/orders`, order)
      .then(res => {
        dispatch(fetchBill());
      })
      .catch(err => {
        dispatch(setOrdersError(err || { message: 'error loading establishment' }));
      })
  }
}
