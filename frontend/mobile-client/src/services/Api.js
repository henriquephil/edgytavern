import Axios from 'axios'
import { auth } from 'tavern-commons'
import { convertDateStringsToDates } from '../utils'

const api = Axios.create({
  transformResponse: Axios.defaults.transformResponse.concat((data) => {
    convertDateStringsToDates(data)
    return data
  })
})

const responseErrorInterceptor = function(error) {
  console.log(error.message, error.response)
  return Promise.reject(error)
}

api.interceptors.request.use(async config => {
  const session = await auth.refreshAndGetSession()
  config.headers.Authorization = session.mountedToken
  return config
}, err => {
  console.log(err)
  Promise.reject(err)
})

api.interceptors.response.use(null, responseErrorInterceptor)
  
export default api