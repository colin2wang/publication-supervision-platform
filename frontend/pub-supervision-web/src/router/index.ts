import { createRouter, createWebHistory } from 'vue-router'
import type { RouteRecordRaw } from 'vue-router'
import NProgress from 'nprogress'
import 'nprogress/nprogress.css'
import { getToken } from '@/utils/auth'

NProgress.configure({ showSpinner: false })

const routes: RouteRecordRaw[] = [
  {
    path: '/login',
    name: 'Login',
    component: () => import('@/views/login/index.vue'),
    meta: { title: '登录' }
  },
  {
    path: '/',
    component: () => import('@/components/MainLayout.vue'),
    redirect: '/dashboard',
    children: [
      {
        path: 'dashboard',
        name: 'Dashboard',
        component: () => import('@/views/dashboard/index.vue'),
        meta: { title: '工作台' }
      },
      {
        path: 'identification/tasks',
        name: 'TaskList',
        component: () => import('@/views/identification/TaskList.vue'),
        meta: { title: '鉴定任务' }
      },
      {
        path: 'identification/tasks/:id',
        name: 'TaskDetail',
        component: () => import('@/views/identification/TaskDetail.vue'),
        meta: { title: '任务详情' },
        props: true
      },
      {
        path: 'identification/publications',
        name: 'PublicationList',
        component: () => import('@/views/identification/PublicationList.vue'),
        meta: { title: '出版物管理' }
      },
      {
        path: 'identification/samples',
        name: 'SampleList',
        component: () => import('@/views/identification/SampleList.vue'),
        meta: { title: '样本管理' }
      },
      {
        path: 'identification/workbench',
        name: 'Workbench',
        component: () => import('@/views/identification/Workbench.vue'),
        meta: { title: '鉴定工作台' }
      },
      {
        path: 'identification/datasets',
        name: 'DatasetList',
        component: () => import('@/views/identification/DatasetList.vue'),
        meta: { title: '数据集管理' }
      },
      {
        path: 'circulation/merchants',
        name: 'MerchantList',
        component: () => import('@/views/circulation/MerchantList.vue'),
        meta: { title: '商户管理' }
      },
      {
        path: 'circulation/qualifications',
        name: 'QualificationList',
        component: () => import('@/views/circulation/QualificationList.vue'),
        meta: { title: '资质审核' }
      },
      {
        path: 'circulation/packages',
        name: 'PackageList',
        component: () => import('@/views/circulation/PackageList.vue'),
        meta: { title: '包裹监管' }
      },
      {
        path: 'circulation/alerts',
        name: 'AlertList',
        component: () => import('@/views/circulation/AlertList.vue'),
        meta: { title: '预警管理' }
      },
      {
        path: 'circulation/lists',
        name: 'BlacklistList',
        component: () => import('@/views/circulation/BlacklistList.vue'),
        meta: { title: '黑白名单' }
      },
      {
        path: 'circulation/dashboard',
        name: 'CirculationDashboard',
        component: () => import('@/views/circulation/Dashboard.vue'),
        meta: { title: '流转监控' }
      },
      {
        path: 'opinion/collection-tasks',
        name: 'CollectionTaskList',
        component: () => import('@/views/opinion/CollectionTaskList.vue'),
        meta: { title: '采集任务' }
      },
      {
        path: 'opinion/opinions',
        name: 'OpinionList',
        component: () => import('@/views/opinion/OpinionList.vue'),
        meta: { title: '舆情信息' }
      },
      {
        path: 'opinion/analysis',
        name: 'OpinionAnalysis',
        component: () => import('@/views/opinion/OpinionAnalysis.vue'),
        meta: { title: '舆情分析' }
      },
      {
        path: 'opinion/events',
        name: 'EventList',
        component: () => import('@/views/opinion/EventList.vue'),
        meta: { title: '事件管理' }
      },
      {
        path: 'opinion/reports',
        name: 'ReportList',
        component: () => import('@/views/opinion/ReportList.vue'),
        meta: { title: '报告管理' }
      },
      {
        path: 'opinion/effectiveness',
        name: 'EffectivenessStats',
        component: () => import('@/views/opinion/EffectivenessStats.vue'),
        meta: { title: '舆情效果' }
      },
      {
        path: 'ai/knowledge-bases',
        name: 'KnowledgeBaseList',
        component: () => import('@/views/ai/KnowledgeBaseList.vue'),
        meta: { title: '知识库管理' }
      },
      {
        path: 'ai/models',
        name: 'ModelList',
        component: () => import('@/views/ai/ModelList.vue'),
        meta: { title: '模型管理' }
      },
      {
        path: 'ai/agents',
        name: 'AgentList',
        component: () => import('@/views/ai/AgentList.vue'),
        meta: { title: '智能体管理' }
      },
      {
        path: 'ai/digital-humans',
        name: 'DigitalHumanList',
        component: () => import('@/views/ai/DigitalHumanList.vue'),
        meta: { title: '数字人管理' }
      },
      {
        path: 'system/users',
        name: 'UserList',
        component: () => import('@/views/system/UserList.vue'),
        meta: { title: '用户管理' }
      },
      {
        path: 'system/roles',
        name: 'RoleList',
        component: () => import('@/views/system/RoleList.vue'),
        meta: { title: '角色管理' }
      },
      {
        path: 'system/menus',
        name: 'MenuList',
        component: () => import('@/views/system/MenuList.vue'),
        meta: { title: '菜单管理' }
      }
    ]
  },
  {
    path: '/403',
    name: 'Forbidden',
    component: () => import('@/views/error/403.vue'),
    meta: { title: '无权限' }
  },
  {
    path: '/500',
    name: 'ServerError',
    component: () => import('@/views/error/500.vue'),
    meta: { title: '服务器错误' }
  },
  {
    path: '/:pathMatch(.*)*',
    name: 'NotFound',
    component: () => import('@/views/error/404.vue'),
    meta: { title: '页面不存在' }
  }
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

router.beforeEach((to, _from, next) => {
  NProgress.start()
  const token = getToken()
  if (to.path === '/login') {
    if (token) {
      next('/')
    } else {
      next()
    }
  } else {
    if (!token) {
      next('/login')
    } else {
      next()
    }
  }
})

router.afterEach(() => {
  NProgress.done()
})

export default router
