<template>
  <div>
    <a-card :bordered="false" style="margin-bottom: 16px">
      <a-form :model="searchForm" layout="inline">
        <a-row :gutter="16">
          <a-col :span="6">
            <a-form-item label="样本编码">
              <a-input v-model:value="searchForm.sampleCode" placeholder="请输入" allow-clear />
            </a-form-item>
          </a-col>
          <a-col :span="6">
            <a-form-item label="分类">
              <a-select v-model:value="searchForm.category" placeholder="请选择" allow-clear>
                <a-select-option value="图书">图书</a-select-option>
                <a-select-option value="期刊">期刊</a-select-option>
                <a-select-option value="报纸">报纸</a-select-option>
              </a-select>
            </a-form-item>
          </a-col>
          <a-col :span="6">
            <a-form-item label="来源">
              <a-select v-model:value="searchForm.source" placeholder="请选择" allow-clear>
                <a-select-option value="自主采录">自主采录</a-select-option>
                <a-select-option value="系统导入">系统导入</a-select-option>
              </a-select>
            </a-form-item>
          </a-col>
          <a-col :span="6">
            <a-space>
              <a-button type="primary" @click="handleSearch">查询</a-button>
              <a-button @click="handleReset">重置</a-button>
            </a-space>
          </a-col>
        </a-row>
      </a-form>
    </a-card>

    <a-card :bordered="false">
      <template #extra>
        <a-button type="primary" @click="showModal()">
          <template #icon><PlusOutlined /></template>
          新增样本
        </a-button>
      </template>
      <a-table
        :columns="columns"
        :data-source="dataSource"
        :loading="loading"
        :pagination="pagination"
        @change="handleTableChange"
        row-key="id"
      >
        <template #bodyCell="{ column, record }">
          <template v-if="column.key === 'actions'">
            <a-space>
              <a @click="showModal(record)">编辑</a>
              <a-popconfirm title="确定删除？" @confirm="handleDelete(record.id)">
                <a style="color: #ff4d4f">删除</a>
              </a-popconfirm>
            </a-space>
          </template>
        </template>
      </a-table>
    </a-card>

    <a-modal v-model:open="modalVisible" :title="editId ? '编辑样本' : '新增样本'" @ok="handleSave" :confirm-loading="saveLoading">
      <a-form :model="formData" :label-col="{ span: 6 }" :wrapper-col="{ span: 16 }">
        <a-form-item label="样本名称" required><a-input v-model:value="formData.sampleName" /></a-form-item>
        <a-form-item label="分类" required>
          <a-select v-model:value="formData.category" placeholder="请选择">
            <a-select-option value="图书">图书</a-select-option>
            <a-select-option value="期刊">期刊</a-select-option>
            <a-select-option value="报纸">报纸</a-select-option>
          </a-select>
        </a-form-item>
        <a-form-item label="来源">
          <a-select v-model:value="formData.source" placeholder="请选择">
            <a-select-option value="自主采录">自主采录</a-select-option>
            <a-select-option value="系统导入">系统导入</a-select-option>
          </a-select>
        </a-form-item>
        <a-form-item label="描述"><a-textarea v-model:value="formData.description" :rows="3" /></a-form-item>
      </a-form>
    </a-modal>
  </div>
</template>

<script setup lang="ts">
import { ref, reactive, onMounted } from 'vue'
import { message } from 'ant-design-vue'
import { PlusOutlined } from '@ant-design/icons-vue'
import { getSamples, createSample, deleteSample } from '@/api/identification'

const loading = ref(false)
const saveLoading = ref(false)
const modalVisible = ref(false)
const dataSource = ref<any[]>([])
const editId = ref<number | null>(null)
const searchForm = reactive({ sampleCode: '', category: undefined as string | undefined, source: undefined as string | undefined })
const formData = reactive({ sampleName: '', category: undefined as string | undefined, source: undefined as string | undefined, description: '' })
const pagination = reactive({ current: 1, pageSize: 10, total: 0, showSizeChanger: true, showTotal: (t: number) => `共 ${t} 条` })

const columns = [
  { title: '样本编码', dataIndex: 'sampleCode', key: 'sampleCode', width: 140 },
  { title: '样本名称', dataIndex: 'sampleName', key: 'sampleName', width: 200 },
  { title: '分类', dataIndex: 'category', key: 'category', width: 100 },
  { title: '来源', dataIndex: 'source', key: 'source', width: 100 },
  { title: '采集时间', dataIndex: 'collectedAt', key: 'collectedAt', width: 180 },
  { title: '状态', dataIndex: 'status', key: 'status', width: 100 },
  { title: '操作', key: 'actions', width: 120, fixed: 'right' as const }
]

async function fetchData() {
  loading.value = true
  try {
    const res = await getSamples({ current: pagination.current, size: pagination.pageSize, ...searchForm })
    dataSource.value = res.records; pagination.total = res.total
  } catch { dataSource.value = [] }
  finally { loading.value = false }
}
function handleSearch() { pagination.current = 1; fetchData() }
function handleReset() { searchForm.sampleCode = ''; searchForm.category = undefined; searchForm.source = undefined; handleSearch() }
function handleTableChange(pag: any) { pagination.current = pag.current; pagination.pageSize = pag.pageSize; fetchData() }
function showModal(record?: any) {
  editId.value = record?.id || null
  Object.assign(formData, record ? { sampleName: record.sampleName, category: record.category, source: record.source, description: record.description } : { sampleName: '', category: undefined, source: undefined, description: '' })
  modalVisible.value = true
}
async function handleSave() {
  saveLoading.value = true
  try { await createSample(formData); message.success('保存成功'); modalVisible.value = false; fetchData() }
  finally { saveLoading.value = false }
}
async function handleDelete(id: number) { await deleteSample(id); message.success('删除成功'); fetchData() }
onMounted(() => fetchData())
</script>
