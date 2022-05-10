import Axios from 'axios';
import { convertDateStringsToDates } from '../utils';
import { Auth } from 'aws-amplify';

const api = Axios.create({
  transformResponse: Axios.defaults.transformResponse.concat((data) => {
    convertDateStringsToDates(data);
    return data;
  })
});

const responseErrorInterceptor = function(error) {
  console.log(error.message, error.response);
  return Promise.reject(error);
};

api.interceptors.request.use(async config => {
  const res = await Auth.currentSession();
  config.headers.Authorization = `Bearer ${res.getAccessToken().getJwtToken()}`;
  return config;
}, err => {
  console.log(err);
  Promise.reject(err);
});

api.interceptors.response.use(null, responseErrorInterceptor);
  
export default api;