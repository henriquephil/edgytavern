import api from "./api"

export async function events(store, establishmentHashId) {
  const codeResponse = await api.post('/api/events/establishment/code')
  var sse = new EventSource(`http://localhost:8088/establishment?code=${codeResponse.code}`)
  sse.onmessage = function (evt) {
    console.log(evt)
  }
}