<template>
  <div>
    <a-card :bordered="false" style="margin-bottom: 16px">
      <a-form :model="searchForm" layout="inline">
        <a-row :gutter="16">
          <a-col :span="6"><a-form-item label="事件标题"><a-input v-model:value="searchForm.eventTitle" placeholder="请输入" allow-clear /></a-form-item></a-col>
          <a-col :span="6"><a-form-item label="事件类型"><a-select v-model:value="searchForm.eventType" placeholder="请选择" allow-clear><a-select-option value="舆情事件">舆情事件</a-select-option><a-select-option value="违规事件">违规事件</a-select-option><a-select-option value="安全事件">安全事件</a-select-option></a-select></a-form-item></a-col>
          <a-col :span="6"><a-form-item label="状态"><a-select v-model:value="searchForm.status" placeholder="请选择" allow-clear><a-select-option value="待处理">待处理</a-select-option><a-select-option value="处理中">处理中</a-select-option><a-select-option value="已关闭">已关闭</a-select-option></a-select></a-form-item></a-col>
          <a-col :span="6"><a-space><a-button type="primary" @click="handleSearch">查询</a-button><a-button @click="handleReset">重置</a-button></a-space></a-col>
        </a-row>
      </a-form>
    </a-card>
    <a-card :bordered="false">
      <template #extra><a-button type="primary" @click="showModal()"><template #icon><PlusOutlined /></template>新建事件</a-button></template>
      <a-table :columns="columns" :data-source="dataSource" :loading="loading" :pagination="pagination" @change="handleTableChange" row-key="id">
        <template #bodyCell="{ column, record }">
          <template v-if="column.key === 'eventLevel'"><a-tag :color="record.eventLevel === '紧急' ? 'red' : record.eventLevel === '重要' ? 'orange' : 'blue'">{{ record.eventLevel }}</a-tag></template>
          <template v-if="column.key === 'status'"><a-tag :color="record.status === '待处理' ? 'warning' : record.status === '处理中' ? 'processing' : 'success'">{{ record.status }}</a-tag></template>
        </template>
      </a-table>
    </a-card>
    <a-modal v-model:open="modalVisible" title="新建事件" @ok="handleSave" :confirm-loading="saveLoading">
      <a-form :model="formData" :label-col="{ span: 6 }" :wrapper-col="{ span: 16 }">
        <a-form-item label="事件标题" required><a-input v-model:value="formData.eventTitle" /></a-form-item>
        <a-form-item label="事件类型" required><a-select v-model:value="formData.eventType"><a-select-option value="舆情事件">舆情事件</a-select-option><a-select-option value="违规事件">违规事件</a-select-option><a-select-option value="安全事件">安全事件</a-select-option></a-select></a-form-item>
        <a-form-item label="紧急程度" required><a-select v-model:value="formData.eventLevel"><a-select-option value="紧急">紧急</a-select-option><a-select-option value="重要">重要</a-select-option><a-select-option value="一般">一般</a-select-option></a-select></a-form-item>
        <a-form-item label="描述"><a-textarea v-model:value="formData.description" :rows="4" /></a-form-item>
      </a-form>
    </a-modal>
  </div>
</template>

<script setup lang="ts">
import { ref, reactive, onMounted } from 'vue'
import { message } from 'ant-design-vue'
import { PlusOutlined } from '@ant-design/icons-vue'
import { getEvents, createEvent } from '@/api/opinion'

const loading = ref(false); const saveLoading = ref(false); const modalVisible = ref(false)
const dataSource = ref<any[]>([])
const searchForm = reactive({ eventTitle: '', eventType: undefined as any, status: undefined as any })
const formData = reactive({ eventTitle: '', eventType: undefined as any, eventLevel: undefined as any, description: '' })
const pagination = reactive({ current: 1, pageSize: 10, total: 0, showSizeChanger: true, showTotal: (t: number) => `共 ${t} 条` })
const columns = [
  { title: '事件编号', dataIndex: 'eventCode', key: 'eventCode', width: 140 },
  { title: '事件标题', dataIndex: 'eventTitle', key: 'eventTitle', width: 200 },
  { title: '事件类型', dataIndex: 'eventType', key: 'eventType', width: 100 },
  { title: '紧急程度', dataIndex: 'eventLevel', key: 'eventLevel', width: 100 },
  { title: '状态', dataIndex: 'status', key: 'status', width: 100 },
  { title: '创建时间', dataIndex: 'createTime', key: 'createTime', width: 180 }
]
async function fetchData() {
  loading.value = true
  try { const res = await getEvents({ current: pagination.current, size: pagination.pageSize, ...searchForm }); dataSource.value = res.records; pagination.total = res.total }
  catch { dataSource.value = [] } finally { loading.value = false }
}
function handleSearch() { pagination.current = 1; fetchData() }
function handleReset() { Object.assign(searchForm, { eventTitle: '', eventType: undefined, status: undefined }); handleSearch() }
function handleTableChange(pag: any) { pagination.current = pag.current; pagination.pageSize = pag.pageSize; fetchData() }
function showModal() { Object.assign(formData, { eventTitle: '', eventType: undefined, eventLevel: undefined, description: '' }); modalVisible.value = true }
async function handleSave() {
  saveLoading.value = true
  try { await createEvent(formData); message.success('创建成功'); modalVisible.value = false; fetchData() }
  finally { saveLoading.value = false }
}
onMounted(() => fetchData())
</script>
