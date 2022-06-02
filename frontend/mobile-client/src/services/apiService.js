import api from './api'
import { setEstablishment, setEstablishmentError, setEstablishmentLoading } from '../state/establishmentSlice'
import { billLoading, setBill, setBillError } from '../state/billSlice'
import { ordersLoading, setOrders, setOrdersError } from '../state/ordersSlice'
import { assetsLoading, setAssets, setAssetsError } from '../state/assetsSlice'
import { setSpot, setSpotError, setSpotLoading } from '../state/spotSlice'
import { emptyCart } from '../state/cartSlice'
import { FRAME_BILL, setVisibleFrame } from '../state/visibleFrameSlice'
 
export function fetchEstablishment(establishmentHash, spotHash) {
  return (dispatch, getState) => {
    dispatch(setEstablishmentLoading())
    api.get(`/api/establishment/${establishmentHash}`)
      .then(res => {
        dispatch(setEstablishment(res.data))
        dispatch(fetchSpot(spotHash))
      })
      .catch(err => {
        dispatch(setEstablishmentError(err || 'error loading establishment'))
      })
  }
}

function _getEstablishmentHashId(getState) {
  return getState().establishment?.data?.hashId
}

export function fetchSpot(spotHash) {
  return (dispatch, getState) => {
    dispatch(setSpotLoading())
    api.get(`/api/establishment/${_getEstablishmentHashId(getState)}/spots/${spotHash}`)
      .then(res => {
        dispatch(setSpot(res.data))
      })
      .catch(err => {
        dispatch(setSpotError(err || 'error loading spot'))
      })
  }
}

function _getSpotHashId(getState) {
  return getState().spot?.data?.hashId
}

export function fetchAssets() {
  return (dispatch, getState) => {
    dispatch(assetsLoading())
    api.get(`/api/establishment/${_getEstablishmentHashId(getState)}/assets`)
      .then(res => {
        dispatch(setAssets(res.data))
      })
      .catch(err => {
        dispatch(setAssetsError(err || 'error loading assets'))
      })
  }
}

export function getOrOpenBill() {
  return (dispatch, getState) => {
    dispatch(billLoading())
    api.post('/api/bills', { establishmentHash: _getEstablishmentHashId(getState) })
      .then(res => {
        dispatch(setBill(res.data))
        dispatch(fetchBillOrders())
      })
      .catch(err => {
        dispatch(setBillError(err || 'error opening bill'))
      })
  }
}

export function fetchBillOrders() {
  return (dispatch, getState) => {
    dispatch(ordersLoading())
    dispatch(setVisibleFrame(FRAME_BILL))
    api.get('/api/bills/orders')
      .then(res => {
        dispatch(setOrders(res.data))
      })
      .catch(err => {
        dispatch(setOrdersError(err ||'error loading orders'))
      })
  }
}

export function postBillOrder() {
  return async (dispatch, getState) => {
    try{
      const cartState = getState().cart
      const items = cartState.items?.map(it => {
        return {
          assetHashId: it.asset.hashId,
          removedIngredientsHashIds: it.removedIngredients,
          additionalsHashIds: it.selectedAdditionals,
          quantity: it.quantity,
          finalValue: cartState.finalValue
        }
      })
      const spotHash = _getSpotHashId(getState)
      if (items?.length > 0 && spotHash) {
        await api.post(`/api/bills/orders`, {
          spotHash,
          items
        })
        dispatch(emptyCart())
        dispatch(fetchBillOrders())
      }
    } catch (err) {
      dispatch(setOrdersError(err || 'error posting order'))
      return err || 'An error ocurred'
    }
  }
}
