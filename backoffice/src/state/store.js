import { combineReducers, createStore, applyMiddleware } from "redux";
import thunk from 'redux-thunk';

let id = 1;
let cid = 1;
const mockedBills = [
  { spot: { id: id++, name: 'asdas' }, bills: [ { id: cid++, customerName: 'asdasd', total: 42 }, { id: cid++, customerName: 'qwewqe', total: 42 } ] },
  { spot: { id: id++, name: 'zxczxc' }, bills: [ { id: cid++, customerName: 'asdasd', total: 42 }, { id: cid++, customerName: 'qwewqe', total: 42 } ] },
  { spot: { id: id++, name: 'gsdfsd' }, bills: [ { id: cid++, customerName: 'asdasd', total: 42 }, { id: cid++, customerName: 'qwewqe', total: 42 } ] },
  { spot: { id: id++, name: 'hgfgh' }, bills: [ { id: cid++, customerName: 'asdasd', total: 42 }, { id: cid++, customerName: 'qwewqe', total: 42 } ] },
  { spot: { id: id++, name: 'vbnvbn' }, bills: [ { id: cid++, customerName: 'asdasd', total: 42 }, { id: cid++, customerName: 'qwewqe', total: 42 } ] },
  { spot: { id: id++, name: 'ytutyu' }, bills: [ { id: cid++, customerName: 'asdasd', total: 42 }, { id: cid++, customerName: 'qwewqe', total: 42 } ] },
  { spot: { id: id++, name: 'ertert' }, bills: [ { id: cid++, customerName: 'asdasd', total: 42 }, { id: cid++, customerName: 'qwewqe', total: 42 } ] },
  { spot: { id: id++, name: '3re23r' }, bills: [ { id: cid++, customerName: 'asdasd', total: 42 }, { id: cid++, customerName: 'qwewqe', total: 42 } ] },
  { spot: { id: id++, name: 'fvvce' }, bills: [ { id: cid++, customerName: 'asdasd', total: 42 }, { id: cid++, customerName: 'qwewqe', total: 42 } ] },
  { spot: { id: id++, name: '3re23r' }, bills: [ { id: cid++, customerName: 'asdasd', total: 42 }, { id: cid++, customerName: 'qwewqe', total: 42 } ] },
  { spot: { id: id++, name: 'fvvce' }, bills: [ { id: cid++, customerName: 'asdasd', total: 42 }, { id: cid++, customerName: 'qwewqe', total: 42 } ] },
]

const establishmentState = {
  data:null,
  loading: false,
  error: null
};
function establishmentReducer(state = establishmentState, action){
  switch (action.type) {
    case 'establishment.loading':
      return {
        data: null,
        loading: true,
        error: null
      };
    case 'establishment.set':
      return {
        data: action.payload,
        loading: false,
        error: null
      };
    case 'establishment.error':
      return {
        data: null,
        loading: false,
        error: action.payload
      };
    default:
      return state
  }
}

function openBillsReducer(state = mockedBills, action){
  switch (action.type) {
      case 'ADD_BILL':
        return [
          ...state,
          action.payload
        ]
      default:
        return state
    }
}

const store = createStore(combineReducers({
  establishment: establishmentReducer,
  openBills: openBillsReducer
}), applyMiddleware(thunk));

export default store;
