<template>
  <div>
    <a-card :bordered="false">
      <template #extra>
        <a-button type="primary" @click="showModal()">
          <template #icon><PlusOutlined /></template>
          新建知识库
        </a-button>
      </template>
      <a-row :gutter="[16, 16]">
        <a-col :span="8" v-for="item in kbList" :key="item.id">
          <a-card hoverable>
            <template #title>
              <div style="display: flex; justify-content: space-between; align-items: center">
                <span>{{ item.name }}</span>
                <a-tag :color="item.status === '启用' ? 'success' : 'default'">{{ item.status }}</a-tag>
              </div>
            </template>
            <p style="color: #666">{{ item.description || '暂无描述' }}</p>
            <a-space>
              <span>文档数: {{ item.docCount || 0 }}</span>
              <span>向量数: {{ item.vectorCount || 0 }}</span>
            </a-space>
            <div style="margin-top: 12px; display: flex; gap: 8px">
              <a-button size="small" @click="handleSearchKb(item)">搜索</a-button>
              <a-popconfirm title="确定删除？" @confirm="handleDelete(item.id)"><a-button danger size="small">删除</a-button></a-popconfirm>
            </div>
          </a-card>
        </a-col>
      </a-row>
      <a-empty v-if="!kbList.length" description="暂无知识库" />
    </a-card>

    <a-modal v-model:open="modalVisible" title="新建知识库" @ok="handleSave" :confirm-loading="saveLoading">
      <a-form :model="formData" :label-col="{ span: 6 }" :wrapper-col="{ span: 16 }">
        <a-form-item label="名称" required><a-input v-model:value="formData.name" /></a-form-item>
        <a-form-item label="描述"><a-textarea v-model:value="formData.description" :rows="3" /></a-form-item>
        <a-form-item label="嵌入模型">
          <a-select v-model:value="formData.embeddingModel" placeholder="请选择">
            <a-select-option value="text-embedding-ada-002">text-embedding-ada-002</a-select-option>
            <a-select-option value="bge-large-zh">bge-large-zh</a-select-option>
          </a-select>
        </a-form-item>
      </a-form>
    </a-modal>

    <a-modal v-model:open="searchModalVisible" title="知识库搜索" :footer="null" width="700px">
      <a-input-search v-model:value="searchQuery" placeholder="输入搜索内容" @search="handleDoSearch" style="margin-bottom: 16px" />
      <a-list :data-source="searchResults" :loading="searchLoading">
        <template #renderItem="{ item }">
          <a-list-item>
            <a-list-item-meta>
              <template #title><span>{{ item.title }}</span> <a-tag style="margin-left: 8px">相似度: {{ (item.score * 100).toFixed(1) }}%</a-tag></template>
              <template #description><span>{{ item.content }}</span></template>
            </a-list-item-meta>
          </a-list-item>
        </template>
      </a-list>
    </a-modal>
  </div>
</template>

<script setup lang="ts">
import { ref, reactive, onMounted } from 'vue'
import { message } from 'ant-design-vue'
import { PlusOutlined } from '@ant-design/icons-vue'
import { getKnowledgeBases, createKnowledgeBase, deleteKnowledgeBase, searchKnowledge } from '@/api/ai'

const kbList = ref<any[]>([])
const modalVisible = ref(false); const saveLoading = ref(false)
const searchModalVisible = ref(false); const searchLoading = ref(false)
const currentKbId = ref(0); const searchQuery = ref(''); const searchResults = ref<any[]>([])
const formData = reactive({ name: '', description: '', embeddingModel: undefined as string | undefined })

async function fetchData() {
  try { const res = await getKnowledgeBases({ current: 1, size: 50 }); kbList.value = res.records }
  catch { kbList.value = [] }
}
function showModal() { Object.assign(formData, { name: '', description: '', embeddingModel: undefined }); modalVisible.value = true }
async function handleSave() {
  saveLoading.value = true
  try { await createKnowledgeBase(formData); message.success('创建成功'); modalVisible.value = false; fetchData() }
  finally { saveLoading.value = false }
}
async function handleDelete(id: number) { await deleteKnowledgeBase(id); message.success('删除成功'); fetchData() }
function handleSearchKb(item: any) { currentKbId.value = item.id; searchQuery.value = ''; searchResults.value = []; searchModalVisible.value = true }
async function handleDoSearch() {
  searchLoading.value = true
  try { const res = await searchKnowledge(currentKbId.value, { query: searchQuery.value }); searchResults.value = (res as any).data || [] }
  catch { searchResults.value = [] }
  finally { searchLoading.value = false }
}
onMounted(() => fetchData())
</script>
