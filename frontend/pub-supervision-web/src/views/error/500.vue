<template>
  <div class="error-page">
    <div class="error-content">
      <h1 class="error-code">500</h1>
      <h2 class="error-title">抱歉，服务器出现了问题</h2>
      <p class="error-desc">服务器内部错误，请稍后再试，或联系技术支持</p>
      <a-button type="primary" @click="$router.push('/')">返回首页</a-button>
    </div>
  </div>
</template>

<style scoped>
.error-page {
  height: 100vh;
  display: flex;
  align-items: center;
  justify-content: center;
  background: #f0f2f5;
}
.error-content {
  text-align: center;
}
.error-code {
  font-size: 120px;
  font-weight: bold;
  color: #ff4d4f;
  margin: 0;
  line-height: 1;
}
.error-title {
  font-size: 24px;
  color: #333;
  margin: 24px 0 12px;
}
.error-desc {
  font-size: 14px;
  color: #999;
  margin-bottom: 24px;
}
</style>
