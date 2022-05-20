import { fetchOpenBills, fetchRegister } from "../services/apiService"
import { events } from "../services/events"

function globalStateChangeHandler(store) {
  let currState = store.getState()
  store.subscribe(() => {
    const previousState = currState
    currState = store.getState()

    if (previousState.establishment.data?.hashId !== currState.establishment.data?.hashId) {
      store.dispatch(fetchRegister())
      events(store, currState.establishment.data?.hashId)
    }

    if (previousState.register.data?.hashId !== currState.register.data?.hashId) {
      store.dispatch(fetchOpenBills())
    }
  })
}

export default globalStateChangeHandler