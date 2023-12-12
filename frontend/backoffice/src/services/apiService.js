import api from './api'
import { setEstablishment, setEstablishmentError, setEstablishmentLoading } from '../state/establishmentSlice'
import { setOpenBills, setOpenBillsError, setOpenBillsLoading } from '../state/openBillsSlice'
import { setRegister, setRegisterError, setRegisterLoading } from '../state/registerSlice'
 
export function fetchEstablishment() {
  return (dispatch, getState) => {
    dispatch(setEstablishmentLoading())
    api.get(`/api/establishment/managed`)
      .then(res => {
        dispatch(setEstablishment(res.data))
      })
      .catch(err => {
        dispatch(setEstablishmentError(err || 'error loading establishment'))
      })
  }
}
 
export function createEstablishment(name) {
  return (dispatch, getState) => {
    dispatch(setEstablishmentLoading())
    api.post(`/api/establishment/managed`, { name })
      .then(res => {
        dispatch(setEstablishment(res.data))
      })
      .catch(err => {
        dispatch(setEstablishmentError(err || 'error loading establishment'))
      })
  }
}

export function fetchRegister() {
  return (dispatch, getState) => {
    if (!getState().establishment.data) {
      dispatch(setRegister(null))
      return
    }
    dispatch(setRegisterLoading())
    api.get(`/api/counter/admin/active/register`)
      .then(res => {
        dispatch(setRegister(res.data))
      })
      .catch(err => {
        dispatch(setRegisterError(err || 'error loading establishment'))
      })
  }
}

export function openRegister() {
  return (dispatch, getState) => {
    dispatch(setRegisterLoading())
    api.post(`/api/counter/admin/active/register`)
      .then(res => {
        dispatch(setRegister(res.data))
      })
      .catch(err => {
        dispatch(setRegisterError(err || 'error loading establishment'))
      })
  }
}

export function closeRegister() {
  return async (dispatch, getState) => {
    const res = await api.post(`/api/counter/admin/active/register/close`)
    dispatch(setRegister(null))
    return res
  }
}
 
export function fetchOpenBills() {
  return (dispatch, getState) => {
    if (!getState().establishment.data) {
      dispatch(setOpenBills([]))
      return
    }
    dispatch(setOpenBillsLoading())
    api.get(`/api/counter/admin/active/bills`)
      .then(res => {
        dispatch(setOpenBills(res.data))
      })
      .catch(err => {
        dispatch(setOpenBillsError(err || 'error loading bills'))
      })
  }
}
