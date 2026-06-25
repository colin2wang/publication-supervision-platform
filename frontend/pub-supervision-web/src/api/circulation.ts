import request from '@/utils/request'
import type { PageParams, PageResult } from '@/types/api'
import type { Merchant, Qualification, Package, Alert, BlacklistItem } from '@/types/circulation'

export function getMerchants(params: PageParams) {
  return request.get<any, PageResult<Merchant>>('/api/v1/circulation/merchants', { params })
}

export function getMerchant(id: number) {
  return request.get<any, Merchant>(`/api/v1/circulation/merchants/${id}`)
}

export function createMerchant(data: Partial<Merchant>) {
  return request.post('/api/v1/circulation/merchants', data)
}

export function updateMerchant(id: number, data: Partial<Merchant>) {
  return request.put(`/api/v1/circulation/merchants/${id}`, data)
}

export function deleteMerchant(id: number) {
  return request.delete(`/api/v1/circulation/merchants/${id}`)
}

export function getPackages(params: PageParams) {
  return request.get<any, PageResult<Package>>('/api/v1/circulation/packages', { params })
}

export function getPackage(id: number) {
  return request.get<any, Package>(`/api/v1/circulation/packages/${id}`)
}

export function getTracking(id: number) {
  return request.get(`/api/v1/circulation/packages/${id}/tracking`)
}

export function interceptPackage(id: number, data: { reason: string }) {
  return request.post(`/api/v1/circulation/packages/${id}/intercept`, data)
}

export function getAlerts(params: PageParams) {
  return request.get<any, PageResult<Alert>>('/api/v1/circulation/alerts', { params })
}

export function getAlert(id: number) {
  return request.get<any, Alert>(`/api/v1/circulation/alerts/${id}`)
}

export function verifyAlert(id: number, data: { verified: boolean; remark: string }) {
  return request.post(`/api/v1/circulation/alerts/${id}/verify`, data)
}

export function handleAlert(id: number, data: { handleResult: string }) {
  return request.post(`/api/v1/circulation/alerts/${id}/handle`, data)
}

export function getLists(params: PageParams) {
  return request.get<any, PageResult<BlacklistItem>>('/api/v1/circulation/lists', { params })
}

export function addToList(data: Partial<BlacklistItem>) {
  return request.post('/api/v1/circulation/lists', data)
}

export function removeFromList(id: number) {
  return request.delete(`/api/v1/circulation/lists/${id}`)
}

export function getOverview(params: any) {
  return request.get('/api/v1/circulation/statistics/overview', { params })
}
