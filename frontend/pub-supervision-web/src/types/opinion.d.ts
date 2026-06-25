export interface CollectionTask {
  id: number
  taskName: string
  taskType: string
  platform: string
  keywords: string
  schedule: string
  status: string
  lastExecuteTime: string
  collectedCount: number
  createTime: string
  updateTime: string
}

export interface Opinion {
  id: number
  title: string
  content: string
  source: string
  platform: string
  author: string
  sentiment: string
  sentimentScore: number
  riskLevel: string
  engagementCount: number
  replyCount: number
  repostCount: number
  collectedAt: string
  createTime: string
}

export interface Event {
  id: number
  eventCode: string
  eventTitle: string
  eventType: string
  eventLevel: string
  source: string
  description: string
  status: string
  relatedOpinions: number[]
  createTime: string
  updateTime: string
}

export interface Report {
  id: number
  reportTitle: string
  reportType: string
  period: string
  status: string
  summary: string
  generatedBy: string
  generatedTime: string
  reviewedBy: string
  reviewedTime: string
  fileUrl: string
}
