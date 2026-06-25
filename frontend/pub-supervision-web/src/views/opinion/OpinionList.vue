<template>
  <div>
    <a-card :bordered="false" style="margin-bottom: 16px">
      <a-form :model="searchForm" layout="inline">
        <a-row :gutter="16">
          <a-col :span="5"><a-form-item label="关键词"><a-input v-model:value="searchForm.keyword" placeholder="请输入" allow-clear /></a-form-item></a-col>
          <a-col :span="5"><a-form-item label="平台"><a-select v-model:value="searchForm.platform" placeholder="请选择" allow-clear><a-select-option value="微博">微博</a-select-option><a-select-option value="微信">微信</a-select-option><a-select-option value="抖音">抖音</a-select-option></a-select></a-form-item></a-col>
          <a-col :span="5"><a-form-item label="情感倾向"><a-select v-model:value="searchForm.sentiment" placeholder="请选择" allow-clear><a-select-option value="正面">正面</a-select-option><a-select-option value="负面">负面</a-select-option><a-select-option value="中性">中性</a-select-option></a-select></a-form-item></a-col>
          <a-col :span="5"><a-form-item label="风险等级"><a-select v-model:value="searchForm.riskLevel" placeholder="请选择" allow-clear><a-select-option value="高">高</a-select-option><a-select-option value="中">中</a-select-option><a-select-option value="低">低</a-select-option></a-select></a-form-item></a-col>
          <a-col :span="4"><a-space><a-button type="primary" @click="handleSearch">查询</a-button><a-button @click="handleReset">重置</a-button></a-space></a-col>
        </a-row>
      </a-form>
    </a-card>
    <a-row :gutter="[16, 16]">
      <a-col :span="8" v-for="item in opinionList" :key="item.id">
        <a-card hoverable>
          <template #title>
            <div style="display: flex; justify-content: space-between; align-items: center">
              <span style="font-size: 14px; flex: 1; overflow: hidden; text-overflow: ellipsis; white-space: nowrap">{{ item.title }}</span>
              <a-tag :color="getSentimentColor(item.sentiment)" style="margin-left: 8px">{{ item.sentiment }}</a-tag>
            </div>
          </template>
          <p style="color: #666; font-size: 13px; margin-bottom: 12px; display: -webkit-box; -webkit-line-clamp: 3; -webkit-box-orient: vertical; overflow: hidden">{{ item.content }}</p>
          <a-space>
            <a-tag :color="getRiskColor(item.riskLevel)">风险: {{ item.riskLevel }}</a-tag>
            <a-tag>{{ item.platform }}</a-tag>
          </a-space>
          <div style="margin-top: 12px; color: #999; font-size: 12px; display: flex; gap: 16px">
            <span>互动: {{ item.engagementCount }}</span>
            <span>回复: {{ item.replyCount }}</span>
            <span>转发: {{ item.repostCount }}</span>
          </div>
          <div style="margin-top: 8px; color: #999; font-size: 12px">{{ item.collectedAt }}</div>
        </a-card>
      </a-col>
    </a-row>
    <a-empty v-if="!opinionList.length && !loading" description="暂无舆情数据" />
    <div style="margin-top: 16px; text-align: right">
      <a-pagination v-model:current="pagination.current" :total="pagination.total" :page-size="pagination.pageSize" show-size-changer @change="handleTableChange" />
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, reactive, onMounted } from 'vue'
import { getOpinions } from '@/api/opinion'

const loading = ref(false)
const opinionList = ref<any[]>([])
const searchForm = reactive({ keyword: '', platform: undefined as any, sentiment: undefined as any, riskLevel: undefined as any })
const pagination = reactive({ current: 1, pageSize: 9, total: 0 })
function getSentimentColor(s: string) { return { '正面': 'success', '负面': 'error', '中性': 'default' }[s] || 'default' }
function getRiskColor(l: string) { return { '高': 'red', '中': 'orange', '低': 'green' }[l] || 'default' }
async function fetchData() {
  loading.value = true
  try { const res = await getOpinions({ current: pagination.current, size: pagination.pageSize, ...searchForm }); opinionList.value = res.records; pagination.total = res.total }
  catch { opinionList.value = [] } finally { loading.value = false }
}
function handleSearch() { pagination.current = 1; fetchData() }
function handleReset() { Object.assign(searchForm, { keyword: '', platform: undefined, sentiment: undefined, riskLevel: undefined }); handleSearch() }
function handleTableChange(pag: any) { pagination.current = typeof pag === 'number' ? pag : pag.current; fetchData() }
onMounted(() => fetchData())
</script>
