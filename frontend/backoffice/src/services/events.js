import webstomp from 'webstomp-client';
import { Auth } from 'aws-amplify';

export async function events(store, establishmentHashId) {
  const res = await Auth.currentSession();
  var sse = new EventSource(`http://localhost:8088/establishment?hashId=${establishmentHashId}`);
  sse.onmessage = function (evt) {
    console.log(evt)
  };
}