<template>
  <div>
    <a-button style="margin-bottom: 16px" @click="router.back()">
      <template #icon><ArrowLeftOutlined /></template>
      返回
    </a-button>

    <a-spin :spinning="loading">
      <a-card title="任务信息" :bordered="false" style="margin-bottom: 16px">
        <a-descriptions :column="3" bordered>
          <a-descriptions-item label="任务编号">{{ task?.taskCode }}</a-descriptions-item>
          <a-descriptions-item label="任务名称">{{ task?.taskName }}</a-descriptions-item>
          <a-descriptions-item label="任务类型">
            <a-tag :color="getTypeColor(task?.taskType)">{{ task?.taskType }}</a-tag>
          </a-descriptions-item>
          <a-descriptions-item label="优先级">
            <a-tag :color="getPriorityColor(task?.priority)">{{ task?.priority }}</a-tag>
          </a-descriptions-item>
          <a-descriptions-item label="状态">
            <a-tag :color="getStatusColor(task?.status)">{{ task?.status }}</a-tag>
          </a-descriptions-item>
          <a-descriptions-item label="负责人">{{ task?.assignee || '-' }}</a-descriptions-item>
          <a-descriptions-item label="截止日期" :span="2">{{ task?.deadline || '-' }}</a-descriptions-item>
          <a-descriptions-item label="创建时间">{{ task?.createTime }}</a-descriptions-item>
          <a-descriptions-item label="任务描述" :span="3">{{ task?.description || '-' }}</a-descriptions-item>
        </a-descriptions>
      </a-card>

      <a-card :bordered="false">
        <a-tabs v-model:activeKey="activeTab">
          <a-tab-pane key="basic" tab="基本信息">
            <p style="color: #999; padding: 20px 0">基本信息内容区域</p>
          </a-tab-pane>
          <a-tab-pane key="materials" tab="鉴定材料">
            <a-table :columns="materialColumns" :data-source="materials" :pagination="false" row-key="id">
              <template #bodyCell="{ column, record }">
                <template v-if="column.key === 'status'">
                  <a-tag :color="record.status === '已上传' ? 'success' : 'default'">{{ record.status }}</a-tag>
                </template>
              </template>
            </a-table>
          </a-tab-pane>
          <a-tab-pane key="results" tab="鉴定结果">
            <a-table :columns="resultColumns" :data-source="results" :pagination="false" row-key="id">
              <template #bodyCell="{ column, record }">
                <template v-if="column.key === 'reviewStatus'">
                  <a-tag :color="record.reviewStatus === '已通过' ? 'success' : record.reviewStatus === '已拒绝' ? 'error' : 'processing'">
                    {{ record.reviewStatus }}
                  </a-tag>
                </template>
              </template>
            </a-table>
          </a-tab-pane>
          <a-tab-pane key="audit" tab="审核记录">
            <a-timeline style="margin-top: 16px">
              <a-timeline-item color="green">
                <p>任务创建 - 系统自动</p>
                <p style="color: #999">{{ task?.createTime }}</p>
              </a-timeline-item>
              <a-timeline-item color="blue">
                <p>任务分配 - 管理员</p>
                <p style="color: #999">分配给 {{ task?.assignee || '未分配' }}</p>
              </a-timeline-item>
            </a-timeline>
          </a-tab-pane>
        </a-tabs>
      </a-card>
    </a-spin>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { ArrowLeftOutlined } from '@ant-design/icons-vue'
import { getTask } from '@/api/identification'

const props = defineProps<{ id: string | number }>()
const router = useRouter()
const loading = ref(false)
const activeTab = ref('basic')
const task = ref<any>(null)
const materials = ref<any[]>([])
const results = ref<any[]>([])

const materialColumns = [
  { title: '材料名称', dataIndex: 'fileName', key: 'fileName' },
  { title: '材料类型', dataIndex: 'materialType', key: 'materialType' },
  { title: '文件大小', dataIndex: 'fileSize', key: 'fileSize' },
  { title: '状态', dataIndex: 'status', key: 'status' },
  { title: '上传时间', dataIndex: 'createTime', key: 'createTime' }
]

const resultColumns = [
  { title: '结果类型', dataIndex: 'resultType', key: 'resultType' },
  { title: '置信度', dataIndex: 'confidence', key: 'confidence' },
  { title: '审核人', dataIndex: 'reviewer', key: 'reviewer' },
  { title: '审核状态', dataIndex: 'reviewStatus', key: 'reviewStatus' },
  { title: '审核时间', dataIndex: 'reviewTime', key: 'reviewTime' }
]

function getTypeColor(type?: string) {
  const map: Record<string, string> = { '鉴定': 'blue', '审核': 'green', '复查': 'orange' }
  return map[type || ''] || 'default'
}

function getPriorityColor(p?: string) {
  const map: Record<string, string> = { '高': 'red', '中': 'orange', '低': 'green' }
  return map[p || ''] || 'default'
}

function getStatusColor(s?: string) {
  const map: Record<string, string> = { '待分配': 'default', '进行中': 'processing', '待审核': 'warning', '已完成': 'success' }
  return map[s || ''] || 'default'
}

onMounted(async () => {
  loading.value = true
  try {
    const res = await getTask(Number(props.id))
    task.value = res
  } catch {
    // ignore
  } finally {
    loading.value = false
  }
})
</script>
