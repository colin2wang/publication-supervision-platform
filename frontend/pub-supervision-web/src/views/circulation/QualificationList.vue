<template>
  <div>
    <a-card :bordered="false" style="margin-bottom: 16px">
      <a-form :model="searchForm" layout="inline">
        <a-row :gutter="16">
          <a-col :span="6"><a-form-item label="商户名称"><a-input v-model:value="searchForm.merchantName" placeholder="请输入" allow-clear /></a-form-item></a-col>
          <a-col :span="6"><a-form-item label="资质类型"><a-select v-model:value="searchForm.qualificationType" placeholder="请选择" allow-clear><a-select-option value="营业执照">营业执照</a-select-option><a-select-option value="出版许可证">出版许可证</a-select-option><a-select-option value="经营许可证">经营许可证</a-select-option></a-select></a-form-item></a-col>
          <a-col :span="6"><a-form-item label="审核状态"><a-select v-model:value="searchForm.verifyStatus" placeholder="请选择" allow-clear><a-select-option value="待审核">待审核</a-select-option><a-select-option value="已通过">已通过</a-select-option><a-select-option value="已拒绝">已拒绝</a-select-option></a-select></a-form-item></a-col>
          <a-col :span="6"><a-space><a-button type="primary" @click="handleSearch">查询</a-button><a-button @click="handleReset">重置</a-button></a-space></a-col>
        </a-row>
      </a-form>
    </a-card>
    <a-card :bordered="false">
      <a-table :columns="columns" :data-source="dataSource" :loading="loading" :pagination="pagination" @change="handleTableChange" row-key="id">
        <template #bodyCell="{ column, record }">
          <template v-if="column.key === 'verifyStatus'"><a-tag :color="record.verifyStatus === '已通过' ? 'success' : record.verifyStatus === '已拒绝' ? 'error' : 'processing'">{{ record.verifyStatus }}</a-tag></template>
          <template v-if="column.key === 'actions'">
            <a-space>
              <a-button v-if="record.verifyStatus === '待审核'" type="primary" size="small" @click="handleVerify(record)">审核</a-button>
              <span v-else style="color: #999">已审核</span>
            </a-space>
          </template>
        </template>
      </a-table>
    </a-card>
    <a-modal v-model:open="verifyModalVisible" title="资质审核" @ok="handleVerifyConfirm">
      <a-form :model="verifyForm" :label-col="{ span: 6 }" :wrapper-col="{ span: 16 }">
        <a-form-item label="审核结果" required><a-radio-group v-model:value="verifyForm.status"><a-radio value="已通过">通过</a-radio><a-radio value="已拒绝">拒绝</a-radio></a-radio-group></a-form-item>
        <a-form-item label="审核意见"><a-textarea v-model:value="verifyForm.remark" :rows="3" placeholder="请输入审核意见" /></a-form-item>
      </a-form>
    </a-modal>
  </div>
</template>

<script setup lang="ts">
import { ref, reactive, onMounted } from 'vue'
import { message } from 'ant-design-vue'
import request from '@/utils/request'

const loading = ref(false); const verifyModalVisible = ref(false)
const dataSource = ref<any[]>([])
const currentId = ref(0)
const searchForm = reactive({ merchantName: '', qualificationType: undefined as any, verifyStatus: undefined as any })
const verifyForm = reactive({ status: '已通过', remark: '' })
const pagination = reactive({ current: 1, pageSize: 10, total: 0, showSizeChanger: true, showTotal: (t: number) => `共 ${t} 条` })
const columns = [
  { title: '商户名称', dataIndex: 'merchantName', key: 'merchantName', width: 180 },
  { title: '资质类型', dataIndex: 'qualificationType', key: 'qualificationType', width: 120 },
  { title: '资质编号', dataIndex: 'qualificationNo', key: 'qualificationNo', width: 160 },
  { title: '发证日期', dataIndex: 'issueDate', key: 'issueDate', width: 120 },
  { title: '到期日期', dataIndex: 'expireDate', key: 'expireDate', width: 120 },
  { title: '审核状态', dataIndex: 'verifyStatus', key: 'verifyStatus', width: 100 },
  { title: '操作', key: 'actions', width: 120 }
]

async function fetchData() {
  loading.value = true
  try { const res = await request.get('/api/circulation/qualifications', { params: { current: pagination.current, size: pagination.pageSize, ...searchForm } }); dataSource.value = res.data?.records || []; pagination.total = res.data?.total || 0 }
  catch { dataSource.value = [] } finally { loading.value = false }
}
function handleSearch() { pagination.current = 1; fetchData() }
function handleReset() { Object.assign(searchForm, { merchantName: '', qualificationType: undefined, verifyStatus: undefined }); handleSearch() }
function handleTableChange(pag: any) { pagination.current = pag.current; pagination.pageSize = pag.pageSize; fetchData() }
function handleVerify(record: any) { currentId.value = record.id; verifyForm.status = '已通过'; verifyForm.remark = ''; verifyModalVisible.value = true }
async function handleVerifyConfirm() {
  try { await request.post(`/api/circulation/qualifications/${currentId.value}/verify`, verifyForm); message.success('审核成功'); verifyModalVisible.value = false; fetchData() }
  catch { /* ignore */ }
}
onMounted(() => fetchData())
</script>
