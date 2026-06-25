<template>
  <div>
    <a-card :bordered="false">
      <template #extra><a-button type="primary" @click="showModal()"><template #icon><PlusOutlined /></template>新增菜单</a-button></template>
      <a-table :columns="columns" :data-source="dataSource" :loading="loading" :pagination="false" row-key="id" :default-expand-all-rows="true" :children-column-name="'children'">
        <template #bodyCell="{ column, record }">
          <template v-if="column.key === 'type'"><a-tag :color="record.type === '目录' ? 'blue' : record.type === '菜单' ? 'green' : 'orange'">{{ record.type }}</a-tag></template>
          <template v-if="column.key === 'status'"><a-tag :color="record.status === '启用' ? 'success' : 'default'">{{ record.status }}</a-tag></template>
          <template v-if="column.key === 'actions'">
            <a-space><a @click="showModal(record)">编辑</a><a-popconfirm title="确定删除？" @confirm="handleDelete(record.id)"><a style="color: #ff4d4f">删除</a></a-popconfirm></a-space>
          </template>
        </template>
      </a-table>
    </a-card>
    <a-modal v-model:open="modalVisible" :title="editId ? '编辑菜单' : '新增菜单'" @ok="handleSave" :confirm-loading="saveLoading">
      <a-form :model="formData" :label-col="{ span: 6 }" :wrapper-col="{ span: 16 }">
        <a-form-item label="菜单类型" required>
          <a-radio-group v-model:value="formData.type"><a-radio value="目录">目录</a-radio><a-radio value="菜单">菜单</a-radio><a-radio value="按钮">按钮</a-radio></a-radio-group>
        </a-form-item>
        <a-form-item label="菜单名称" required><a-input v-model:value="formData.name" /></a-form-item>
        <a-form-item label="上级菜单">
          <a-tree-select v-model:value="formData.parentId" :tree-data="dataSource" placeholder="请选择" allow-clear :field-names="{ label: 'name', value: 'id', children: 'children' }" />
        </a-form-item>
        <a-form-item v-if="formData.type !== '按钮'" label="路由路径"><a-input v-model:value="formData.path" /></a-form-item>
        <a-form-item v-if="formData.type === '菜单'" label="组件路径"><a-input v-model:value="formData.component" /></a-form-item>
        <a-form-item label="权限标识"><a-input v-model:value="formData.permission" /></a-form-item>
        <a-form-item label="排序"><a-input-number v-model:value="formData.sort" :min="0" style="width: 100%" /></a-form-item>
        <a-form-item label="状态"><a-radio-group v-model:value="formData.status"><a-radio value="启用">启用</a-radio><a-radio value="禁用">禁用</a-radio></a-radio-group></a-form-item>
      </a-form>
    </a-modal>
  </div>
</template>

<script setup lang="ts">
import { ref, reactive, onMounted } from 'vue'
import { message } from 'ant-design-vue'
import { PlusOutlined } from '@ant-design/icons-vue'
import { getMenuTree, createMenu, updateMenu, deleteMenu } from '@/api/system'

const loading = ref(false); const saveLoading = ref(false); const modalVisible = ref(false)
const dataSource = ref<any[]>([]); const editId = ref<number | null>(null)
const formData = reactive({ type: '菜单', name: '', parentId: undefined as number | undefined, path: '', component: '', permission: '', sort: 0, status: '启用' })
const columns = [
  { title: '菜单名称', dataIndex: 'name', key: 'name', width: 200 },
  { title: '图标', dataIndex: 'icon', key: 'icon', width: 100 },
  { title: '类型', dataIndex: 'type', key: 'type', width: 80 },
  { title: '路由路径', dataIndex: 'path', key: 'path', width: 180 },
  { title: '权限标识', dataIndex: 'permission', key: 'permission', width: 160 },
  { title: '排序', dataIndex: 'sort', key: 'sort', width: 80 },
  { title: '状态', dataIndex: 'status', key: 'status', width: 80 },
  { title: '操作', key: 'actions', width: 120 }
]
async function fetchData() {
  loading.value = true
  try { const res = await getMenuTree(); dataSource.value = (res as any).data || [] }
  catch { dataSource.value = [] } finally { loading.value = false }
}
function showModal(record?: any) {
  editId.value = record?.id || null
  Object.assign(formData, record ? { type: record.type, name: record.name, parentId: record.parentId, path: record.path, component: record.component, permission: record.permission, sort: record.sort, status: record.status } : { type: '菜单', name: '', parentId: undefined, path: '', component: '', permission: '', sort: 0, status: '启用' })
  modalVisible.value = true
}
async function handleSave() {
  saveLoading.value = true
  try { if (editId.value) await updateMenu(editId.value, formData); else await createMenu(formData); message.success('保存成功'); modalVisible.value = false; fetchData() }
  finally { saveLoading.value = false }
}
async function handleDelete(id: number) { await deleteMenu(id); message.success('删除成功'); fetchData() }
onMounted(() => fetchData())
</script>
