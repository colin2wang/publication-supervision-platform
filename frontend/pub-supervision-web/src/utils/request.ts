import axios from 'axios'
import { getToken, removeToken } from './auth'
import { message } from 'ant-design-vue'
import router from '@/router'

const request = axios.create({
  baseURL: import.meta.env.VITE_API_BASE_URL,
  timeout: 30000
})

request.interceptors.request.use(
  (config) => {
    const token = getToken()
    if (token) {
      config.headers.Authorization = `Bearer ${token}`
    }
    return config
  },
  (error) => Promise.reject(error)
)

request.interceptors.response.use(
  (response) => {
    const res = response.data
    if (res.code !== 0 && res.code !== 200) {
      message.error(res.message || '请求失败')
      return Promise.reject(new Error(res.message || '请求失败'))
    }
    return res.data
  },
  (error) => {
    if (error.response) {
      const { status } = error.response
      if (status === 401) {
        removeToken()
        message.error('登录已过期，请重新登录')
        router.push('/login')
      } else if (status === 403) {
        message.error('无权限访问')
        router.push('/403')
      } else if (status === 500) {
        message.error('服务器错误')
      } else {
        message.error(error.response.data?.message || '请求失败')
      }
    } else {
      message.error('网络异常，请检查网络连接')
    }
    return Promise.reject(error)
  }
)

export default request
