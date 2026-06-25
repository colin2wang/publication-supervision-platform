<template>
  <div>
    <div class="welcome-row">
      <a-card>
        <a-row :gutter="24" align="middle">
          <a-col :span="16">
            <h2 style="margin: 0">欢迎回来，{{ userStore.userInfo?.realName || '管理员' }}</h2>
            <p style="color: #999; margin: 8px 0 0">今天是 {{ today }}，祝您工作愉快！</p>
          </a-col>
          <a-col :span="8" style="text-align: right">
            <a-tag color="blue">{{ userStore.userInfo?.roles?.join(', ') || '管理员' }}</a-tag>
          </a-col>
        </a-row>
      </a-card>
    </div>

    <a-row :gutter="16" style="margin-top: 16px">
      <a-col :span="6">
        <a-card>
          <a-statistic title="待处理任务" :value="stats.pendingTasks" style="color: #1890ff">
            <template #prefix><ClockCircleOutlined style="color: #1890ff" /></template>
          </a-statistic>
        </a-card>
      </a-col>
      <a-col :span="6">
        <a-card>
          <a-statistic title="今日预警" :value="stats.todayAlerts" style="color: #faad14">
            <template #prefix><WarningOutlined style="color: #faad14" /></template>
          </a-statistic>
        </a-card>
      </a-col>
      <a-col :span="6">
        <a-card>
          <a-statistic title="已处理" :value="stats.processed" style="color: #52c41a">
            <template #prefix><CheckCircleOutlined style="color: #52c41a" /></template>
          </a-statistic>
        </a-card>
      </a-col>
      <a-col :span="6">
        <a-card>
          <a-statistic title="总出版物" :value="stats.totalPublications" style="color: #722ed1">
            <template #prefix><BookOutlined style="color: #722ed1" /></template>
          </a-statistic>
        </a-card>
      </a-col>
    </a-row>

    <a-row :gutter="16" style="margin-top: 16px">
      <a-col :span="16">
        <a-card title="趋势分析" :bordered="false">
          <div ref="lineChartRef" style="height: 300px"></div>
        </a-card>
      </a-col>
      <a-col :span="8">
        <a-card title="分类统计" :bordered="false">
          <div ref="pieChartRef" style="height: 300px"></div>
        </a-card>
      </a-col>
    </a-row>

    <a-row :gutter="16" style="margin-top: 16px">
      <a-col :span="12">
        <a-card title="待办事项" :bordered="false">
          <a-list :data-source="todoList" size="small">
            <template #renderItem="{ item }">
              <a-list-item>
                <a-list-item-meta>
                  <template #avatar>
                    <a-badge :status="item.urgent ? 'error' : 'default'" />
                  </template>
                  <template #title>
                    <span>{{ item.title }}</span>
                  </template>
                  <template #description>
                    <span>{{ item.time }}</span>
                  </template>
                </a-list-item-meta>
              </a-list-item>
            </template>
          </a-list>
        </a-card>
      </a-col>
      <a-col :span="12">
        <a-card title="最新预警" :bordered="false">
          <a-list :data-source="alertList" size="small">
            <template #renderItem="{ item }">
              <a-list-item>
                <a-list-item-meta>
                  <template #avatar>
                    <a-tag :color="item.levelColor">{{ item.level }}</a-tag>
                  </template>
                  <template #title>
                    <span>{{ item.title }}</span>
                  </template>
                  <template #description>
                    <span>{{ item.time }}</span>
                  </template>
                </a-list-item-meta>
              </a-list-item>
            </template>
          </a-list>
        </a-card>
      </a-col>
    </a-row>

    <div style="margin-top: 16px">
      <a-card title="快捷操作" :bordered="false">
        <a-space :size="16">
          <a-button type="primary" @click="$router.push('/identification/tasks')">新建鉴定任务</a-button>
          <a-button @click="$router.push('/circulation/alerts')">查看预警</a-button>
          <a-button @click="$router.push('/opinion/analysis')">舆情分析</a-button>
          <a-button @click="$router.push('/ai/knowledge-bases')">知识库管理</a-button>
        </a-space>
      </a-card>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, reactive, onMounted } from 'vue'
import { useUserStore } from '@/store/user'
import dayjs from 'dayjs'
import * as echarts from 'echarts'
import {
  ClockCircleOutlined,
  WarningOutlined,
  CheckCircleOutlined,
  BookOutlined
} from '@ant-design/icons-vue'

const userStore = useUserStore()
const today = dayjs().format('YYYY年MM月DD日')

const stats = reactive({
  pendingTasks: 12,
  todayAlerts: 5,
  processed: 86,
  totalPublications: 3256
})

const lineChartRef = ref<HTMLDivElement>()
const pieChartRef = ref<HTMLDivElement>()

const todoList = ref([
  { title: '审核出版物鉴定报告 #2024-001', time: '10分钟前', urgent: true },
  { title: '处理商户资质审核', time: '30分钟前', urgent: false },
  { title: '查看红色预警信息', time: '1小时前', urgent: true },
  { title: '更新采集任务配置', time: '2小时前', urgent: false },
  { title: '审核智能体发布申请', time: '3小时前', urgent: false }
])

const alertList = ref([
  { title: '发现疑似非法出版物流转', level: '红色', levelColor: 'red', time: '5分钟前' },
  { title: '商户资质即将过期', level: '橙色', levelColor: 'orange', time: '20分钟前' },
  { title: '负面舆情热度上升', level: '黄色', levelColor: 'gold', time: '1小时前' },
  { title: '包裹信息异常', level: '蓝色', levelColor: 'blue', time: '2小时前' }
])

onMounted(() => {
  initLineChart()
  initPieChart()
})

function initLineChart() {
  if (!lineChartRef.value) return
  const chart = echarts.init(lineChartRef.value)
  chart.setOption({
    tooltip: { trigger: 'axis' },
    legend: { data: ['鉴定任务', '预警处理', '舆情采集'] },
    grid: { left: '3%', right: '4%', bottom: '3%', containLabel: true },
    xAxis: {
      type: 'category',
      data: ['周一', '周二', '周三', '周四', '周五', '周六', '周日']
    },
    yAxis: { type: 'value' },
    series: [
      { name: '鉴定任务', type: 'line', data: [12, 19, 15, 25, 22, 18, 20], smooth: true },
      { name: '预警处理', type: 'line', data: [5, 8, 6, 12, 9, 7, 10], smooth: true },
      { name: '舆情采集', type: 'line', data: [30, 42, 38, 50, 45, 35, 40], smooth: true }
    ]
  })
  window.addEventListener('resize', () => chart.resize())
}

function initPieChart() {
  if (!pieChartRef.value) return
  const chart = echarts.init(pieChartRef.value)
  chart.setOption({
    tooltip: { trigger: 'item' },
    series: [
      {
        type: 'pie',
        radius: ['40%', '70%'],
        avoidLabelOverlap: false,
        itemStyle: { borderRadius: 6, borderColor: '#fff', borderWidth: 2 },
        label: { show: false },
        emphasis: { label: { show: true, fontSize: 14, fontWeight: 'bold' } },
        labelLine: { show: false },
        data: [
          { value: 1048, name: '教辅类' },
          { value: 735, name: '文学类' },
          { value: 580, name: '科技类' },
          { value: 484, name: '艺术类' },
          { value: 300, name: '其他' }
        ]
      }
    ]
  })
  window.addEventListener('resize', () => chart.resize())
}
</script>

<style scoped>
.welcome-row {
  margin-bottom: 0;
}
</style>
