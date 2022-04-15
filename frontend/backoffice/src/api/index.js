import Axios from 'axios';
import { convertDateStringsToDates } from '../utils';

const requestErrorInterceptor = function(error) {
  console.log('requestErrorInterceptor')
  return Promise.reject(error);
}
const responseErrorInterceptor = function(error) {
  // TODO Feedback
  console.log(error.message, error.response);
  return Promise.reject(error);
};

const api = Axios.create({
    baseURL: process.env.REACT_APP_BACKEND,
    transformResponse: Axios.defaults.transformResponse.concat((data) => {
        convertDateStringsToDates(data);
        return data;
    })
});

function setRequestInterceptor(reqFulfilled) {
  api.interceptors.request.use(reqFulfilled, requestErrorInterceptor);
}

function setResponseInterceptor(respFulfilled) {
  api.interceptors.response.use(respFulfilled, responseErrorInterceptor);
}

setRequestInterceptor(config => config)
setResponseInterceptor(response => response)

export { api, setRequestInterceptor, setResponseInterceptor };