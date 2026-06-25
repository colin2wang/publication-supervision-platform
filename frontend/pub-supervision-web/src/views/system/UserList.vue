<template>
  <div>
    <a-card :bordered="false" style="margin-bottom: 16px">
      <a-form :model="searchForm" layout="inline">
        <a-row :gutter="16">
          <a-col :span="6"><a-form-item label="用户名"><a-input v-model:value="searchForm.username" placeholder="请输入" allow-clear /></a-form-item></a-col>
          <a-col :span="6"><a-form-item label="真实姓名"><a-input v-model:value="searchForm.realName" placeholder="请输入" allow-clear /></a-form-item></a-col>
          <a-col :span="6"><a-form-item label="部门"><a-tree-select v-model:value="searchForm.departmentId" :tree-data="departmentTree" placeholder="请选择" allow-clear :field-names="{ label: 'name', value: 'id' }" /></a-form-item></a-col>
          <a-col :span="6"><a-space><a-button type="primary" @click="handleSearch">查询</a-button><a-button @click="handleReset">重置</a-button></a-space></a-col>
        </a-row>
      </a-form>
    </a-card>
    <a-card :bordered="false">
      <template #extra><a-button type="primary" @click="showModal()"><template #icon><PlusOutlined /></template>新增用户</a-button></template>
      <a-table :columns="columns" :data-source="dataSource" :loading="loading" :pagination="pagination" @change="handleTableChange" row-key="id">
        <template #bodyCell="{ column, record }">
          <template v-if="column.key === 'status'"><a-switch :checked="record.status === '启用'" checked-children="启用" un-checked-children="禁用" @change="(checked: boolean) => handleStatusChange(record.id, checked)" /></template>
          <template v-if="column.key === 'roles'">
            <a-tag v-for="role in record.roles" :key="role" color="blue">{{ role }}</a-tag>
          </template>
          <template v-if="column.key === 'actions'">
            <a-space><a @click="showModal(record)">编辑</a><a-popconfirm title="确定删除？" @confirm="handleDelete(record.id)"><a style="color: #ff4d4f">删除</a></a-popconfirm></a-space>
          </template>
        </template>
      </a-table>
    </a-card>
    <a-modal v-model:open="modalVisible" :title="editId ? '编辑用户' : '新增用户'" @ok="handleSave" :confirm-loading="saveLoading">
      <a-form :model="formData" :label-col="{ span: 6 }" :wrapper-col="{ span: 16 }">
        <a-form-item label="用户名" required><a-input v-model:value="formData.username" :disabled="!!editId" /></a-form-item>
        <a-form-item v-if="!editId" label="密码" required><a-input-password v-model:value="formData.password" /></a-form-item>
        <a-form-item label="真实姓名" required><a-input v-model:value="formData.realName" /></a-form-item>
        <a-form-item label="部门">
          <a-tree-select v-model:value="formData.departmentId" :tree-data="departmentTree" placeholder="请选择" allow-clear :field-names="{ label: 'name', value: 'id' }" />
        </a-form-item>
        <a-form-item label="角色" required>
          <a-select v-model:value="formData.roleIds" mode="multiple" placeholder="请选择">
            <a-select-option v-for="role in roleOptions" :key="role.id" :value="role.id">{{ role.roleName }}</a-select-option>
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
import { getUsers, createUser, updateUser, deleteUser, getRoles, getDepartmentTree } from '@/api/system'

const loading = ref(false); const saveLoading = ref(false); const modalVisible = ref(false)
const dataSource = ref<any[]>([]); const editId = ref<number | null>(null)
const departmentTree = ref<any[]>([]); const roleOptions = ref<any[]>([])
const searchForm = reactive({ username: '', realName: '', departmentId: undefined as any })
const formData = reactive({ username: '', password: '', realName: '', departmentId: undefined as any, roleIds: [] as number[] })
const pagination = reactive({ current: 1, pageSize: 10, total: 0, showSizeChanger: true, showTotal: (t: number) => `共 ${t} 条` })
const columns = [
  { title: '用户名', dataIndex: 'username', key: 'username', width: 120 },
  { title: '真实姓名', dataIndex: 'realName', key: 'realName', width: 120 },
  { title: '部门', dataIndex: 'departmentName', key: 'departmentName', width: 140 },
  { title: '状态', dataIndex: 'status', key: 'status', width: 100 },
  { title: '角色', dataIndex: 'roles', key: 'roles', width: 200 },
  { title: '操作', key: 'actions', width: 120, fixed: 'right' as const }
]

async function fetchData() {
  loading.value = true
  try { const res = await getUsers({ current: pagination.current, size: pagination.pageSize, ...searchForm }); dataSource.value = res.records; pagination.total = res.total }
  catch { dataSource.value = [] } finally { loading.value = false }
}
async function fetchOptions() {
  try { const res = await getRoles({ current: 1, size: 100 }); roleOptions.value = res.records } catch { roleOptions.value = [] }
  try { const res = await getDepartmentTree(); departmentTree.value = (res as any).data || [] } catch { departmentTree.value = [] }
}
function handleSearch() { pagination.current = 1; fetchData() }
function handleReset() { Object.assign(searchForm, { username: '', realName: '', departmentId: undefined }); handleSearch() }
function handleTableChange(pag: any) { pagination.current = pag.current; pagination.pageSize = pag.pageSize; fetchData() }
function showModal(record?: any) {
  editId.value = record?.id || null
  Object.assign(formData, record ? { username: record.username, realName: record.realName, departmentId: record.departmentId, roleIds: record.roleIds || [], password: '' } : { username: '', password: '', realName: '', departmentId: undefined, roleIds: [] })
  modalVisible.value = true
}
async function handleSave() {
  saveLoading.value = true
  try { if (editId.value) await updateUser(editId.value, formData); else await createUser(formData); message.success('保存成功'); modalVisible.value = false; fetchData() }
  finally { saveLoading.value = false }
}
async function handleDelete(id: number) { await deleteUser(id); message.success('删除成功'); fetchData() }
async function handleStatusChange(id: number, checked: boolean) {
  try { await updateUser(id, { status: checked ? '启用' : '禁用' }); message.success('状态已更新'); fetchData() }
  catch { /* ignore */ }
}
onMounted(() => { fetchData(); fetchOptions() })
</script>
