<template>
  <div>
    <a-card :bordered="false" style="margin-bottom: 16px">
      <a-form :model="searchForm" layout="inline">
        <a-row :gutter="16">
          <a-col :span="6"><a-form-item label="报告标题"><a-input v-model:value="searchForm.reportTitle" placeholder="请输入" allow-clear /></a-form-item></a-col>
          <a-col :span="6"><a-form-item label="报告类型"><a-select v-model:value="searchForm.reportType" placeholder="请选择" allow-clear><a-select-option value="日报">日报</a-select-option><a-select-option value="周报">周报</a-select-option><a-select-option value="月报">月报</a-select-option></a-select></a-form-item></a-col>
          <a-col :span="6"><a-form-item label="状态"><a-select v-model:value="searchForm.status" placeholder="请选择" allow-clear><a-select-option value="草稿">草稿</a-select-option><a-select-option value="已生成">已生成</a-select-option><a-select-option value="已审核">已审核</a-select-option></a-select></a-form-item></a-col>
          <a-col :span="6"><a-space><a-button type="primary" @click="handleSearch">查询</a-button><a-button @click="handleReset">重置</a-button></a-space></a-col>
        </a-row>
      </a-form>
    </a-card>
    <a-card :bordered="false">
      <template #extra><a-button type="primary" @click="showGenerateModal"><template #icon><PlusOutlined /></template>生成报告</a-button></template>
      <a-table :columns="columns" :data-source="dataSource" :loading="loading" :pagination="pagination" @change="handleTableChange" row-key="id">
        <template #bodyCell="{ column, record }">
          <template v-if="column.key === 'status'"><a-tag :color="record.status === '已审核' ? 'success' : record.status === '已生成' ? 'processing' : 'default'">{{ record.status }}</a-tag></template>
          <template v-if="column.key === 'actions'">
            <a-space>
              <a @click="handleDownload(record)">下载</a>
              <a-button v-if="record.status === '已生成'" type="primary" size="small" @click="handleReview(record)">审核</a-button>
            </a-space>
          </template>
        </template>
      </a-table>
    </a-card>
    <a-modal v-model:open="generateModalVisible" title="生成报告" @ok="handleGenerate" :confirm-loading="generateLoading">
      <a-form :model="generateForm" :label-col="{ span: 6 }" :wrapper-col="{ span: 16 }">
        <a-form-item label="报告类型" required><a-select v-model:value="generateForm.reportType"><a-select-option value="日报">日报</a-select-option><a-select-option value="周报">周报</a-select-option><a-select-option value="月报">月报</a-select-option></a-select></a-form-item>
        <a-form-item label="周期" required><a-input v-model:value="generateForm.period" placeholder="如: 2024-01-01 ~ 2024-01-07" /></a-form-item>
      </a-form>
    </a-modal>
    <a-modal v-model:open="reviewModalVisible" title="审核报告" @ok="handleReviewConfirm" :confirm-loading="reviewLoading">
      <a-form :model="reviewForm" :label-col="{ span: 6 }" :wrapper-col="{ span: 16 }">
        <a-form-item label="审核结果"><a-radio-group v-model:value="reviewForm.reviewStatus"><a-radio value="已审核">通过</a-radio><a-radio value="驳回">驳回</a-radio></a-radio-group></a-form-item>
      </a-form>
    </a-modal>
  </div>
</template>

<script setup lang="ts">
import { ref, reactive, onMounted } from 'vue'
import { message } from 'ant-design-vue'
import { PlusOutlined } from '@ant-design/icons-vue'
import { getReports, generateReport, downloadReport, reviewReport } from '@/api/opinion'
import { downloadFile } from '@/utils'

const loading = ref(false); const generateLoading = ref(false); const reviewLoading = ref(false)
const generateModalVisible = ref(false); const reviewModalVisible = ref(false)
const dataSource = ref<any[]>([]); const currentId = ref(0)
const searchForm = reactive({ reportTitle: '', reportType: undefined as any, status: undefined as any })
const generateForm = reactive({ reportType: undefined as any, period: '' })
const reviewForm = reactive({ reviewStatus: '已审核' })
const pagination = reactive({ current: 1, pageSize: 10, total: 0, showSizeChanger: true, showTotal: (t: number) => `共 ${t} 条` })
const columns = [
  { title: '报告标题', dataIndex: 'reportTitle', key: 'reportTitle', width: 200 },
  { title: '报告类型', dataIndex: 'reportType', key: 'reportType', width: 100 },
  { title: '周期', dataIndex: 'period', key: 'period', width: 160 },
  { title: '状态', dataIndex: 'status', key: 'status', width: 100 },
  { title: '生成时间', dataIndex: 'generatedTime', key: 'generatedTime', width: 180 },
  { title: '操作', key: 'actions', width: 140, fixed: 'right' as const }
]
async function fetchData() {
  loading.value = true
  try { const res = await getReports({ current: pagination.current, size: pagination.pageSize, ...searchForm }); dataSource.value = res.records; pagination.total = res.total }
  catch { dataSource.value = [] } finally { loading.value = false }
}
function handleSearch() { pagination.current = 1; fetchData() }
function handleReset() { Object.assign(searchForm, { reportTitle: '', reportType: undefined, status: undefined }); handleSearch() }
function handleTableChange(pag: any) { pagination.current = pag.current; pagination.pageSize = pag.pageSize; fetchData() }
function showGenerateModal() { generateForm.reportType = undefined; generateForm.period = ''; generateModalVisible.value = true }
async function handleGenerate() {
  generateLoading.value = true
  try { await generateReport(generateForm); message.success('报告生成中...'); generateModalVisible.value = false; fetchData() }
  finally { generateLoading.value = false }
}
async function handleDownload(record: any) {
  try { const res = await downloadReport(record.id); downloadFile(res as any, `${record.reportTitle}.pdf`) }
  catch { message.error('下载失败') }
}
function handleReview(record: any) { currentId.value = record.id; reviewForm.reviewStatus = '已审核'; reviewModalVisible.value = true }
async function handleReviewConfirm() {
  reviewLoading.value = true
  try { await reviewReport(currentId.value, reviewForm); message.success('审核成功'); reviewModalVisible.value = false; fetchData() }
  finally { reviewLoading.value = false }
}
onMounted(() => fetchData())
</script>
