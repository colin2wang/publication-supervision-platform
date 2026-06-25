export interface R<T> {
  code: number
  message: string
  data: T
}

export interface PageResult<T> {
  records: T[]
  total: number
  current: number
  size: number
}

export interface PageParams {
  current?: number
  size?: number
  [key: string]: any
}

export interface LoginRequest {
  username: string
  password: string
  captcha: string
  captchaKey: string
}

export interface LoginResult {
  token: string
}

export interface UserInfo {
  id: number
  username: string
  realName: string
  avatar: string
  roles: string[]
  permissions: string[]
}

export interface CaptchaResult {
  uuid: string
  image: string
}
