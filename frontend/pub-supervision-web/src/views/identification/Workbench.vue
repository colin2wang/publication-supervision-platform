<template>
  <div>
    <a-row :gutter="16" style="margin-bottom: 16px">
      <a-col :span="6"><a-card><a-statistic title="今日任务" :value="stats.todayTasks" /></a-card></a-col>
      <a-col :span="6"><a-card><a-statistic title="进行中" :value="stats.inProgress" style="color: #1890ff" /></a-card></a-col>
      <a-col :span="6"><a-card><a-statistic title="已完成" :value="stats.completed" style="color: #52c41a" /></a-card></a-col>
      <a-col :span="6"><a-card><a-statistic title="待审核" :value="stats.pendingReview" style="color: #faad14" /></a-card></a-col>
    </a-row>

    <a-card title="我的任务" :bordered="false">
      <a-tabs v-model:activeKey="activeTab">
        <a-tab-pane key="all" tab="全部" />
        <a-tab-pane key="in_progress" tab="进行中" />
        <a-tab-pane key="completed" tab="已完成" />
      </a-tabs>
      <a-list :data-source="taskList" :loading="loading">
        <template #renderItem="{ item }">
          <a-list-item>
            <a-list-item-meta>
              <template #title>
                <a @click="$router.push(`/identification/tasks/${item.id}`)">{{ item.taskName }}</a>
              </template>
              <template #description>
                <span>{{ item.taskCode }} | {{ item.taskType }}</span>
              </template>
            </a-list-item-meta>
            <template #extra>
              <a-tag :color="getPriorityColor(item.priority)">{{ item.priority }}</a-tag>
              <a-tag :color="getStatusColor(item.status)">{{ item.status }}</a-tag>
            </template>
          </a-list-item>
        </template>
      </a-list>
    </a-card>
  </div>
</template>

<script setup lang="ts">
import { ref, reactive, onMounted } from 'vue'
import { getTasks } from '@/api/identification'

const loading = ref(false)
const activeTab = ref('all')
const taskList = ref<any[]>([])
const stats = reactive({ todayTasks: 8, inProgress: 5, completed: 12, pendingReview: 3 })

function getPriorityColor(p: string) { return { '高': 'red', '中': 'orange', '低': 'green' }[p] || 'default' }
function getStatusColor(s: string) { return { '待分配': 'default', '进行中': 'processing', '待审核': 'warning', '已完成': 'success' }[s] || 'default' }

async function fetchData() {
  loading.value = true
  try {
    const res = await getTasks({ current: 1, size: 20 })
    taskList.value = res.records
  } catch { taskList.value = [] }
  finally { loading.value = false }
}

onMounted(() => fetchData())
</script>
