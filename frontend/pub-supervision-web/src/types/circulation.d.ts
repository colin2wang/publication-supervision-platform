export interface Merchant {
  id: number
  merchantName: string
  merchantType: string
  legalPerson: string
  region: string
  address: string
  phone: string
  riskLevel: string
  listType: string
  status: string
  createTime: string
  updateTime: string
}

export interface Qualification {
  id: number
  merchantId: number
  merchantName: string
  qualificationType: string
  qualificationNo: string
  issueDate: string
  expireDate: string
  status: string
  verifyStatus: string
  createTime: string
}

export interface Package {
  id: number
  trackingNo: string
  expressCompany: string
  senderName: string
  senderAddress: string
  receiverName: string
  receiverAddress: string
  status: string
  riskLevel: string
  createTime: string
  updateTime: string
}

export interface TrackingLog {
  id: number
  packageId: number
  trackingNo: string
  status: string
  location: string
  operator: string
  remark: string
  timestamp: string
}

export interface Alert {
  id: number
  alertCode: string
  alertType: string
  alertLevel: string
  source: string
  content: string
  status: string
  handleResult: string
  createTime: string
  updateTime: string
}

export interface BlacklistItem {
  id: number
  itemType: string
  itemName: string
  itemValue: string
  reason: string
  source: string
  status: string
  expireDate: string
  createTime: string
}
