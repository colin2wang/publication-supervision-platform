import request from '@/utils/request'
import type { PageParams, PageResult } from '@/types/api'
import type { CollectionTask, Opinion, Event, Report } from '@/types/opinion'

export function getCollectionTasks(params: PageParams) {
  return request.get<any, PageResult<CollectionTask>>('/api/v1/opinion/collection-tasks', { params })
}

export function createCollectionTask(data: Partial<CollectionTask>) {
  return request.post('/api/v1/opinion/collection-tasks', data)
}

export function executeTask(id: number) {
  return request.post(`/api/v1/opinion/collection-tasks/${id}/execute`)
}

export function pauseTask(id: number) {
  return request.post(`/api/v1/opinion/collection-tasks/${id}/pause`)
}

export function deleteCollectionTask(id: number) {
  return request.delete(`/api/v1/opinion/collection-tasks/${id}`)
}

export function getOpinions(params: PageParams) {
  return request.get<any, PageResult<Opinion>>('/api/v1/opinion/opinions', { params })
}

export function getOpinion(id: number) {
  return request.get<any, Opinion>(`/api/v1/opinion/opinions/${id}`)
}

export function deleteOpinion(id: number) {
  return request.delete(`/api/v1/opinion/opinions/${id}`)
}

export function getEvents(params: PageParams) {
  return request.get<any, PageResult<Event>>('/api/v1/opinion/events', { params })
}

export function createEvent(data: Partial<Event>) {
  return request.post('/api/v1/opinion/events', data)
}

export function deleteEvent(id: number) {
  return request.delete(`/api/v1/opinion/events/${id}`)
}

export function getReports(params: PageParams) {
  return request.get<any, PageResult<Report>>('/api/v1/opinion/reports', { params })
}

export function generateReport(data: { reportType: string; period: string }) {
  return request.post('/api/v1/opinion/reports/generate', data)
}

export function downloadReport(id: number) {
  return request.get(`/api/v1/opinion/reports/${id}/download`, { responseType: 'blob' })
}

export function reviewReport(id: number, data: { reviewStatus: string }) {
  return request.post(`/api/v1/opinion/reports/${id}/review`, data)
}

export function getEffectiveness(params: any) {
  return request.get('/api/v1/opinion/effectiveness/overview', { params })
}
