<template>
  <div>
    <a-row :gutter="16" style="margin-bottom: 16px">
      <a-col :span="6"><a-card><a-statistic title="监控包裹" :value="stats.totalPackages" /></a-card></a-col>
      <a-col :span="6"><a-card><a-statistic title="今日预警" :value="stats.todayAlerts" style="color: #faad14" /></a-card></a-col>
      <a-col :span="6"><a-card><a-statistic title="已拦截" :value="stats.intercepted" style="color: #ff4d4f" /></a-card></a-col>
      <a-col :span="6"><a-card><a-statistic title="活跃商户" :value="stats.activeMerchants" style="color: #52c41a" /></a-card></a-col>
    </a-row>
    <a-row :gutter="16" style="margin-bottom: 16px">
      <a-col :span="16">
        <a-card title="流转趋势" :bordered="false">
          <div ref="chartRef" style="height: 300px"></div>
        </a-card>
      </a-col>
      <a-col :span="8">
        <a-card title="实时预警" :bordered="false" :body-style="{ maxHeight: '340px', overflow: 'auto' }">
          <a-list :data-source="realtimeAlerts" size="small">
            <template #renderItem="{ item }">
              <a-list-item>
                <a-list-item-meta>
                  <template #avatar><a-tag :color="getLevelColor(item.level)">{{ item.level }}</a-tag></template>
                  <template #title><span style="font-size: 13px">{{ item.title }}</span></template>
                  <template #description><span style="font-size: 12px; color: #999">{{ item.time }}</span></template>
                </a-list-item-meta>
              </a-list-item>
            </template>
          </a-list>
        </a-card>
      </a-col>
    </a-row>
  </div>
</template>

<script setup lang="ts">
import { ref, reactive, onMounted } from 'vue'
import * as echarts from 'echarts'

const chartRef = ref<HTMLDivElement>()
const stats = reactive({ totalPackages: 12580, todayAlerts: 23, intercepted: 8, activeMerchants: 156 })
const realtimeAlerts = ref([
  { title: '包裹 SF1234567890 可疑', level: '红色', time: '1分钟前' },
  { title: '商户资质异常', level: '橙色', time: '5分钟前' },
  { title: '批量包裹异常', level: '黄色', time: '15分钟前' },
  { title: '信息不完整包裹', level: '蓝色', time: '30分钟前' },
  { title: '商户黑名单触发', level: '红色', time: '45分钟前' }
])
function getLevelColor(l: string) { return { '红色': 'red', '橙色': 'orange', '黄色': 'gold', '蓝色': 'blue' }[l] || 'default' }

onMounted(() => {
  if (!chartRef.value) return
  const chart = echarts.init(chartRef.value)
  chart.setOption({
    tooltip: { trigger: 'axis' },
    legend: { data: ['包裹数量', '预警数量'] },
    grid: { left: '3%', right: '4%', bottom: '3%', containLabel: true },
    xAxis: { type: 'category', data: ['1月', '2月', '3月', '4月', '5月', '6月'] },
    yAxis: { type: 'value' },
    series: [
      { name: '包裹数量', type: 'line', data: [1200, 1500, 1800, 2100, 1900, 2300], smooth: true },
      { name: '预警数量', type: 'line', data: [23, 45, 32, 56, 28, 42], smooth: true }
    ]
  })
  window.addEventListener('resize', () => chart.resize())
})
</script>
