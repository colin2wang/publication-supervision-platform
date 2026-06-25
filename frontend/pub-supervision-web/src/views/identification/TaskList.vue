<template>
  <div>
    <div class="page-header">
      <a-button type="primary" @click="showCreateModal">
        <template #icon><PlusOutlined /></template>
        新建任务
      </a-button>
    </div>

    <a-card :bordered="false" style="margin-bottom: 16px">
      <a-form :model="searchForm" layout="inline">
        <a-row :gutter="16">
          <a-col :span="6">
            <a-form-item label="任务编号">
              <a-input v-model:value="searchForm.taskCode" placeholder="请输入任务编号" allow-clear />
            </a-form-item>
          </a-col>
          <a-col :span="6">
            <a-form-item label="任务类型">
              <a-select v-model:value="searchForm.taskType" placeholder="请选择" allow-clear>
                <a-select-option value="鉴定">鉴定</a-select-option>
                <a-select-option value="审核">审核</a-select-option>
                <a-select-option value="复查">复查</a-select-option>
              </a-select>
            </a-form-item>
          </a-col>
          <a-col :span="6">
            <a-form-item label="状态">
              <a-select v-model:value="searchForm.status" placeholder="请选择" allow-clear>
                <a-select-option value="待分配">待分配</a-select-option>
                <a-select-option value="进行中">进行中</a-select-option>
                <a-select-option value="待审核">待审核</a-select-option>
                <a-select-option value="已完成">已完成</a-select-option>
              </a-select>
            </a-form-item>
          </a-col>
          <a-col :span="6">
            <a-form-item label="优先级">
              <a-select v-model:value="searchForm.priority" placeholder="请选择" allow-clear>
                <a-select-option value="高">高</a-select-option>
                <a-select-option value="中">中</a-select-option>
                <a-select-option value="低">低</a-select-option>
              </a-select>
            </a-form-item>
          </a-col>
        </a-row>
        <a-row>
          <a-col :span="24" style="text-align: right">
            <a-space>
              <a-button type="primary" @click="handleSearch">查询</a-button>
              <a-button @click="handleReset">重置</a-button>
            </a-space>
          </a-col>
        </a-row>
      </a-form>
    </a-card>

    <a-card :bordered="false">
      <a-tabs v-model:activeKey="activeTab" @change="handleTabChange">
        <a-tab-pane key="全部" tab="全部" />
        <a-tab-pane key="待分配" tab="待分配" />
        <a-tab-pane key="进行中" tab="进行中" />
        <a-tab-pane key="待审核" tab="待审核" />
        <a-tab-pane key="已完成" tab="已完成" />
      </a-tabs>

      <a-table
        :columns="columns"
        :data-source="dataSource"
        :loading="loading"
        :pagination="pagination"
        @change="handleTableChange"
        row-key="id"
      >
        <template #bodyCell="{ column, record }">
          <template v-if="column.key === 'taskCode'">
            <a @click="goToDetail(record.id)">{{ record.taskCode }}</a>
          </template>
          <template v-if="column.key === 'taskType'">
            <a-tag :color="getTypeColor(record.taskType)">{{ record.taskType }}</a-tag>
          </template>
          <template v-if="column.key === 'priority'">
            <a-tag :color="getPriorityColor(record.priority)">{{ record.priority }}</a-tag>
          </template>
          <template v-if="column.key === 'status'">
            <a-tag :color="getStatusColor(record.status)">{{ record.status }}</a-tag>
          </template>
          <template v-if="column.key === 'actions'">
            <a-space>
              <a @click="goToDetail(record.id)">详情</a>
              <a @click="handleAssign(record)">分配</a>
              <a-popconfirm title="确定删除此任务？" @confirm="handleDelete(record.id)">
                <a style="color: #ff4d4f">删除</a>
              </a-popconfirm>
            </a-space>
          </template>
        </template>
      </a-table>
    </a-card>

    <a-modal
      v-model:open="createModalVisible"
      title="新建鉴定任务"
      @ok="handleCreate"
      :confirm-loading="createLoading"
    >
      <a-form :model="createForm" :label-col="{ span: 6 }" :wrapper-col="{ span: 16 }">
        <a-form-item label="任务名称" required>
          <a-input v-model:value="createForm.taskName" placeholder="请输入任务名称" />
        </a-form-item>
        <a-form-item label="任务类型" required>
          <a-select v-model:value="createForm.taskType" placeholder="请选择">
            <a-select-option value="鉴定">鉴定</a-select-option>
            <a-select-option value="审核">审核</a-select-option>
            <a-select-option value="复查">复查</a-select-option>
          </a-select>
        </a-form-item>
        <a-form-item label="优先级" required>
          <a-select v-model:value="createForm.priority" placeholder="请选择">
            <a-select-option value="高">高</a-select-option>
            <a-select-option value="中">中</a-select-option>
            <a-select-option value="低">低</a-select-option>
          </a-select>
        </a-form-item>
        <a-form-item label="截止日期">
          <a-date-picker v-model:value="createForm.deadline" style="width: 100%" />
        </a-form-item>
        <a-form-item label="任务描述">
          <a-textarea v-model:value="createForm.description" :rows="3" placeholder="请输入任务描述" />
        </a-form-item>
      </a-form>
    </a-modal>

    <a-modal
      v-model:open="assignModalVisible"
      title="分配任务"
      @ok="handleAssignConfirm"
      :confirm-loading="assignLoading"
    >
      <a-form :model="assignForm" :label-col="{ span: 6 }" :wrapper-col="{ span: 16 }">
        <a-form-item label="指派给" required>
          <a-select v-model:value="assignForm.assignee" placeholder="请选择负责人">
            <a-select-option value="张三">张三</a-select-option>
            <a-select-option value="李四">李四</a-select-option>
            <a-select-option value="王五">王五</a-select-option>
          </a-select>
        </a-form-item>
      </a-form>
    </a-modal>
  </div>
