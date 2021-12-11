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
                <a href="/registerInfo" type="primary">没有账户？现在注册</a>
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
          // 这里的对象属性判断可以做路由分支处理（在后端的map数据结构中put不同的key）
          if (resp.status === 200 && resp.data.hasOwnProperty('user')) {
            // Save user info
            this.$store.commit('login', resp.data.user)
            this.$message({
              type: 'success',
              message: '欢迎登录'
            })
            // 需要修改，根据返回数据push不同的路径
            // this.$router.push('/patientDataPanel')
            this.$router.push('/userInfo')
            this.reload()
          }
        })
        .catch((error) => {
          console.log(error)
          if (error.message === 'Request failed with status code 403') {
            this.$message({
              type: 'error',
              message: '用户名或密码错误，请重试。'
            })
            this.loading = false
          } else {
            this.$message({
              type: 'error',
              message: '服务暂时不可用，请稍后再试。'
            })
            this.loading = false
          }
        })
    }
  }
}
</script>

<style>
</style>
