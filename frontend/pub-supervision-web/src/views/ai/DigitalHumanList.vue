<template>
  <div>
    <a-card :bordered="false">
      <template #extra>
        <a-button type="primary" @click="showModal()">
          <template #icon><PlusOutlined /></template>
          创建数字人
        </a-button>
      </template>
      <a-row :gutter="[16, 16]">
        <a-col :span="6" v-for="item in digitalHumans" :key="item.id">
          <a-card hoverable>
            <template #title>{{ item.name }}</template>
            <div style="text-align: center; margin-bottom: 12px">
              <a-avatar :size="80" :src="item.avatar" style="background-color: #1890ff">
                {{ item.name?.charAt(0) }}
              </a-avatar>
            </div>
            <p style="color: #666; text-align: center">{{ item.description || '暂无描述' }}</p>
            <a-space style="display: flex; justify-content: center">
              <a-tag>{{ item.voiceType || '默认音色' }}</a-tag>
              <a-tag :color="item.status === '启用' ? 'success' : 'default'">{{ item.status }}</a-tag>
            </a-space>
            <div style="margin-top: 12px; text-align: center">
              <a-popconfirm title="确定删除？" @confirm="handleDelete(item.id)"><a-button danger size="small">删除</a-button></a-popconfirm>
            </div>
          </a-card>
        </a-col>
      </a-row>
      <a-empty v-if="!digitalHumans.length" description="暂无数字人" />
    </a-card>

    <a-modal v-model:open="modalVisible" title="创建数字人" @ok="handleSave" :confirm-loading="saveLoading">
      <a-form :model="formData" :label-col="{ span: 6 }" :wrapper-col="{ span: 16 }">
        <a-form-item label="名称" required><a-input v-model:value="formData.name" /></a-form-item>
        <a-form-item label="描述"><a-textarea v-model:value="formData.description" :rows="3" /></a-form-item>
        <a-form-item label="头像URL"><a-input v-model:value="formData.avatar" placeholder="请输入头像链接" /></a-form-item>
        <a-form-item label="音色类型">
          <a-select v-model:value="formData.voiceType">
            <a-select-option value="男声-标准">男声-标准</a-select-option>
            <a-select-option value="女声-标准">女声-标准</a-select-option>
            <a-select-option value="男声-温暖">男声-温暖</a-select-option>
            <a-select-option value="女声-活泼">女声-活泼</a-select-option>
          </a-select>
        </a-form-item>
      </a-form>
    </a-modal>
  </div>
</template>

<script setup lang="ts">
import { ref, reactive, onMounted } from 'vue'
import { message } from 'ant-design-vue'
import { PlusOutlined } from '@ant-design/icons-vue'
import { getDigitalHumans, createDigitalHuman, deleteDigitalHuman } from '@/api/ai'

const digitalHumans = ref<any[]>([])
const modalVisible = ref(false); const saveLoading = ref(false)
const formData = reactive({ name: '', description: '', avatar: '', voiceType: undefined as string | undefined })

async function fetchData() {
  try { const res = await getDigitalHumans({ current: 1, size: 50 }); digitalHumans.value = res.records }
  catch { digitalHumans.value = [] }
}
function showModal() { Object.assign(formData, { name: '', description: '', avatar: '', voiceType: undefined }); modalVisible.value = true }
async function handleSave() {
  saveLoading.value = true
  try { await createDigitalHuman(formData); message.success('创建成功'); modalVisible.value = false; fetchData() }
  finally { saveLoading.value = false }
}
async function handleDelete(id: number) { await deleteDigitalHuman(id); message.success('删除成功'); fetchData() }
onMounted(() => fetchData())
</script>
