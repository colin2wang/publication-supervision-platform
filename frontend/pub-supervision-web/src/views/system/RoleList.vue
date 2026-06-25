<template>
  <div>
    <a-card :bordered="false">
      <template #extra><a-button type="primary" @click="showModal()"><template #icon><PlusOutlined /></template>新增角色</a-button></template>
      <a-table :columns="columns" :data-source="dataSource" :loading="loading" :pagination="pagination" @change="handleTableChange" row-key="id">
        <template #bodyCell="{ column, record }">
          <template v-if="column.key === 'actions'">
            <a-space><a @click="showModal(record)">编辑</a><a-popconfirm title="确定删除？" @confirm="handleDelete(record.id)"><a style="color: #ff4d4f">删除</a></a-popconfirm></a-space>
          </template>
        </template>
      </a-table>
    </a-card>
    <a-modal v-model:open="modalVisible" :title="editId ? '编辑角色' : '新增角色'" @ok="handleSave" :confirm-loading="saveLoading" width="600px">
      <a-form :model="formData" :label-col="{ span: 6 }" :wrapper-col="{ span: 16 }">
        <a-form-item label="角色名称" required><a-input v-model:value="formData.roleName" /></a-form-item>
        <a-form-item label="角色标识" required><a-input v-model:value="formData.roleCode" :disabled="!!editId" /></a-form-item>
        <a-form-item label="描述"><a-textarea v-model:value="formData.description" :rows="3" /></a-form-item>
        <a-form-item label="菜单权限">
          <a-tree
            v-model:checkedKeys="formData.menuIds"
            :tree-data="menuTree"
            checkable
            :field-names="{ title: 'name', key: 'id', children: 'children' }"
            :default-expand-all="true"
          />
        </a-form-item>
      </a-form>
    </a-modal>
  </div>
</template>

<script setup lang="ts">
import { ref, reactive, onMounted } from 'vue'
import { message } from 'ant-design-vue'
import { PlusOutlined } from '@ant-design/icons-vue'
import { getRoles, createRole, updateRole, deleteRole, getMenuTree } from '@/api/system'

const loading = ref(false); const saveLoading = ref(false); const modalVisible = ref(false)
const dataSource = ref<any[]>([]); const editId = ref<number | null>(null); const menuTree = ref<any[]>([])
const formData = reactive({ roleName: '', roleCode: '', description: '', menuIds: [] as number[] })
const pagination = reactive({ current: 1, pageSize: 10, total: 0, showSizeChanger: true, showTotal: (t: number) => `共 ${t} 条` })
const columns = [
  { title: '角色名称', dataIndex: 'roleName', key: 'roleName', width: 150 },
  { title: '角色标识', dataIndex: 'roleCode', key: 'roleCode', width: 150 },
  { title: '描述', dataIndex: 'description', key: 'description', width: 200 },
  { title: '创建时间', dataIndex: 'createTime', key: 'createTime', width: 180 },
  { title: '操作', key: 'actions', width: 120 }
]
async function fetchData() {
  loading.value = true
  try { const res = await getRoles({ current: pagination.current, size: pagination.pageSize }); dataSource.value = res.records; pagination.total = res.total }
  catch { dataSource.value = [] } finally { loading.value = false }
}
async function fetchMenuTree() {
  try { const res = await getMenuTree(); menuTree.value = (res as any).data || [] } catch { menuTree.value = [] }
}
function handleTableChange(pag: any) { pagination.current = pag.current; pagination.pageSize = pag.pageSize; fetchData() }
function showModal(record?: any) {
  editId.value = record?.id || null
  Object.assign(formData, record ? { roleName: record.roleName, roleCode: record.roleCode, description: record.description, menuIds: record.menuIds || [] } : { roleName: '', roleCode: '', description: '', menuIds: [] })
  modalVisible.value = true
}
async function handleSave() {
  saveLoading.value = true
  try { if (editId.value) await updateRole(editId.value, formData); else await createRole(formData); message.success('保存成功'); modalVisible.value = false; fetchData() }
  finally { saveLoading.value = false }
}
async function handleDelete(id: number) { await deleteRole(id); message.success('删除成功'); fetchData() }
onMounted(() => { fetchData(); fetchMenuTree() })
</script>
