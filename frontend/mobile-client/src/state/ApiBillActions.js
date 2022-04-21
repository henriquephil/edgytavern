import { ordersApi } from '../services/api';

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
export function openBill(establishmentHash) {
  return (dispatch, getState) => {
    console.log(getState, getState())
    dispatch(loadingBill());
    ordersApi.post(`/bill`, { establishmentHash })
      .then(res => {
        dispatch(fetchBill());
      })
      .catch(err => {
        dispatch(errorLoadingBill(err || { message: 'error loading establishment' }));
      })
  }
}

export function fetchBill() {
  return (dispatch, getState) => {
    dispatch(loadingBill());
    ordersApi.get(`/bill`)
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
    ordersApi.post(`/bill/orders`, {})
      .then(res => {
        dispatch(fetchBill());
      })
      .catch(err => {
        dispatch(errorLoadingBill(err || { message: 'error loading establishment' }));
      })
  }
}
