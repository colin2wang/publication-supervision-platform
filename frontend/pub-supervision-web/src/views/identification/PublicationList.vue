<template>
  <div>
    <a-card :bordered="false" style="margin-bottom: 16px">
      <a-form :model="searchForm" layout="inline">
        <a-row :gutter="16">
          <a-col :span="6">
            <a-form-item label="ISBN">
              <a-input v-model:value="searchForm.isbn" placeholder="请输入ISBN" allow-clear />
            </a-form-item>
          </a-col>
          <a-col :span="6">
            <a-form-item label="书名">
              <a-input v-model:value="searchForm.title" placeholder="请输入书名" allow-clear />
            </a-form-item>
          </a-col>
          <a-col :span="6">
            <a-form-item label="作者">
              <a-input v-model:value="searchForm.author" placeholder="请输入作者" allow-clear />
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
          新增出版物
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

    <a-modal
      v-model:open="modalVisible"
      :title="editId ? '编辑出版物' : '新增出版物'"
      @ok="handleSave"
      :confirm-loading="saveLoading"
    >
      <a-form :model="formData" :label-col="{ span: 6 }" :wrapper-col="{ span: 16 }">
        <a-form-item label="ISBN" required>
          <a-input v-model:value="formData.isbn" placeholder="请输入ISBN" />
        </a-form-item>
        <a-form-item label="书名" required>
          <a-input v-model:value="formData.title" placeholder="请输入书名" />
        </a-form-item>
        <a-form-item label="作者">
          <a-input v-model:value="formData.author" placeholder="请输入作者" />
        </a-form-item>
        <a-form-item label="出版社">
          <a-input v-model:value="formData.publisher" placeholder="请输入出版社" />
        </a-form-item>
        <a-form-item label="来源">
          <a-select v-model:value="formData.source" placeholder="请选择">
            <a-select-option value="自主采录">自主采录</a-select-option>
            <a-select-option value="系统导入">系统导入</a-select-option>
            <a-select-option value="第三方推送">第三方推送</a-select-option>
          </a-select>
        </a-form-item>
      </a-form>
    </a-modal>
  </div>
</template>

<script setup lang="ts">
import { ref, reactive, onMounted } from 'vue'
import { message } from 'ant-design-vue'
import { PlusOutlined } from '@ant-design/icons-vue'
import { getPublications, createPublication, updatePublication, deletePublication } from '@/api/identification'

const loading = ref(false)
const saveLoading = ref(false)
const modalVisible = ref(false)
const dataSource = ref<any[]>([])
const editId = ref<number | null>(null)

const searchForm = reactive({ isbn: '', title: '', author: '' })

const formData = reactive({
  isbn: '', title: '', author: '', publisher: '', source: undefined as string | undefined
})

const pagination = reactive({
  current: 1, pageSize: 10, total: 0, showSizeChanger: true,
  showTotal: (total: number) => `共 ${total} 条`
})

const columns = [
  { title: 'ISBN', dataIndex: 'isbn', key: 'isbn', width: 160 },
  { title: '书名', dataIndex: 'title', key: 'title', width: 200 },
  { title: '作者', dataIndex: 'author', key: 'author', width: 120 },
  { title: '出版社', dataIndex: 'publisher', key: 'publisher', width: 160 },
  { title: '来源', dataIndex: 'source', key: 'source', width: 100 },
  { title: '状态', dataIndex: 'status', key: 'status', width: 100 },
  { title: '创建时间', dataIndex: 'createTime', key: 'createTime', width: 180 },
  { title: '操作', key: 'actions', width: 120, fixed: 'right' as const }
]

async function fetchData() {
  loading.value = true
  try {
    const res = await getPublications({ current: pagination.current, size: pagination.pageSize, ...searchForm })
    dataSource.value = res.records
    pagination.total = res.total
  } catch { dataSource.value = [] }
  finally { loading.value = false }
}

function handleSearch() { pagination.current = 1; fetchData() }
function handleReset() { searchForm.isbn = ''; searchForm.title = ''; searchForm.author = ''; handleSearch() }
function handleTableChange(pag: any) { pagination.current = pag.current; pagination.pageSize = pag.pageSize; fetchData() }

function showModal(record?: any) {
  if (record) {
    editId.value = record.id
    Object.assign(formData, { isbn: record.isbn, title: record.title, author: record.author, publisher: record.publisher, source: record.source })
  } else {
    editId.value = null
    Object.assign(formData, { isbn: '', title: '', author: '', publisher: '', source: undefined })
  }
  modalVisible.value = true
}

async function handleSave() {
  saveLoading.value = true
  try {
    if (editId.value) {
      await updatePublication(editId.value, formData)
    } else {
      await createPublication(formData)
    }
    message.success('保存成功')
    modalVisible.value = false
    fetchData()
  } finally { saveLoading.value = false }
}

async function handleDelete(id: number) {
  await deletePublication(id)
  message.success('删除成功')
  fetchData()
}

onMounted(() => fetchData())
</script>
