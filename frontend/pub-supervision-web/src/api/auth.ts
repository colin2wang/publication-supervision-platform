import request from '@/utils/request'
import type { LoginRequest, CaptchaResult, LoginResult, UserInfo } from '@/types/api'

export function login(data: LoginRequest) {
  return request.post<any, LoginResult>('/api/v1/auth/login', data)
}

export function logout() {
  return request.post('/api/v1/auth/logout')
}

export function getUserInfo() {
  return request.get<any, UserInfo>('/api/v1/auth/userinfo')
}

export function getCaptcha() {
  return request.get<any, CaptchaResult>('/api/v1/auth/captcha')
}
