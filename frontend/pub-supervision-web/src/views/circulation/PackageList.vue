<template>
  <div>
    <a-card :bordered="false" style="margin-bottom: 16px">
      <a-form :model="searchForm" layout="inline">
        <a-row :gutter="16">
          <a-col :span="6"><a-form-item label="快递单号"><a-input v-model:value="searchForm.trackingNo" placeholder="请输入" allow-clear /></a-form-item></a-col>
          <a-col :span="6"><a-form-item label="快递公司"><a-select v-model:value="searchForm.expressCompany" placeholder="请选择" allow-clear><a-select-option value="顺丰">顺丰</a-select-option><a-select-option value="中通">中通</a-select-option><a-select-option value="圆通">圆通</a-select-option><a-select-option value="韵达">韵达</a-select-option></a-select></a-form-item></a-col>
          <a-col :span="6"><a-form-item label="状态"><a-select v-model:value="searchForm.status" placeholder="请选择" allow-clear><a-select-option value="正常">正常</a-select-option><a-select-option value="可疑">可疑</a-select-option><a-select-option value="已拦截">已拦截</a-select-option></a-select></a-form-item></a-col>
          <a-col :span="6"><a-space><a-button type="primary" @click="handleSearch">查询</a-button><a-button @click="handleReset">重置</a-button></a-space></a-col>
        </a-row>
      </a-form>
    </a-card>
    <a-card :bordered="false">
      <a-table :columns="columns" :data-source="dataSource" :loading="loading" :pagination="pagination" @change="handleTableChange" row-key="id">
        <template #bodyCell="{ column, record }">
          <template v-if="column.key === 'status'"><a-tag :color="record.status === '正常' ? 'success' : record.status === '可疑' ? 'warning' : 'error'">{{ record.status }}</a-tag></template>
          <template v-if="column.key === 'riskLevel'"><a-tag :color="getRiskColor(record.riskLevel)">{{ record.riskLevel }}</a-tag></template>
          <template v-if="column.key === 'actions'">
            <a-space>
              <a @click="handleViewTracking(record)">物流</a>
              <a-popconfirm v-if="record.status !== '已拦截'" title="确定拦截此包裹？" @confirm="handleIntercept(record)">
                <a style="color: #ff4d4f">拦截</a>
              </a-popconfirm>
            </a-space>
          </template>
        </template>
      </a-table>
    </a-card>
    <a-modal v-model:open="trackingVisible" title="物流跟踪" :footer="null" width="600px">
      <a-timeline>
        <a-timeline-item v-for="(log, idx) in trackingLogs" :key="idx" :color="idx === 0 ? 'green' : 'gray'">
          <p>{{ log.status }} - {{ log.location }}</p>
          <p style="color: #999">{{ log.operator }} | {{ log.timestamp }}</p>
          <p v-if="log.remark" style="color: #666">{{ log.remark }}</p>
        </a-timeline-item>
      </a-timeline>
      <a-empty v-if="!trackingLogs.length" description="暂无物流信息" />
    </a-modal>
  </div>
</template>

<script setup lang="ts">
import { ref, reactive, onMounted } from 'vue'
import { message } from 'ant-design-vue'
import { getPackages, getTracking, interceptPackage } from '@/api/circulation'

const loading = ref(false); const trackingVisible = ref(false)
const dataSource = ref<any[]>([]); const trackingLogs = ref<any[]>([])
const searchForm = reactive({ trackingNo: '', expressCompany: undefined as any, status: undefined as any })
const pagination = reactive({ current: 1, pageSize: 10, total: 0, showSizeChanger: true, showTotal: (t: number) => `共 ${t} 条` })
const columns = [
  { title: '快递单号', dataIndex: 'trackingNo', key: 'trackingNo', width: 180 },
  { title: '快递公司', dataIndex: 'expressCompany', key: 'expressCompany', width: 100 },
  { title: '寄件人', dataIndex: 'senderName', key: 'senderName', width: 120 },
  { title: '收件人', dataIndex: 'receiverName', key: 'receiverName', width: 120 },
  { title: '状态', dataIndex: 'status', key: 'status', width: 100 },
  { title: '风险等级', dataIndex: 'riskLevel', key: 'riskLevel', width: 100 },
  { title: '操作', key: 'actions', width: 140, fixed: 'right' as const }
]
function getRiskColor(l: string) { return { '高': 'red', '中': 'orange', '低': 'green' }[l] || 'default' }
async function fetchData() {
  loading.value = true
  try { const res = await getPackages({ current: pagination.current, size: pagination.pageSize, ...searchForm }); dataSource.value = res.records; pagination.total = res.total }
  catch { dataSource.value = [] } finally { loading.value = false }
}
function handleSearch() { pagination.current = 1; fetchData() }
function handleReset() { Object.assign(searchForm, { trackingNo: '', expressCompany: undefined, status: undefined }); handleSearch() }
function handleTableChange(pag: any) { pagination.current = pag.current; pagination.pageSize = pag.pageSize; fetchData() }
async function handleViewTracking(record: any) {
  try { const res = await getTracking(record.id); trackingLogs.value = (res as any).data || []; trackingVisible.value = true }
  catch { trackingLogs.value = [] }
}
async function handleIntercept(record: any) {
  try { await interceptPackage(record.id, { reason: '人工拦截' }); message.success('拦截成功'); fetchData() }
  catch { /* ignore */ }
}
onMounted(() => fetchData())
</script>
