import { combineReducers, createStore, applyMiddleware } from "redux";
import thunk from 'redux-thunk';

function establishmentReducer(state = {
  data: null,
  error: null
}, action){
  switch (action.type) {
    case 'establishment.set':
      return {
        data: action.payload,
        error: null
      }
    case 'establishment.error':
      return {
        data: null,
        error: action.payload
      };
    case 'establishment.leave':
      return {
        data: null,
        error: null
      };
    default:
      return state;
  }
}

function billReducer(state = {
  loading: true,
  data: null,
  error: null
}, action){
  switch (action.type) {
    case 'bill.loading':
      return {
        loading: true,
        data: null,
        error: null
      }
    case 'bill.set':
      return {
        loading: false,
        data: action.payload,
        error: null
      };
    case 'bill.error':
      return {
        loading: false,
        data: null,
        error: action.payload
      };
    default:
      return state;
  }
}

function ordersReducer(state = {
  loading: true,
  data: [],
  error: null
}, action){
  switch (action.type) {
    case 'orders.loading':
      return {
        loading: true,
        data: [],
        error: null
      }
    case 'orders.set':
      return {
        loading: false,
        data: action.payload,
        error: null
      };
    case 'orders.error':
      return {
        loading: false,
        data: [],
        error: action.payload
      };
    default:
      return state;
  }
}

const store = createStore(combineReducers({
  establishment: establishmentReducer,
  bill: billReducer,
  orders: ordersReducer
}), applyMiddleware(thunk));

export default store;
