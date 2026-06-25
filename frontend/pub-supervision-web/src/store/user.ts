import { defineStore } from 'pinia'
import { ref, computed } from 'vue'
import type { UserInfo } from '@/types/api'
import { getToken, setToken, removeToken } from '@/utils/auth'
import { login as loginApi, logout as logoutApi, getUserInfo } from '@/api/auth'
import router from '@/router'

export const useUserStore = defineStore('user', () => {
  const token = ref<string>(getToken() || '')
  const userInfo = ref<UserInfo | null>(null)

  const isLoggedIn = computed(() => !!token.value)

  async function login(data: { username: string; password: string; captcha: string; captchaKey: string }) {
    const res = await loginApi(data)
    token.value = res.token
    setToken(res.token)
    await fetchUserInfo()
  }

  async function fetchUserInfo() {
    const res = await getUserInfo()
    userInfo.value = res
  }

  async function logout() {
    try {
      await logoutApi()
    } finally {
      token.value = ''
      userInfo.value = null
      removeToken()
      router.push('/login')
    }
  }

  return {
    token,
    userInfo,
    isLoggedIn,
    login,
    fetchUserInfo,
    logout
  }
})
