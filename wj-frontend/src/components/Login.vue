<template>
  <div class="login-container">
    <el-form
      class="login"
      label="登录"
      label-position="left"
    >
      <h3 class="login__title">
        系统登录
      </h3>
      <el-form-item>
        <el-input
          v-model="loginForm.username"
          type="text"
          auto-complete="off"
          placeholder="账号"
        />
      </el-form-item>
      <el-form-item>
        <el-input
          v-model="loginForm.password"
          type="password"
          auto-complete="off"
          placeholder="密码"
        />
      </el-form-item>
      <el-form-item>
        <el-button
          type="primary"
          style="background: #505458;border: none"
          @click="login"
        >
          登录
        </el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
export default {
  name: 'Login',
  data () {
    return {
      loginForm: {
        username: 'admin',
        password: ''
      },
      responseResult: []
    }
  },
  methods: {
    login () {
      this.$axios
        .post('/login', {
          username: this.loginForm.username,
          password: this.loginForm.password
        })
        .then(successResponse => {
          if (successResponse.data.code === 200) {
            this.$store.commit('login', this.loginForm)
            const path = this.$route.query.redirect
            this.$router.replace({ path: path === '/' || path === undefined ? '/index' : path })
          } else { console.log(successResponse.data.code) }
        })
        .catch(failResponse => {
        })
    }
  }
}
</script>

<style scoped lang="less">
.login-container {
  width: 100%;
  height: 100%;
  position: absolute;
  top: 0;
  background:url("../assets/eva.jpg") no-repeat;
  background-position: center;
  background-size: cover;
  /* 设置元素的背景（背景图片或颜色）是否延伸到边框、内边距盒子、内容盒子下面。 */
  background-clip: padding-box;
  display: flex;
  justify-content: center;
  align-items: center;

  .login {
    border-radius: 15px;
    width: 350px;
    padding: 35px 35px 15px 35px;
    background-color: #fff;
    border: 1px solid #eaeaea;
    box-shadow: 0 0 25px #cac6c6;
  }

  .login__title {
    margin: 0px auto 40px auto;
    text-align: center;
    color: #505458;
  }
}

</style>
