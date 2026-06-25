<template>
  <div>
    <a-row :gutter="16" style="margin-bottom: 16px">
      <a-col :span="6"><a-card><a-statistic title="采集总量" :value="stats.totalCollected" /></a-card></a-col>
      <a-col :span="6"><a-card><a-statistic title="有效信息" :value="stats.validInfo" style="color: #52c41a" /></a-card></a-col>
      <a-col :span="6"><a-card><a-statistic title="预警触发" :value="stats.alertTriggered" style="color: #faad14" /></a-card></a-col>
      <a-col :span="6"><a-card><a-statistic title="处理率" :value="stats.processRate" suffix="%" style="color: #1890ff" /></a-card></a-col>
    </a-row>
    <a-row :gutter="16">
      <a-col :span="12">
        <a-card title="采集效果趋势" :bordered="false">
          <div ref="effectChartRef" style="height: 300px"></div>
        </a-card>
      </a-col>
      <a-col :span="12">
        <a-card title="渠道效果排行" :bordered="false">
          <a-table :columns="rankColumns" :data-source="rankData" :pagination="false" size="small" row-key="channel">
            <template #bodyCell="{ column, record, index }">
              <template v-if="column.key === 'rank'">
                <a-tag :color="index < 3 ? 'gold' : 'default'">{{ index + 1 }}</a-tag>
              </template>
            </template>
          </a-table>
        </a-card>
      </a-col>
    </a-row>
  </div>
</template>

<script setup lang="ts">
import { ref, reactive, onMounted } from 'vue'
import * as echarts from 'echarts'

const effectChartRef = ref<HTMLDivElement>()
const stats = reactive({ totalCollected: 15680, validInfo: 12350, alertTriggered: 86, processRate: 92 })
const rankData = ref([
  { channel: '微博', collected: 5230, valid: 4100, rate: '78.4%' },
  { channel: '微信', collected: 3560, valid: 3100, rate: '87.1%' },
  { channel: '抖音', collected: 4200, valid: 3200, rate: '76.2%' },
  { channel: '知乎', collected: 1800, valid: 1600, rate: '88.9%' },
  { channel: '其他', collected: 890, valid: 350, rate: '39.3%' }
])
const rankColumns = [
  { title: '排名', key: 'rank', width: 60 },
  { title: '渠道', dataIndex: 'channel', key: 'channel' },
  { title: '采集量', dataIndex: 'collected', key: 'collected' },
  { title: '有效量', dataIndex: 'valid', key: 'valid' },
  { title: '有效率', dataIndex: 'rate', key: 'rate' }
]

onMounted(() => {
  if (!effectChartRef.value) return
  const chart = echarts.init(effectChartRef.value)
  chart.setOption({
    tooltip: { trigger: 'axis' },
    legend: { data: ['采集量', '有效信息量'] },
    grid: { left: '3%', right: '4%', bottom: '3%', containLabel: true },
    xAxis: { type: 'category', data: ['1月', '2月', '3月', '4月', '5月', '6月'] },
    yAxis: { type: 'value' },
    series: [
      { name: '采集量', type: 'bar', data: [2500, 2800, 3100, 3400, 2900, 3600], itemStyle: { color: '#1890ff' } },
      { name: '有效信息量', type: 'bar', data: [2000, 2200, 2500, 2700, 2300, 2900], itemStyle: { color: '#52c41a' } }
    ]
  })
  window.addEventListener('resize', () => chart.resize())
})
</script>
