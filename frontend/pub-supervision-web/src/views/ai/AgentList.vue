<template>
  <div>
    <a-card :bordered="false">
      <template #extra>
        <a-button type="primary" @click="showModal()">
          <template #icon><PlusOutlined /></template>
          创建智能体
        </a-button>
      </template>
      <a-row :gutter="[16, 16]">
        <a-col :span="8" v-for="item in agentList" :key="item.id">
          <a-card hoverable>
            <template #title>
              <div style="display: flex; justify-content: space-between; align-items: center">
                <span>{{ item.agentName }}</span>
                <a-tag :color="item.status === '已发布' ? 'success' : item.status === '草稿' ? 'default' : 'processing'">{{ item.status }}</a-tag>
              </div>
            </template>
            <p style="color: #666">{{ item.description || '暂无描述' }}</p>
            <a-space>
              <span>调用次数: {{ item.invocationCount || 0 }}</span>
              <span>模型: {{ item.modelName || '-' }}</span>
            </a-space>
            <div style="margin-top: 12px; display: flex; gap: 8px">
              <a-button v-if="item.status !== '已发布'" type="primary" size="small" @click="handlePublish(item)">发布</a-button>
              <a-button size="small" @click="handleTest(item)">测试</a-button>
              <a-popconfirm title="确定删除？" @confirm="handleDelete(item.id)"><a-button danger size="small">删除</a-button></a-popconfirm>
            </div>
          </a-card>
        </a-col>
      </a-row>
      <a-empty v-if="!agentList.length" description="暂无智能体" />
    </a-card>

    <a-modal v-model:open="modalVisible" title="创建智能体" @ok="handleSave" :confirm-loading="saveLoading">
      <a-form :model="formData" :label-col="{ span: 6 }" :wrapper-col="{ span: 16 }">
        <a-form-item label="名称" required><a-input v-model:value="formData.agentName" /></a-form-item>
        <a-form-item label="描述"><a-textarea v-model:value="formData.description" :rows="3" /></a-form-item>
        <a-form-item label="绑定模型">
          <a-select v-model:value="formData.modelId" placeholder="请选择">
            <a-select-option v-for="m in modelOptions" :key="m.id" :value="m.id">{{ m.modelName }}</a-select-option>
          </a-select>
        </a-form-item>
        <a-form-item label="系统提示词"><a-textarea v-model:value="formData.systemPrompt" :rows="4" placeholder="请输入系统提示词" /></a-form-item>
      </a-form>
    </a-modal>

    <a-modal v-model:open="testModalVisible" title="测试智能体" :footer="null" width="600px">
      <a-input.TextArea v-model:value="testInput" :rows="3" placeholder="请输入测试内容" style="margin-bottom: 12px" />
      <a-button type="primary" @click="handleDoTest" :loading="testLoading" style="margin-bottom: 12px">发送</a-button>
      <a-card v-if="testOutput" size="small"><p style="white-space: pre-wrap">{{ testOutput }}</p></a-card>
    </a-modal>
  </div>
</template>

<script setup lang="ts">
import { ref, reactive, onMounted } from 'vue'
import { message } from 'ant-design-vue'
import { PlusOutlined } from '@ant-design/icons-vue'
import { getAgents, createAgent, publishAgent, invokeAgent, deleteAgent, getModels } from '@/api/ai'

const agentList = ref<any[]>([]); const modelOptions = ref<any[]>([])
const modalVisible = ref(false); const saveLoading = ref(false)
const testModalVisible = ref(false); const testLoading = ref(false); const testInput = ref(''); const testOutput = ref('')
const currentTestAgentId = ref(0)
const formData = reactive({ agentName: '', description: '', modelId: undefined as number | undefined, systemPrompt: '' })

async function fetchData() {
  try { const res = await getAgents({ current: 1, size: 50 }); agentList.value = res.records } catch { agentList.value = [] }
  try { const res = await getModels({ current: 1, size: 100 }); modelOptions.value = res.records } catch { modelOptions.value = [] }
}
function showModal() { Object.assign(formData, { agentName: '', description: '', modelId: undefined, systemPrompt: '' }); modalVisible.value = true }
async function handleSave() {
  saveLoading.value = true
  try { await createAgent(formData); message.success('创建成功'); modalVisible.value = false; fetchData() }
  finally { saveLoading.value = false }
}
async function handlePublish(item: any) { await publishAgent(item.id); message.success('发布成功'); fetchData() }
function handleTest(item: any) { currentTestAgentId.value = item.id; testInput.value = ''; testOutput.value = ''; testModalVisible.value = true }
async function handleDoTest() {
  testLoading.value = true
  try { const res = await invokeAgent(currentTestAgentId.value, { input: testInput.value }); testOutput.value = (res as any).output || '无返回' }
  catch { testOutput.value = '调用失败' }
  finally { testLoading.value = false }
}
async function handleDelete(id: number) { await deleteAgent(id); message.success('删除成功'); fetchData() }
onMounted(() => fetchData())
</script>
