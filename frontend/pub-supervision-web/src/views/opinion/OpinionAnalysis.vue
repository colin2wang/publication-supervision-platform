<template>
  <div>
    <a-card :bordered="false" style="margin-bottom: 16px">
      <a-form layout="inline">
        <a-row :gutter="16">
          <a-col :span="6"><a-form-item label="时间范围"><a-range-picker v-model:value="dateRange" style="width: 100%" /></a-form-item></a-col>
          <a-col :span="6"><a-form-item label="平台"><a-select v-model:value="platform" placeholder="请选择" allow-clear><a-select-option value="微博">微博</a-select-option><a-select-option value="微信">微信</a-select-option><a-select-option value="全部">全部</a-select-option></a-select></a-form-item></a-col>
          <a-col :span="4"><a-button type="primary" @click="handleSearch">查询</a-button></a-col>
        </a-row>
      </a-form>
    </a-card>
    <a-row :gutter="16" style="margin-bottom: 16px">
      <a-col :span="12">
        <a-card title="舆情趋势" :bordered="false">
          <div ref="trendChartRef" style="height: 300px"></div>
        </a-card>
      </a-col>
      <a-col :span="12">
        <a-card title="渠道分布" :bordered="false">
          <div ref="channelChartRef" style="height: 300px"></div>
        </a-card>
      </a-col>
    </a-row>
    <a-row :gutter="16">
      <a-col :span="12">
        <a-card title="热门话题" :bordered="false">
          <div ref="topicChartRef" style="height: 300px"></div>
        </a-card>
      </a-col>
      <a-col :span="12">
        <a-card title="地域分布" :bordered="false">
          <div style="height: 300px; display: flex; align-items: center; justify-content: center; color: #999">
            <a-empty description="地图数据加载中..." />
          </div>
        </a-card>
      </a-col>
    </a-row>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'
import dayjs from 'dayjs'
import * as echarts from 'echarts'

const dateRange = ref<[dayjs.Dayjs, dayjs.Dayjs]>([dayjs().subtract(7, 'day'), dayjs()])
const platform = ref<string | undefined>(undefined)
const trendChartRef = ref<HTMLDivElement>()
const channelChartRef = ref<HTMLDivElement>()
const topicChartRef = ref<HTMLDivElement>()

function handleSearch() { initCharts() }

onMounted(() => initCharts())

function initCharts() {
  if (trendChartRef.value) {
    const c = echarts.init(trendChartRef.value)
    c.setOption({
      tooltip: { trigger: 'axis' },
      legend: { data: ['正面', '负面', '中性'] },
      grid: { left: '3%', right: '4%', bottom: '3%', containLabel: true },
      xAxis: { type: 'category', data: Array.from({ length: 7 }, (_, i) => dayjs().subtract(6 - i, 'day').format('MM-DD')) },
      yAxis: { type: 'value' },
      series: [
        { name: '正面', type: 'line', data: [120, 132, 101, 134, 90, 230, 210], smooth: true, areaStyle: { opacity: 0.3 } },
        { name: '负面', type: 'line', data: [22, 18, 15, 25, 12, 30, 28], smooth: true, areaStyle: { opacity: 0.3 } },
        { name: '中性', type: 'line', data: [80, 92, 71, 84, 60, 120, 110], smooth: true, areaStyle: { opacity: 0.3 } }
      ]
    })
    window.addEventListener('resize', () => c.resize())
  }
  if (channelChartRef.value) {
    const c = echarts.init(channelChartRef.value)
    c.setOption({
      tooltip: { trigger: 'item' },
      series: [{ type: 'pie', radius: ['40%', '70%'], data: [{ value: 435, name: '微博' }, { value: 310, name: '微信' }, { value: 234, name: '抖音' }, { value: 155, name: '知乎' }, { value: 98, name: '其他' }] }]
    })
    window.addEventListener('resize', () => c.resize())
  }
  if (topicChartRef.value) {
    const c = echarts.init(topicChartRef.value)
    c.setOption({
      tooltip: {},
      xAxis: { type: 'category', data: ['非法出版物', '版权侵权', '虚假宣传', '内容违规', '价格欺诈'], axisLabel: { rotate: 0 } },
      yAxis: { type: 'value' },
      series: [{ type: 'bar', data: [120, 98, 76, 65, 42], itemStyle: { color: '#1890ff' } }]
    })
    window.addEventListener('resize', () => c.resize())
  }
}
</script>
