import Axios from 'axios';
import { convertDateStringsToDates } from '../utils';
import { Auth } from 'aws-amplify';

function apiBuilder(baseURL) {
  const api = Axios.create({
    baseURL,
    transformResponse: Axios.defaults.transformResponse.concat((data) => {
        convertDateStringsToDates(data);
        return data;
    })
  });

  const responseErrorInterceptor = function(error) {
    console.log(error.message, error.response);
    return Promise.reject(error);
  };

  api.interceptors.request.use(config => {
    return Auth.currentSession().then(res=>{
      config.headers.Authorization = `Bearer ${res.getAccessToken().getJwtToken()}`
      return config;
    })
  }, err => {
    console.log(err);
    Promise.reject(err);
  });

  api.interceptors.response.use(null, responseErrorInterceptor);
    
  return api;
}

const establishmentApi = apiBuilder(process.env.REACT_APP_BACKEND_ESTABLISHMENT);

const ordersApi = apiBuilder(process.env.REACT_APP_BACKEND_ORDERS);

export { establishmentApi, ordersApi };