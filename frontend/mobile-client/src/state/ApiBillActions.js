import { billsApi } from '../services/api';

export function loadingBill() {
  return {
    type: 'bill.loading'
  };
}

export function errorLoadingBill(error) {
  return {
    type: 'bill.error',
    payload: error
  };
}

export function setBill(bill) {
  return {
    type: 'bill.set',
    payload: bill
  };
}
 
/*
 * POST /bill
 * DELETE /bill
 * GET /bill
 * POST /bill/orders
 */
export function openBill() {
  return (dispatch, getState) => {
    dispatch(loadingBill());
    billsApi.post('/', { establishmentHash: getState().establishment.data.hashId })
      .then(res => {
        dispatch(setBill(res.data));
      })
      .catch(err => {
        dispatch(errorLoadingBill(err || { message: 'error opening bill' }));
      })
  }
}

export function fetchBill() {
  return (dispatch, getState) => {
    dispatch(loadingBill());
    billsApi.get('/')
      .then(res => {
        dispatch(setBill(res.data));
      })
      .catch(err => {
        dispatch(errorLoadingBill(err || { message: 'error loading bill' }));
      })
  }
}

export function fetchBillOrders() {
  return (dispatch, getState) => {
    dispatch(loadingBill());
    billsApi.get('/orders')
      .then(res => {
        dispatch(setBill(res.data));
      })
      .catch(err => {
        dispatch(errorLoadingBill(err || { message: 'error loading establishment' }));
      })
  }
}

export function postBillOrder() {
  return (dispatch, getState) => {
    billsApi.post(`/orders`, {})
      .then(res => {
        dispatch(fetchBill());
      })
      .catch(err => {
        dispatch(errorLoadingBill(err || { message: 'error loading establishment' }));
      })
  }
}
