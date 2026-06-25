<template>
  <div>
    <a-card :bordered="false">
      <template #extra>
        <a-button type="primary" @click="showModal()">
          <template #icon><PlusOutlined /></template>
          注册模型
        </a-button>
      </template>
      <a-table :columns="columns" :data-source="dataSource" :loading="loading" :pagination="pagination" @change="handleTableChange" row-key="id">
        <template #bodyCell="{ column, record }">
          <template v-if="column.key === 'status'"><a-tag :color="record.status === '正常' ? 'success' : 'error'">{{ record.status }}</a-tag></template>
          <template v-if="column.key === 'actions'">
            <a-popconfirm title="确定删除？" @confirm="handleDelete(record.id)"><a style="color: #ff4d4f">删除</a></a-popconfirm>
          </template>
        </template>
      </a-table>
    </a-card>
    <a-modal v-model:open="modalVisible" title="注册模型" @ok="handleSave" :confirm-loading="saveLoading">
      <a-form :model="formData" :label-col="{ span: 6 }" :wrapper-col="{ span: 16 }">
        <a-form-item label="模型名称" required><a-input v-model:value="formData.modelName" /></a-form-item>
        <a-form-item label="模型类型" required>
          <a-select v-model:value="formData.modelType">
            <a-select-option value="LLM">LLM</a-select-option>
            <a-select-option value="Embedding">Embedding</a-select-option>
            <a-select-option value="Rerank">Rerank</a-select-option>
          </a-select>
        </a-form-item>
        <a-form-item label="API地址" required><a-input v-model:value="formData.apiEndpoint" /></a-form-item>
        <a-form-item label="API Key"><a-input-password v-model:value="formData.apiKey" /></a-form-item>
        <a-form-item label="描述"><a-textarea v-model:value="formData.description" :rows="3" /></a-form-item>
      </a-form>
    </a-modal>
  </div>
</template>

<script setup lang="ts">
import { ref, reactive, onMounted } from 'vue'
import { message } from 'ant-design-vue'
import { PlusOutlined } from '@ant-design/icons-vue'
import { getModels, registerModel, deleteModel } from '@/api/ai'

const loading = ref(false); const saveLoading = ref(false); const modalVisible = ref(false)
const dataSource = ref<any[]>([])
const formData = reactive({ modelName: '', modelType: undefined as string | undefined, apiEndpoint: '', apiKey: '', description: '' })
const pagination = reactive({ current: 1, pageSize: 10, total: 0, showSizeChanger: true, showTotal: (t: number) => `共 ${t} 条` })
const columns = [
  { title: '模型名称', dataIndex: 'modelName', key: 'modelName', width: 180 },
  { title: '模型类型', dataIndex: 'modelType', key: 'modelType', width: 100 },
  { title: 'API地址', dataIndex: 'apiEndpoint', key: 'apiEndpoint', width: 250 },
  { title: '状态', dataIndex: 'status', key: 'status', width: 100 },
  { title: '注册时间', dataIndex: 'createTime', key: 'createTime', width: 180 },
  { title: '操作', key: 'actions', width: 100 }
]
async function fetchData() {
  loading.value = true
  try { const res = await getModels({ current: pagination.current, size: pagination.pageSize }); dataSource.value = res.records; pagination.total = res.total }
  catch { dataSource.value = [] } finally { loading.value = false }
}
function handleTableChange(pag: any) { pagination.current = pag.current; pagination.pageSize = pag.pageSize; fetchData() }
function showModal() { Object.assign(formData, { modelName: '', modelType: undefined, apiEndpoint: '', apiKey: '', description: '' }); modalVisible.value = true }
async function handleSave() {
  saveLoading.value = true
  try { await registerModel(formData); message.success('注册成功'); modalVisible.value = false; fetchData() }
  finally { saveLoading.value = false }
}
async function handleDelete(id: number) { await deleteModel(id); message.success('删除成功'); fetchData() }
onMounted(() => fetchData())
</script>
