import request from '@/utils/request'
import type { PageParams, PageResult } from '@/types/api'
import type { Publication, IdentificationTask, IdentificationResult, Material, Sample, Dataset } from '@/types/identification'

export function getPublications(params: PageParams) {
  return request.get<any, PageResult<Publication>>('/api/v1/identification/publications', { params })
}

export function getPublication(id: number) {
  return request.get<any, Publication>(`/api/v1/identification/publications/${id}`)
}

export function createPublication(data: Partial<Publication>) {
  return request.post('/api/v1/identification/publications', data)
}

export function updatePublication(id: number, data: Partial<Publication>) {
  return request.put(`/api/v1/identification/publications/${id}`, data)
}

export function deletePublication(id: number) {
  return request.delete(`/api/v1/identification/publications/${id}`)
}

export function getTasks(params: PageParams) {
  return request.get<any, PageResult<IdentificationTask>>('/api/v1/identification/tasks', { params })
}

export function getTask(id: number) {
  return request.get<any, IdentificationTask>(`/api/v1/identification/tasks/${id}`)
}

export function createTask(data: Partial<IdentificationTask>) {
  return request.post('/api/v1/identification/tasks', data)
}

export function updateTask(id: number, data: Partial<IdentificationTask>) {
  return request.put(`/api/v1/identification/tasks/${id}`, data)
}

export function deleteTask(id: number) {
  return request.delete(`/api/v1/identification/tasks/${id}`)
}

export function assignTask(id: number, data: { assignee: string }) {
  return request.post(`/api/v1/identification/tasks/${id}/assign`, data)
}

export function startTask(id: number) {
  return request.post(`/api/v1/identification/tasks/${id}/start`)
}

export function submitResult(id: number, data: Partial<IdentificationResult>) {
  return request.post(`/api/v1/identification/tasks/${id}/submit`, data)
}

export function reviewTask(id: number, data: { reviewStatus: string; reviewComment: string }) {
  return request.post(`/api/v1/identification/tasks/${id}/review`, data)
}

export function getSamples(params: PageParams) {
  return request.get<any, PageResult<Sample>>('/api/v1/identification/samples', { params })
}

export function createSample(data: Partial<Sample>) {
  return request.post('/api/v1/identification/samples', data)
}

export function deleteSample(id: number) {
  return request.delete(`/api/v1/identification/samples/${id}`)
}

export function getDatasets(params: PageParams) {
  return request.get<any, PageResult<Dataset>>('/api/v1/identification/datasets', { params })
}

export function createDataset(data: Partial<Dataset>) {
  return request.post('/api/v1/identification/datasets', data)
}

export function deleteDataset(id: number) {
  return request.delete(`/api/v1/identification/datasets/${id}`)
}
