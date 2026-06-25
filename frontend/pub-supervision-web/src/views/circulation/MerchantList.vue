<template>
  <div>
    <a-card :bordered="false" style="margin-bottom: 16px">
      <a-form :model="searchForm" layout="inline">
        <a-row :gutter="16">
          <a-col :span="5"><a-form-item label="商户名称"><a-input v-model:value="searchForm.merchantName" placeholder="请输入" allow-clear /></a-form-item></a-col>
          <a-col :span="5"><a-form-item label="类型"><a-select v-model:value="searchForm.merchantType" placeholder="请选择" allow-clear><a-select-option value="出版社">出版社</a-select-option><a-select-option value="书店">书店</a-select-option><a-select-option value="电商平台">电商平台</a-select-option></a-select></a-form-item></a-col>
          <a-col :span="5"><a-form-item label="风险等级"><a-select v-model:value="searchForm.riskLevel" placeholder="请选择" allow-clear><a-select-option value="高">高</a-select-option><a-select-option value="中">中</a-select-option><a-select-option value="低">低</a-select-option></a-select></a-form-item></a-col>
          <a-col :span="5"><a-form-item label="名单类型"><a-select v-model:value="searchForm.listType" placeholder="请选择" allow-clear><a-select-option value="白名单">白名单</a-select-option><a-select-option value="黑名单">黑名单</a-select-option></a-select></a-form-item></a-col>
          <a-col :span="4"><a-space><a-button type="primary" @click="handleSearch">查询</a-button><a-button @click="handleReset">重置</a-button></a-space></a-col>
        </a-row>
      </a-form>
    </a-card>
    <a-card :bordered="false">
      <template #extra><a-button type="primary" @click="showModal()"><template #icon><PlusOutlined /></template>新增商户</a-button></template>
      <a-table :columns="columns" :data-source="dataSource" :loading="loading" :pagination="pagination" @change="handleTableChange" row-key="id">
        <template #bodyCell="{ column, record }">
          <template v-if="column.key === 'riskLevel'"><a-tag :color="getRiskColor(record.riskLevel)">{{ record.riskLevel }}</a-tag></template>
          <template v-if="column.key === 'listType'"><a-tag :color="record.listType === '白名单' ? 'success' : record.listType === '黑名单' ? 'error' : 'default'">{{ record.listType || '未分类' }}</a-tag></template>
          <template v-if="column.key === 'actions'">
            <a-space><a @click="showModal(record)">编辑</a><a-popconfirm title="确定删除？" @confirm="handleDelete(record.id)"><a style="color: #ff4d4f">删除</a></a-popconfirm></a-space>
          </template>
        </template>
      </a-table>
    </a-card>
    <a-modal v-model:open="modalVisible" :title="editId ? '编辑商户' : '新增商户'" @ok="handleSave" :confirm-loading="saveLoading">
      <a-form :model="formData" :label-col="{ span: 6 }" :wrapper-col="{ span: 16 }">
        <a-form-item label="商户名称" required><a-input v-model:value="formData.merchantName" /></a-form-item>
        <a-form-item label="类型" required><a-select v-model:value="formData.merchantType"><a-select-option value="出版社">出版社</a-select-option><a-select-option value="书店">书店</a-select-option><a-select-option value="电商平台">电商平台</a-select-option></a-select></a-form-item>
        <a-form-item label="法人"><a-input v-model:value="formData.legalPerson" /></a-form-item>
        <a-form-item label="地区"><a-input v-model:value="formData.region" /></a-form-item>
        <a-form-item label="联系电话"><a-input v-model:value="formData.phone" /></a-form-item>
        <a-form-item label="地址"><a-textarea v-model:value="formData.address" :rows="2" /></a-form-item>
      </a-form>
    </a-modal>
  </div>
</template>

<script setup lang="ts">
import { ref, reactive, onMounted } from 'vue'
import { message } from 'ant-design-vue'
import { PlusOutlined } from '@ant-design/icons-vue'
import { getMerchants, createMerchant, updateMerchant, deleteMerchant } from '@/api/circulation'

const loading = ref(false); const saveLoading = ref(false); const modalVisible = ref(false)
const dataSource = ref<any[]>([]); const editId = ref<number | null>(null)
const searchForm = reactive({ merchantName: '', merchantType: undefined as any, riskLevel: undefined as any, listType: undefined as any })
const formData = reactive({ merchantName: '', merchantType: undefined as any, legalPerson: '', region: '', phone: '', address: '' })
const pagination = reactive({ current: 1, pageSize: 10, total: 0, showSizeChanger: true, showTotal: (t: number) => `共 ${t} 条` })
const columns = [
  { title: '商户名称', dataIndex: 'merchantName', key: 'merchantName', width: 180 },
  { title: '类型', dataIndex: 'merchantType', key: 'merchantType', width: 100 },
  { title: '法人', dataIndex: 'legalPerson', key: 'legalPerson', width: 100 },
  { title: '地区', dataIndex: 'region', key: 'region', width: 120 },
  { title: '风险等级', dataIndex: 'riskLevel', key: 'riskLevel', width: 100 },
  { title: '名单类型', dataIndex: 'listType', key: 'listType', width: 100 },
  { title: '操作', key: 'actions', width: 120, fixed: 'right' as const }
]
function getRiskColor(l: string) { return { '高': 'red', '中': 'orange', '低': 'green' }[l] || 'default' }
async function fetchData() {
  loading.value = true
  try { const res = await getMerchants({ current: pagination.current, size: pagination.pageSize, ...searchForm }); dataSource.value = res.records; pagination.total = res.total }
  catch { dataSource.value = [] } finally { loading.value = false }
}
function handleSearch() { pagination.current = 1; fetchData() }
function handleReset() { Object.assign(searchForm, { merchantName: '', merchantType: undefined, riskLevel: undefined, listType: undefined }); handleSearch() }
function handleTableChange(pag: any) { pagination.current = pag.current; pagination.pageSize = pag.pageSize; fetchData() }
function showModal(record?: any) {
  editId.value = record?.id || null
  Object.assign(formData, record ? { merchantName: record.merchantName, merchantType: record.merchantType, legalPerson: record.legalPerson, region: record.region, phone: record.phone, address: record.address } : { merchantName: '', merchantType: undefined, legalPerson: '', region: '', phone: '', address: '' })
  modalVisible.value = true
}
async function handleSave() {
  saveLoading.value = true
  try { if (editId.value) await updateMerchant(editId.value, formData); else await createMerchant(formData); message.success('保存成功'); modalVisible.value = false; fetchData() }
  finally { saveLoading.value = false }
}
async function handleDelete(id: number) { await deleteMerchant(id); message.success('删除成功'); fetchData() }
onMounted(() => fetchData())
</script>
