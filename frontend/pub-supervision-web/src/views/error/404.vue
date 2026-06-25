<template>
  <div class="error-page">
    <div class="error-content">
      <h1 class="error-code">404</h1>
      <h2 class="error-title">抱歉，您访问的页面不存在</h2>
      <p class="error-desc">请检查输入的URL是否正确，或点击下方按钮返回首页</p>
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
  color: #1890ff;
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
