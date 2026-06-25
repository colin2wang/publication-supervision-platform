<template>
  <div>
    <a-card :bordered="false">
      <template #extra>
        <a-button type="primary" @click="showModal()">
          <template #icon><PlusOutlined /></template>
          新建数据集
        </a-button>
      </template>
      <a-row :gutter="[16, 16]">
        <a-col :span="8" v-for="item in datasetList" :key="item.id">
          <a-card hoverable>
            <template #title>{{ item.datasetName }}</template>
            <a-space direction="vertical" style="width: 100%">
              <p>{{ item.description || '暂无描述' }}</p>
              <a-space>
                <a-tag>{{ item.category }}</a-tag>
                <a-tag :color="item.status === '启用' ? 'success' : 'default'">{{ item.status }}</a-tag>
              </a-space>
              <p style="color: #999">记录数: {{ item.recordCount }} | 更新: {{ item.updateTime }}</p>
              <a-popconfirm title="确定删除？" @confirm="handleDelete(item.id)">
                <a-button danger size="small">删除</a-button>
              </a-popconfirm>
            </a-space>
          </a-card>
        </a-col>
      </a-row>
      <a-empty v-if="!datasetList.length" description="暂无数据集" />
    </a-card>

    <a-modal v-model:open="modalVisible" title="新建数据集" @ok="handleSave" :confirm-loading="saveLoading">
      <a-form :model="formData" :label-col="{ span: 6 }" :wrapper-col="{ span: 16 }">
        <a-form-item label="数据集名称" required><a-input v-model:value="formData.datasetName" /></a-form-item>
        <a-form-item label="分类">
          <a-select v-model:value="formData.category" placeholder="请选择">
            <a-select-option value="鉴定数据">鉴定数据</a-select-option>
            <a-select-option value="样本数据">样本数据</a-select-option>
            <a-select-option value="训练数据">训练数据</a-select-option>
          </a-select>
        </a-form-item>
        <a-form-item label="描述"><a-textarea v-model:value="formData.description" :rows="3" /></a-form-item>
      </a-form>
    </a-modal>
  </div>
</template>

<script setup lang="ts">
import { ref, reactive, onMounted } from 'vue'
import { message } from 'ant-design-vue'
import { PlusOutlined } from '@ant-design/icons-vue'
import { getDatasets, createDataset, deleteDataset } from '@/api/identification'

const datasetList = ref<any[]>([])
const modalVisible = ref(false)
const saveLoading = ref(false)
const formData = reactive({ datasetName: '', category: undefined as string | undefined, description: '' })

async function fetchData() {
  try { const res = await getDatasets({ current: 1, size: 50 }); datasetList.value = res.records }
  catch { datasetList.value = [] }
}
function showModal() { formData.datasetName = ''; formData.category = undefined; formData.description = ''; modalVisible.value = true }
async function handleSave() {
  saveLoading.value = true
  try { await createDataset(formData); message.success('创建成功'); modalVisible.value = false; fetchData() }
  finally { saveLoading.value = false }
}
async function handleDelete(id: number) { await deleteDataset(id); message.success('删除成功'); fetchData() }
onMounted(() => fetchData())
</script>
