<template>
  <div class="login-container">
    <div class="login-card">
      <div class="login-header">
        <h1>出版物智能监管一体化平台</h1>
        <p>Publication Supervision Platform</p>
      </div>
      <a-form
        :model="formState"
        :rules="rules"
        @finish="handleLogin"
        layout="vertical"
        class="login-form"
      >
        <a-form-item name="username">
          <a-input
            v-model:value="formState.username"
            size="large"
            placeholder="请输入用户名"
          >
            <template #prefix><UserOutlined /></template>
          </a-input>
        </a-form-item>
        <a-form-item name="password">
          <a-input-password
            v-model:value="formState.password"
            size="large"
            placeholder="请输入密码"
          >
            <template #prefix><LockOutlined /></template>
          </a-input-password>
        </a-form-item>
        <a-form-item name="captcha">
          <div class="captcha-row">
            <a-input
              v-model:value="formState.captcha"
              size="large"
              placeholder="请输入验证码"
              style="flex: 1"
            >
              <template #prefix><SafetyOutlined /></template>
            </a-input>
            <div class="captcha-img" @click="refreshCaptcha">
              <img v-if="captchaImage" :src="captchaImage" alt="验证码" />
              <span v-else>获取验证码</span>
            </div>
          </div>
        </a-form-item>
        <a-form-item>
          <div class="login-options">
            <a-checkbox v-model:checked="rememberMe">记住我</a-checkbox>
            <a-link>忘记密码?</a-link>
          </div>
        </a-form-item>
        <a-form-item>
          <a-button
            type="primary"
            html-type="submit"
            size="large"
            block
            :loading="loading"
          >
            登录
          </a-button>
        </a-form-item>
      </a-form>
      <div class="login-footer">
        <p>Copyright &copy; 2026 出版物智能监管平台 All Rights Reserved</p>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, reactive, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { message } from 'ant-design-vue'
import { UserOutlined, LockOutlined, SafetyOutlined } from '@ant-design/icons-vue'
import { useUserStore } from '@/store/user'
import { getCaptcha } from '@/api/auth'

const router = useRouter()
const userStore = useUserStore()

const loading = ref(false)
const rememberMe = ref(true)
const captchaImage = ref('')
const formState = reactive({
  username: '',
  password: '',
  captcha: '',
  captchaKey: ''
})

const rules = {
  username: [{ required: true, message: '请输入用户名', trigger: 'blur' }],
  password: [{ required: true, message: '请输入密码', trigger: 'blur' }],
  captcha: [{ required: true, message: '请输入验证码', trigger: 'blur' }]
}

const refreshCaptcha = async () => {
  try {
    const res = await getCaptcha()
    captchaImage.value = res.image
    formState.captchaKey = res.uuid
  } catch {
    // ignore
  }
}

const handleLogin = async () => {
  loading.value = true
  try {
    await userStore.login({
      username: formState.username,
      password: formState.password,
      captcha: formState.captcha,
      captchaKey: formState.captchaKey
    })
    message.success('登录成功')
    router.push('/')
  } catch {
    refreshCaptcha()
  } finally {
    loading.value = false
  }
}

onMounted(() => {
  refreshCaptcha()
})
</script>

<style scoped>
.login-container {
  width: 100vw;
  height: 100vh;
  display: flex;
  align-items: center;
  justify-content: center;
  background: linear-gradient(135deg, #1890ff 0%, #096dd9 50%, #003a8c 100%);
}

.login-card {
  width: 400px;
  padding: 40px;
  background: #fff;
  border-radius: 8px;
  box-shadow: 0 8px 24px rgba(0, 0, 0, 0.15);
}

.login-header {
  text-align: center;
  margin-bottom: 32px;
}

.login-header h1 {
  font-size: 22px;
  color: #333;
  margin-bottom: 8px;
}

.login-header p {
  font-size: 13px;
  color: #999;
}

.login-form {
  width: 100%;
}

.captcha-row {
  display: flex;
  gap: 12px;
}

.captcha-img {
  width: 120px;
  height: 40px;
  border: 1px solid #d9d9d9;
  border-radius: 4px;
  display: flex;
  align-items: center;
  justify-content: center;
  cursor: pointer;
  overflow: hidden;
  flex-shrink: 0;
}

.captcha-img img {
  width: 100%;
  height: 100%;
  object-fit: cover;
}

.captcha-img span {
  font-size: 12px;
  color: #999;
}

.login-options {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.login-footer {
  text-align: center;
  margin-top: 24px;
}

.login-footer p {
  font-size: 12px;
  color: #999;
}
</style>
