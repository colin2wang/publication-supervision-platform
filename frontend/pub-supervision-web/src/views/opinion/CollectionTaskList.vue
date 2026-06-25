<template>
  <div>
    <a-card :bordered="false" style="margin-bottom: 16px">
      <a-form :model="searchForm" layout="inline">
        <a-row :gutter="16">
          <a-col :span="6"><a-form-item label="任务名称"><a-input v-model:value="searchForm.taskName" placeholder="请输入" allow-clear /></a-form-item></a-col>
          <a-col :span="6"><a-form-item label="平台"><a-select v-model:value="searchForm.platform" placeholder="请选择" allow-clear><a-select-option value="微博">微博</a-select-option><a-select-option value="微信">微信</a-select-option><a-select-option value="抖音">抖音</a-select-option><a-select-option value="知乎">知乎</a-select-option></a-select></a-form-item></a-col>
          <a-col :span="6"><a-form-item label="状态"><a-select v-model:value="searchForm.status" placeholder="请选择" allow-clear><a-select-option value="运行中">运行中</a-select-option><a-select-option value="已暂停">已暂停</a-select-option><a-select-option value="已完成">已完成</a-select-option></a-select></a-form-item></a-col>
          <a-col :span="6"><a-space><a-button type="primary" @click="handleSearch">查询</a-button><a-button @click="handleReset">重置</a-button></a-space></a-col>
        </a-row>
      </a-form>
    </a-card>
    <a-card :bordered="false">
      <template #extra><a-button type="primary" @click="showModal()"><template #icon><PlusOutlined /></template>新建采集任务</a-button></template>
      <a-table :columns="columns" :data-source="dataSource" :loading="loading" :pagination="pagination" @change="handleTableChange" row-key="id">
        <template #bodyCell="{ column, record }">
          <template v-if="column.key === 'status'"><a-tag :color="record.status === '运行中' ? 'processing' : record.status === '已暂停' ? 'warning' : 'success'">{{ record.status }}</a-tag></template>
          <template v-if="column.key === 'actions'">
            <a-space>
              <a-button v-if="record.status === '已暂停'" type="primary" size="small" @click="handleExecute(record)">执行</a-button>
              <a-button v-if="record.status === '运行中'" size="small" @click="handlePause(record)">暂停</a-button>
              <a-popconfirm title="确定删除？" @confirm="handleDelete(record.id)"><a style="color: #ff4d4f">删除</a></a-popconfirm>
            </a-space>
          </template>
        </template>
      </a-table>
    </a-card>
    <a-modal v-model:open="modalVisible" :title="editId ? '编辑采集任务' : '新建采集任务'" @ok="handleSave" :confirm-loading="saveLoading">
      <a-form :model="formData" :label-col="{ span: 6 }" :wrapper-col="{ span: 16 }">
        <a-form-item label="任务名称" required><a-input v-model:value="formData.taskName" /></a-form-item>
        <a-form-item label="平台" required>
          <a-select v-model:value="formData.platform" placeholder="请选择">
            <a-select-option value="微博">微博</a-select-option><a-select-option value="微信">微信</a-select-option><a-select-option value="抖音">抖音</a-select-option><a-select-option value="知乎">知乎</a-select-option>
          </a-select>
        </a-form-item>
        <a-form-item label="关键词"><a-input v-model:value="formData.keywords" placeholder="多个关键词用逗号分隔" /></a-form-item>
        <a-form-item label="采集频率"><a-select v-model:value="formData.schedule"><a-select-option value="hourly">每小时</a-select-option><a-select-option value="daily">每天</a-select-option><a-select-option value="weekly">每周</a-select-option></a-select></a-form-item>
      </a-form>
    </a-modal>
  </div>
</template>

<script setup lang="ts">
import { ref, reactive, onMounted } from 'vue'
import { message } from 'ant-design-vue'
import { PlusOutlined } from '@ant-design/icons-vue'
import { getCollectionTasks, createCollectionTask, executeTask, pauseTask, deleteCollectionTask } from '@/api/opinion'

const loading = ref(false); const saveLoading = ref(false); const modalVisible = ref(false)
const dataSource = ref<any[]>([]); const editId = ref<number | null>(null)
const searchForm = reactive({ taskName: '', platform: undefined as any, status: undefined as any })
const formData = reactive({ taskName: '', platform: undefined as any, keywords: '', schedule: 'daily' })
const pagination = reactive({ current: 1, pageSize: 10, total: 0, showSizeChanger: true, showTotal: (t: number) => `共 ${t} 条` })
const columns = [
  { title: '任务名称', dataIndex: 'taskName', key: 'taskName', width: 180 },
  { title: '平台', dataIndex: 'platform', key: 'platform', width: 100 },
  { title: '关键词', dataIndex: 'keywords', key: 'keywords', width: 200 },
  { title: '采集数量', dataIndex: 'collectedCount', key: 'collectedCount', width: 100 },
  { title: '状态', dataIndex: 'status', key: 'status', width: 100 },
  { title: '最后执行', dataIndex: 'lastExecuteTime', key: 'lastExecuteTime', width: 180 },
  { title: '操作', key: 'actions', width: 180, fixed: 'right' as const }
]

async function fetchData() {
  loading.value = true
  try { const res = await getCollectionTasks({ current: pagination.current, size: pagination.pageSize, ...searchForm }); dataSource.value = res.records; pagination.total = res.total }
  catch { dataSource.value = [] } finally { loading.value = false }
}
function handleSearch() { pagination.current = 1; fetchData() }
function handleReset() { Object.assign(searchForm, { taskName: '', platform: undefined, status: undefined }); handleSearch() }
function handleTableChange(pag: any) { pagination.current = pag.current; pagination.pageSize = pag.pageSize; fetchData() }
function showModal() { Object.assign(formData, { taskName: '', platform: undefined, keywords: '', schedule: 'daily' }); editId.value = null; modalVisible.value = true }
async function handleSave() {
  saveLoading.value = true
  try { await createCollectionTask(formData); message.success('创建成功'); modalVisible.value = false; fetchData() }
  finally { saveLoading.value = false }
}
async function handleExecute(record: any) { await executeTask(record.id); message.success('任务已启动'); fetchData() }
async function handlePause(record: any) { await pauseTask(record.id); message.success('任务已暂停'); fetchData() }
async function handleDelete(id: number) { await deleteCollectionTask(id); message.success('删除成功'); fetchData() }
onMounted(() => fetchData())
</script>