</template>

<script setup lang="ts">
import { ref, reactive, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { message } from 'ant-design-vue'
import { PlusOutlined } from '@ant-design/icons-vue'
import { getTasks, createTask, deleteTask, assignTask } from '@/api/identification'

const router = useRouter()
const loading = ref(false)
const createLoading = ref(false)
const assignLoading = ref(false)
const createModalVisible = ref(false)
const assignModalVisible = ref(false)
const activeTab = ref('全部')
const dataSource = ref<any[]>([])
const currentAssignId = ref(0)

const searchForm = reactive({
  taskCode: '',
  taskType: undefined as string | undefined,
  status: undefined as string | undefined,
  priority: undefined as string | undefined
})

const pagination = reactive({
  current: 1,
  pageSize: 10,
  total: 0,
  showSizeChanger: true,
  showTotal: (total: number) => `共 ${total} 条`
})

const createForm = reactive({
  taskName: '',
  taskType: undefined as string | undefined,
  priority: undefined as string | undefined,
  deadline: null,
  description: ''
})

const assignForm = reactive({
  assignee: ''
})

const columns = [
  { title: '任务编号', key: 'taskCode', dataIndex: 'taskCode', width: 140 },
  { title: '任务名称', key: 'taskName', dataIndex: 'taskName', width: 200 },
  { title: '任务类型', key: 'taskType', dataIndex: 'taskType', width: 100 },
  { title: '优先级', key: 'priority', dataIndex: 'priority', width: 80 },
  { title: '状态', key: 'status', dataIndex: 'status', width: 100 },
  { title: '负责人', key: 'assignee', dataIndex: 'assignee', width: 120 },
  { title: '创建时间', key: 'createTime', dataIndex: 'createTime', width: 180 },
  { title: '操作', key: 'actions', width: 160, fixed: 'right' as const }
]

function getTypeColor(type: string) {
  const map: Record<string, string> = { '鉴定': 'blue', '审核': 'green', '复查': 'orange' }
  return map[type] || 'default'
}

function getPriorityColor(p: string) {
  const map: Record<string, string> = { '高': 'red', '中': 'orange', '低': 'green' }
  return map[p] || 'default'
}

function getStatusColor(s: string) {
  const map: Record<string, string> = { '待分配': 'default', '进行中': 'processing', '待审核': 'warning', '已完成': 'success' }
  return map[s] || 'default'
}

function goToDetail(id: number) {
  router.push(`/identification/tasks/${id}`)
}

async function fetchData() {
  loading.value = true
  try {
    const res = await getTasks({
      current: pagination.current,
      size: pagination.pageSize,
      ...searchForm,
      status: searchForm.status === '全部' ? undefined : searchForm.status
    })
    dataSource.value = res.records
    pagination.total = res.total
  } catch {
    dataSource.value = []
  } finally {
    loading.value = false
  }
}

function handleSearch() {
  pagination.current = 1
  fetchData()
}

function handleReset() {
  searchForm.taskCode = ''
  searchForm.taskType = undefined
  searchForm.status = undefined
  searchForm.priority = undefined
  handleSearch()
}

function handleTableChange(pag: any) {
  pagination.current = pag.current
  pagination.pageSize = pag.pageSize
  fetchData()
}

function handleTabChange() {
  if (activeTab.value === '全部') {
    searchForm.status = undefined
  } else {
    searchForm.status = activeTab.value
  }
  handleSearch()
}

function showCreateModal() {
  createForm.taskName = ''
  createForm.taskType = undefined
  createForm.priority = undefined
  createForm.deadline = null
  createForm.description = ''
  createModalVisible.value = true
}

async function handleCreate() {
  createLoading.value = true
  try {
    await createTask(createForm)
    message.success('创建成功')
    createModalVisible.value = false
    fetchData()
  } finally {
    createLoading.value = false
  }
}

function handleAssign(record: any) {
  currentAssignId.value = record.id
  assignForm.assignee = ''
  assignModalVisible.value = true
}

async function handleAssignConfirm() {
  assignLoading.value = true
  try {
    await assignTask(currentAssignId.value, { assignee: assignForm.assignee })
    message.success('分配成功')
    assignModalVisible.value = false
    fetchData()
  } finally {
    assignLoading.value = false
  }
}

async function handleDelete(id: number) {
  try {
    await deleteTask(id)
    message.success('删除成功')
    fetchData()
  } catch {
    // ignore
  }
}

onMounted(() => {
  fetchData()
})
</script>

<style scoped>
.page-header {
  margin-bottom: 16px;
}
</style>
