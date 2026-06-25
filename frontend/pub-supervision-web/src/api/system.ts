import request from '@/utils/request'
import type { PageParams, PageResult } from '@/types/api'
import type { UserInfo } from '@/types/api'

export function getUsers(params: PageParams) {
  return request.get<any, PageResult<UserInfo>>('/api/v1/system/users', { params })
}

export function getUser(id: number) {
  return request.get<any, UserInfo>(`/api/v1/system/users/${id}`)
}

export function createUser(data: any) {
  return request.post('/api/v1/system/users', data)
}

export function updateUser(id: number, data: any) {
  return request.put(`/api/v1/system/users/${id}`, data)
}

export function deleteUser(id: number) {
  return request.delete(`/api/v1/system/users/${id}`)
}

export function getRoles(params: PageParams) {
  return request.get<any, PageResult<any>>('/api/v1/system/roles', { params })
}

export function createRole(data: any) {
  return request.post('/api/v1/system/roles', data)
}

export function updateRole(id: number, data: any) {
  return request.put(`/api/v1/system/roles/${id}`, data)
}

export function deleteRole(id: number) {
  return request.delete(`/api/v1/system/roles/${id}`)
}

export function getMenuTree() {
  return request.get('/api/v1/system/menus/tree')
}

export function createMenu(data: any) {
  return request.post('/api/v1/system/menus', data)
}

export function updateMenu(id: number, data: any) {
  return request.put(`/api/v1/system/menus/${id}`, data)
}

export function deleteMenu(id: number) {
  return request.delete(`/api/v1/system/menus/${id}`)
}

export function getDepartmentTree() {
  return request.get('/api/v1/system/departments/tree')
}

export function getDictData(code: string) {
  return request.get(`/api/v1/system/dict/data/${code}`)
}

export function getNotifications(params: PageParams) {
  return request.get<any, PageResult<any>>('/api/v1/system/notifications', { params })
}

export function readNotification(id: number) {
  return request.patch(`/api/v1/system/notifications/${id}/read`)
}

export function readAllNotifications() {
  return request.post('/api/v1/system/notifications/read-all')
}

export function uploadFile(file: File) {
  const formData = new FormData()
  formData.append('file', file)
  return request.post('/api/v1/system/files/upload', formData, {
    headers: { 'Content-Type': 'multipart/form-data' }
  })
}
