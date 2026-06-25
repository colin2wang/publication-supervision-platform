import request from '@/utils/request'
import type { PageParams, PageResult } from '@/types/api'

export function getKnowledgeBases(params: PageParams) {
  return request.get<any, PageResult<any>>('/api/v1/ai/knowledge-bases', { params })
}

export function createKnowledgeBase(data: any) {
  return request.post('/api/v1/ai/knowledge-bases', data)
}

export function deleteKnowledgeBase(id: number) {
  return request.delete(`/api/v1/ai/knowledge-bases/${id}`)
}

export function searchKnowledge(kbId: number, data: { query: string }) {
  return request.post(`/api/v1/ai/knowledge-bases/${kbId}/search`, data)
}

export function getModels(params: PageParams) {
  return request.get<any, PageResult<any>>('/api/v1/ai/models', { params })
}

export function registerModel(data: any) {
  return request.post('/api/v1/ai/models', data)
}

export function deleteModel(id: number) {
  return request.delete(`/api/v1/ai/models/${id}`)
}

export function getAgents(params: PageParams) {
  return request.get<any, PageResult<any>>('/api/v1/ai/agents', { params })
}

export function createAgent(data: any) {
  return request.post('/api/v1/ai/agents', data)
}

export function publishAgent(id: number) {
  return request.post(`/api/v1/ai/agents/${id}/publish`)
}

export function invokeAgent(id: number, data: { input: string }) {
  return request.post(`/api/v1/ai/agents/${id}/invoke`, data)
}

export function deleteAgent(id: number) {
  return request.delete(`/api/v1/ai/agents/${id}`)
}

export function getDigitalHumans(params: PageParams) {
  return request.get<any, PageResult<any>>('/api/v1/ai/digital-humans', { params })
}

export function createDigitalHuman(data: any) {
  return request.post('/api/v1/ai/digital-humans', data)
}

export function deleteDigitalHuman(id: number) {
  return request.delete(`/api/v1/ai/digital-humans/${id}`)
}
