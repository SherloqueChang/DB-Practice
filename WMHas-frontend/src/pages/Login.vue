<template>
  <el-container>
    <el-header style="font-size: 30px; text-align: center">未名医院</el-header>
    <el-main>
      <el-row type="flex" justify="center">
        <el-col :span="6">
          <el-form
            @submit.native.prevent
            :model="loginForm"
            :rules="rules"
            status-icon
            ref="loginForm"
            v-loading="loading"
          >
            <el-form-item label="用户名" prop="id">
              <el-input v-model="loginForm.id"></el-input>
            </el-form-item>
            <el-form-item label="密码" prop="password">
              <el-input
                type="password"
                v-model="loginForm.password"
                autocomplete="off"
              ></el-input>
            </el-form-item>
            <el-form-item>
<!--              还需要设置病人注册的按钮，即signinForm-->
              <el-button
                native-type="submit"
                type="primary"
                @click="submitForm('loginForm')"
                style="width: 25%"
                >
                登录
              </el-button>
              <p class="tips" style="float: right">
                <a href="/register" type="primary">没有账户？现在注册</a>
              </p>
            </el-form-item>
          </el-form>
        </el-col>
      </el-row>
    </el-main>
  </el-container>
</template>

<script>

export default {
  name: 'Login',
  inject: ['reload'],
  data () {
    return {
      loginForm: {
        id: '',
        password: ''
      },
      rules: {
        id: { required: true, message: '请输入用户名', blur: 'change' },
        password: { required: true, message: '请输入密码', blur: 'change' }
      },
      loading: false
    }
  },
  methods: {
    submitForm (formName) {
      this.loading = true
      this.$axios
        .post('/login', {
          id: this.loginForm.id,
          password: this.loginForm.password
        })
        .then((resp) => {
          if (resp.status === 200 && resp.data.hasOwnProperty('needinfo')) {
            // need more infomation, goto register info
            this.$store.commit('login', resp.data.user)
            this.$message({
              type: 'success',
              message: resp.data.needinfo
            })
            this.$router.push({
              name: 'RegisterInfo',
              params: {
                id: this.loginForm.id
              }
            })
            // this.reload()
          } else if (resp.status === 200 && resp.data.hasOwnProperty('user')) {
            // Save user info
            this.$store.commit('login', resp.data.user)
            this.$message({
              type: 'success',
              message: '欢迎登录'
            })
            this.$router.push('/userInfo')
            // this.reload()
          } else if (resp.status === 200 && resp.data.hasOwnProperty('error')) {
            this.$message({
              type: 'error',
              message: resp.data.error
            })
            this.loading = false
          }
        })
        .catch((error) => {
          console.log(error)
          this.$message({
            type: 'error',
            message: '服务暂时不可用，请稍后再试。'
          })
          this.loading = false
        })
    }
  }
}
</script>

<style>
</style>
