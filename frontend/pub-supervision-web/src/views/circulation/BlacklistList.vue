<template>
  <div>
    <a-card :bordered="false">
      <a-tabs v-model:activeKey="activeTab" @change="handleTabChange">
        <a-tab-pane key="全部" tab="全部" />
        <a-tab-pane key="白名单" tab="白名单" />
        <a-tab-pane key="灰名单" tab="灰名单" />
        <a-tab-pane key="黑名单" tab="黑名单" />
      </a-tabs>

      <template #extra>
        <a-button type="primary" @click="showModal()">
          <template #icon><PlusOutlined /></template>
          添加名单
        </a-button>
      </template>

      <a-table :columns="columns" :data-source="dataSource" :loading="loading" :pagination="pagination" @change="handleTableChange" row-key="id">
        <template #bodyCell="{ column, record }">
          <template v-if="column.key === 'itemType'">
            <a-tag :color="record.itemType === '白名单' ? 'success' : record.itemType === '黑名单' ? 'error' : 'default'">{{ record.itemType }}</a-tag>
          </template>
          <template v-if="column.key === 'actions'">
            <a-popconfirm title="确定移除？" @confirm="handleRemove(record.id)">
              <a style="color: #ff4d4f">移除</a>
            </a-popconfirm>
          </template>
        </template>
      </a-table>
    </a-card>

    <a-modal v-model:open="modalVisible" title="添加名单" @ok="handleSave" :confirm-loading="saveLoading">
      <a-form :model="formData" :label-col="{ span: 6 }" :wrapper-col="{ span: 16 }">
        <a-form-item label="名单类型" required>
          <a-select v-model:value="formData.itemType" placeholder="请选择">
            <a-select-option value="白名单">白名单</a-select-option>
            <a-select-option value="灰名单">灰名单</a-select-option>
            <a-select-option value="黑名单">黑名单</a-select-option>
          </a-select>
        </a-form-item>
        <a-form-item label="名称" required><a-input v-model:value="formData.itemName" placeholder="请输入名称" /></a-form-item>
        <a-form-item label="值" required><a-input v-model:value="formData.itemValue" placeholder="请输入值" /></a-form-item>
        <a-form-item label="原因"><a-textarea v-model:value="formData.reason" :rows="3" placeholder="请输入原因" /></a-form-item>
      </a-form>
    </a-modal>
  </div>
</template>

<script setup lang="ts">
import { ref, reactive, onMounted } from 'vue'
import { message } from 'ant-design-vue'
import { PlusOutlined } from '@ant-design/icons-vue'
import { getLists, addToList, removeFromList } from '@/api/circulation'

const loading = ref(false); const saveLoading = ref(false); const modalVisible = ref(false)
const dataSource = ref<any[]>([])
const activeTab = ref('全部')
const searchForm = reactive({ itemType: undefined as string | undefined })
const formData = reactive({ itemType: undefined as string | undefined, itemName: '', itemValue: '', reason: '' })
const pagination = reactive({ current: 1, pageSize: 10, total: 0, showSizeChanger: true, showTotal: (t: number) => `共 ${t} 条` })
const columns = [
  { title: '名单类型', dataIndex: 'itemType', key: 'itemType', width: 100 },
  { title: '名称', dataIndex: 'itemName', key: 'itemName', width: 180 },
  { title: '值', dataIndex: 'itemValue', key: 'itemValue', width: 200 },
  { title: '原因', dataIndex: 'reason', key: 'reason', width: 200 },
  { title: '来源', dataIndex: 'source', key: 'source', width: 120 },
  { title: '创建时间', dataIndex: 'createTime', key: 'createTime', width: 180 },
  { title: '操作', key: 'actions', width: 100, fixed: 'right' as const }
]

async function fetchData() {
  loading.value = true
  try { const res = await getLists({ current: pagination.current, size: pagination.pageSize, ...searchForm }); dataSource.value = res.records; pagination.total = res.total }
  catch { dataSource.value = [] } finally { loading.value = false }
}
function handleSearch() { pagination.current = 1; fetchData() }
function handleTabChange(key: string) { searchForm.itemType = key === '全部' ? undefined : key; handleSearch() }
function handleTableChange(pag: any) { pagination.current = pag.current; pagination.pageSize = pag.pageSize; fetchData() }
function showModal() { Object.assign(formData, { itemType: undefined, itemName: '', itemValue: '', reason: '' }); modalVisible.value = true }
async function handleSave() {
  saveLoading.value = true
  try { await addToList(formData); message.success('添加成功'); modalVisible.value = false; fetchData() }
  finally { saveLoading.value = false }
}
async function handleRemove(id: number) { await removeFromList(id); message.success('移除成功'); fetchData() }
onMounted(() => fetchData())
</script>
