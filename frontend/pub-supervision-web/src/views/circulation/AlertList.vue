<template>
  <div>
    <a-card :bordered="false">
      <a-tabs v-model:activeKey="activeTab" @change="handleTabChange">
        <a-tab-pane key="全部" tab="全部" />
        <a-tab-pane key="红色" tab="红色预警">
          <template #tab><a-badge status="error" text="红色预警" /></template>
        </a-tab-pane>
        <a-tab-pane key="橙色" tab="橙色预警">
          <template #tab><a-badge status="warning" text="橙色预警" /></template>
        </a-tab-pane>
        <a-tab-pane key="黄色" tab="黄色预警">
          <template #tab><a-badge status="processing" text="黄色预警" /></template>
        </a-tab-pane>
        <a-tab-pane key="蓝色" tab="蓝色预警">
          <template #tab><a-badge status="default" text="蓝色预警" /></template>
        </a-tab-pane>
      </a-tabs>

      <a-table :columns="columns" :data-source="dataSource" :loading="loading" :pagination="pagination" @change="handleTableChange" row-key="id">
        <template #bodyCell="{ column, record }">
          <template v-if="column.key === 'alertLevel'">
            <a-tag :color="getLevelColor(record.alertLevel)">{{ record.alertLevel }}</a-tag>
          </template>
          <template v-if="column.key === 'status'">
            <a-tag :color="record.status === '待处理' ? 'warning' : record.status === '已处理' ? 'success' : 'default'">{{ record.status }}</a-tag>
          </template>
          <template v-if="column.key === 'actions'">
            <a-space>
              <a @click="handleView(record)">查看</a>
              <a-button v-if="record.status === '待处理'" type="primary" size="small" @click="handleProcess(record)">处理</a-button>
            </a-space>
          </template>
        </template>
      </a-table>
    </a-card>

    <a-modal v-model:open="detailVisible" title="预警详情" :footer="null" width="600px">
      <a-descriptions :column="2" bordered>
        <a-descriptions-item label="预警编号">{{ currentAlert?.alertCode }}</a-descriptions-item>
        <a-descriptions-item label="预警类型">{{ currentAlert?.alertType }}</a-descriptions-item>
        <a-descriptions-item label="预警等级"><a-tag :color="getLevelColor(currentAlert?.alertLevel)">{{ currentAlert?.alertLevel }}</a-tag></a-descriptions-item>
        <a-descriptions-item label="状态">{{ currentAlert?.status }}</a-descriptions-item>
        <a-descriptions-item label="来源" :span="2">{{ currentAlert?.source }}</a-descriptions-item>
        <a-descriptions-item label="内容" :span="2">{{ currentAlert?.content }}</a-descriptions-item>
        <a-descriptions-item label="处理结果" :span="2">{{ currentAlert?.handleResult || '待处理' }}</a-descriptions-item>
      </a-descriptions>
    </a-modal>

    <a-modal v-model:open="processModalVisible" title="处理预警" @ok="handleProcessConfirm" :confirm-loading="processLoading">
      <a-form :model="processForm" :label-col="{ span: 6 }" :wrapper-col="{ span: 16 }">
        <a-form-item label="处理结果" required><a-textarea v-model:value="processForm.handleResult" :rows="4" placeholder="请输入处理结果" /></a-form-item>
      </a-form>
    </a-modal>
  </div>
</template>

<script setup lang="ts">
import { ref, reactive, onMounted } from 'vue'
import { message } from 'ant-design-vue'
import { getAlerts, getAlert, handleAlert } from '@/api/circulation'

const loading = ref(false); const processLoading = ref(false)
const detailVisible = ref(false); const processModalVisible = ref(false)
const dataSource = ref<any[]>([]); const currentAlert = ref<any>(null); const currentId = ref(0)
const activeTab = ref('全部')
const searchForm = reactive({ alertLevel: undefined as string | undefined })
const processForm = reactive({ handleResult: '' })
const pagination = reactive({ current: 1, pageSize: 10, total: 0, showSizeChanger: true, showTotal: (t: number) => `共 ${t} 条` })
const columns = [
  { title: '预警编号', dataIndex: 'alertCode', key: 'alertCode', width: 160 },
  { title: '预警类型', dataIndex: 'alertType', key: 'alertType', width: 120 },
  { title: '预警等级', dataIndex: 'alertLevel', key: 'alertLevel', width: 100 },
  { title: '来源', dataIndex: 'source', key: 'source', width: 140 },
  { title: '状态', dataIndex: 'status', key: 'status', width: 100 },
  { title: '创建时间', dataIndex: 'createTime', key: 'createTime', width: 180 },
  { title: '操作', key: 'actions', width: 140, fixed: 'right' as const }
]
function getLevelColor(l?: string) { return { '红色': 'red', '橙色': 'orange', '黄色': 'gold', '蓝色': 'blue' }[l || ''] || 'default' }
async function fetchData() {
  loading.value = true
  try { const res = await getAlerts({ current: pagination.current, size: pagination.pageSize, ...searchForm }); dataSource.value = res.records; pagination.total = res.total }
  catch { dataSource.value = [] } finally { loading.value = false }
}
function handleSearch() { pagination.current = 1; fetchData() }
function handleTabChange(key: string) { searchForm.alertLevel = key === '全部' ? undefined : key; handleSearch() }
function handleTableChange(pag: any) { pagination.current = pag.current; pagination.pageSize = pag.pageSize; fetchData() }
async function handleView(record: any) { try { const res = await getAlert(record.id); currentAlert.value = res; detailVisible.value = true } catch { /* ignore */ } }
function handleProcess(record: any) { currentId.value = record.id; processForm.handleResult = ''; processModalVisible.value = true }
async function handleProcessConfirm() {
  processLoading.value = true
  try { await handleAlert(currentId.value, processForm); message.success('处理成功'); processModalVisible.value = false; fetchData() }
  finally { processLoading.value = false }
}
onMounted(() => fetchData())
</script>
