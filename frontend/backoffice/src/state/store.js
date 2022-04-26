import { combineReducers, createStore, applyMiddleware } from "redux";
import thunk from 'redux-thunk';

import _ from 'lodash';

function randomString() {
  return _.range(Math.floor(Math.random() * 20) + 5)
    .map(i => String.fromCharCode(Math.floor(Math.random() * 26) + 65)).join('')
}

let cid = 1;
const mockedBills = _.range(Math.floor(Math.random() * 20) + 15)
  .map(id => {
    return {
      spot: {
        id: id++,
        name: randomString()
      },
      bills: _.range(Math.floor(Math.random() * 6) + 1).map(it => {
        return {
          id: cid++,
          customerName: randomString(),
          total: Math.floor(Math.random() * 100)
        }
      })
    }
  })

const establishmentState = {
  data:null,
  error: null,
  loading: false
};
function establishmentReducer(state = establishmentState, action){
  switch (action.type) {
    case 'establishment.loading':
      return {
        data:null,
        error: null,
        loading: true
      };
    case 'establishment.set':
      return {
        data: action.payload,
        error: null,
        loading: false
      };
    case 'establishment.error':
      return {
        data: null,
        error: action.payload,
        loading: false
      };
    default:
      return state;
  }
}

function openBillsReducer(state = mockedBills, action){
  switch (action.type) {
      case 'ADD_BILL':
        return [
          ...state,
          action.payload
        ];
      default:
        return state;
    }
}

const store = createStore(combineReducers({
  establishment: establishmentReducer,
  openBills: openBillsReducer
}), applyMiddleware(thunk));

export default store;
