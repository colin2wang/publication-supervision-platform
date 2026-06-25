<template>
  <a-layout style="min-height: 100vh">
    <a-layout-sider
      v-model:collapsed="appStore.sidebarCollapsed"
      collapsible
      :trigger="null"
      theme="dark"
      :width="220"
      :collapsed-width="80"
    >
      <div class="logo">
        <h1 v-if="!appStore.sidebarCollapsed">出版物监管</h1>
        <h1 v-else>出</h1>
      </div>
      <a-menu
        v-model:selectedKeys="selectedKeys"
        v-model:openKeys="openKeys"
        theme="dark"
        mode="inline"
        @click="handleMenuClick"
      >
        <a-menu-item key="/dashboard">
          <template #icon><DashboardOutlined /></template>
          <span>工作台</span>
        </a-menu-item>

        <a-sub-menu key="identification">
          <template #icon><FileSearchOutlined /></template>
          <template #title>鉴定管理</template>
          <a-menu-item key="/identification/tasks">鉴定任务</a-menu-item>
          <a-menu-item key="/identification/publications">出版物管理</a-menu-item>
          <a-menu-item key="/identification/samples">样本管理</a-menu-item>
          <a-menu-item key="/identification/workbench">鉴定工作台</a-menu-item>
          <a-menu-item key="/identification/datasets">数据集管理</a-menu-item>
        </a-sub-menu>

        <a-sub-menu key="circulation">
          <template #icon><ShopOutlined /></template>
          <template #title>流转管理</template>
          <a-menu-item key="/circulation/dashboard">流转监控</a-menu-item>
          <a-menu-item key="/circulation/merchants">商户管理</a-menu-item>
          <a-menu-item key="/circulation/qualifications">资质审核</a-menu-item>
          <a-menu-item key="/circulation/packages">包裹监管</a-menu-item>
          <a-menu-item key="/circulation/alerts">预警管理</a-menu-item>
          <a-menu-item key="/circulation/lists">黑白名单</a-menu-item>
        </a-sub-menu>

        <a-sub-menu key="opinion">
          <template #icon><AlertOutlined /></template>
          <template #title>舆情管理</template>
          <a-menu-item key="/opinion/collection-tasks">采集任务</a-menu-item>
          <a-menu-item key="/opinion/opinions">舆情信息</a-menu-item>
          <a-menu-item key="/opinion/analysis">舆情分析</a-menu-item>
          <a-menu-item key="/opinion/events">事件管理</a-menu-item>
          <a-menu-item key="/opinion/reports">报告管理</a-menu-item>
          <a-menu-item key="/opinion/effectiveness">舆情效果</a-menu-item>
        </a-sub-menu>

        <a-sub-menu key="ai">
          <template #icon><RobotOutlined /></template>
          <template #title>AI中心</template>
          <a-menu-item key="/ai/knowledge-bases">知识库管理</a-menu-item>
          <a-menu-item key="/ai/models">模型管理</a-menu-item>
          <a-menu-item key="/ai/agents">智能体管理</a-menu-item>
          <a-menu-item key="/ai/digital-humans">数字人管理</a-menu-item>
        </a-sub-menu>

        <a-sub-menu key="system">
          <template #icon><SettingOutlined /></template>
          <template #title>系统管理</template>
          <a-menu-item key="/system/users">用户管理</a-menu-item>
          <a-menu-item key="/system/roles">角色管理</a-menu-item>
          <a-menu-item key="/system/menus">菜单管理</a-menu-item>
        </a-sub-menu>
      </a-menu>
    </a-layout-sider>

    <a-layout>
      <a-layout-header class="layout-header">
        <div class="header-left">
          <MenuUnfoldOutlined v-if="appStore.sidebarCollapsed" class="trigger" @click="appStore.toggleSidebar" />
          <MenuFoldOutlined v-else class="trigger" @click="appStore.toggleSidebar" />
          <a-breadcrumb style="margin-left: 16px">
            <a-breadcrumb-item>首页</a-breadcrumb-item>
            <a-breadcrumb-item v-if="currentRoute?.meta?.title">{{ currentRoute.meta.title }}</a-breadcrumb-item>
          </a-breadcrumb>
        </div>
        <div class="header-right">
          <a-badge :count="5" class="notification-badge">
            <BellOutlined class="header-icon" />
          </a-badge>
          <a-dropdown>
            <div class="user-info">
              <a-avatar size="small" style="background-color: #1890ff">
                {{ userStore.userInfo?.realName?.charAt(0) || 'U' }}
              </a-avatar>
              <span class="username">{{ userStore.userInfo?.realName || '用户' }}</span>
            </div>
            <template #overlay>
              <a-menu>
                <a-menu-item key="profile">个人中心</a-menu-item>
                <a-menu-divider />
                <a-menu-item key="logout" @click="handleLogout">退出登录</a-menu-item>
              </a-menu>
            </template>
          </a-dropdown>
        </div>
      </a-layout-header>

      <a-layout-content class="layout-content">
        <router-view />
      </a-layout-content>
    </a-layout>
  </a-layout>
</template>

<script setup lang="ts">
import { ref, computed, watch } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { useAppStore } from '@/store/app'
import { useUserStore } from '@/store/user'
import {
  DashboardOutlined,
  FileSearchOutlined,
  ShopOutlined,
  AlertOutlined,
  RobotOutlined,
  SettingOutlined,
  MenuFoldOutlined,
  MenuUnfoldOutlined,
  BellOutlined
} from '@ant-design/icons-vue'

const route = useRoute()
const router = useRouter()
const appStore = useAppStore()
const userStore = useUserStore()

const currentRoute = computed(() => route)
const selectedKeys = ref<string[]>([route.path])
const openKeys = ref<string[]>([])

watch(() => route.path, (path) => {
  selectedKeys.value = [path]
  const segments = path.split('/')
  if (segments.length > 2) {
    openKeys.value = [segments[1]]
  }
})

const handleMenuClick = ({ key }: { key: string }) => {
  router.push(key)
}

const handleLogout = async () => {
  await userStore.logout()
}
</script>

<style scoped>
.logo {
  height: 64px;
  display: flex;
  align-items: center;
  justify-content: center;
  background: rgba(255, 255, 255, 0.1);
  margin: 0;
}

.logo h1 {
  color: #fff;
  font-size: 18px;
  margin: 0;
  white-space: nowrap;
  overflow: hidden;
}

.layout-header {
  background: #fff;
  padding: 0 24px;
  display: flex;
  align-items: center;
  justify-content: space-between;
  box-shadow: 0 1px 4px rgba(0, 0, 0, 0.08);
  z-index: 10;
}

.header-left {
  display: flex;
  align-items: center;
}

.trigger {
  font-size: 18px;
  cursor: pointer;
  transition: color 0.3s;
  padding: 0 12px;
}

.trigger:hover {
  color: #1890ff;
}

.header-right {
  display: flex;
  align-items: center;
  gap: 24px;
}

.notification-badge {
  cursor: pointer;
}

.header-icon {
  font-size: 18px;
  cursor: pointer;
}

.user-info {
  display: flex;
  align-items: center;
  gap: 8px;
  cursor: pointer;
  padding: 0 8px;
}

.username {
  font-size: 14px;
  color: #333;
}

.layout-content {
  margin: 24px;
  padding: 24px;
  background: #fff;
  border-radius: 4px;
  min-height: calc(100vh - 64px - 48px);
}
</style>
