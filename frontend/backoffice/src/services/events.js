export async function events(store, establishmentHashId) {
  var sse = new EventSource(`http://localhost:8088/establishment?hashId=${establishmentHashId}`);
  sse.onmessage = function (evt) {
    console.log(evt)
  };
}