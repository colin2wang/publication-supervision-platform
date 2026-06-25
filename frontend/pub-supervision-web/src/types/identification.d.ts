export interface Publication {
  id: number
  isbn: string
  title: string
  author: string
  publisher: string
  publishDate: string
  source: string
  category: string
  status: string
  createTime: string
  updateTime: string
}

export interface IdentificationTask {
  id: number
  taskCode: string
  taskName: string
  taskType: string
  priority: string
  status: string
  assignee: string
  description: string
  deadline: string
  createTime: string
  updateTime: string
}

export interface IdentificationResult {
  id: number
  taskId: number
  publicationId: number
  resultType: string
  confidence: number
  summary: string
  details: string
  reviewer: string
  reviewTime: string
  reviewStatus: string
}

export interface Material {
  id: number
  taskId: number
  materialType: string
  fileName: string
  fileUrl: string
  fileSize: number
  status: string
  createTime: string
}

export interface Sample {
  id: number
  sampleCode: string
  sampleName: string
  category: string
  source: string
  description: string
  collectedAt: string
  status: string
  createTime: string
}

export interface Dataset {
  id: number
  datasetName: string
  description: string
  recordCount: number
  category: string
  status: string
  createTime: string
  updateTime: string
}
