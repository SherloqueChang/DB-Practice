<template>
  <el-container>
    <el-header style="font-size: 30px; text-align: center">WeiMing Hospital</el-header>
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
            <el-form-item label="User ID" prop="id">
              <el-input v-model="loginForm.id"></el-input>
            </el-form-item>
            <el-form-item label="Password" prop="password">
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
                Log in
              </el-button>
              <p class="tips" style="float: right">
                <a href="/register" type="primary">No account？Register now</a>
              </p>
            </el-form-item>
          </el-form>
        </el-col>
      </el-row>
    </el-main>
  </el-container>
</template>

<script>
// import navmenu from '../components/Nav.vue'

export default {
  name: 'Login',
  // components: { navmenu },
  inject: ['reload'],
  data () {
    return {
      loginForm: {
        id: '',
        password: ''
      },
      rules: {
        id: { required: true, message: 'Please enter user id', blur: 'change' },
        password: { required: true, message: 'Please enter password', blur: 'change' }
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
          if (resp.status === 200 && resp.data.hasOwnProperty('user')) {
            // Save user info
            this.$store.commit('login', resp.data.user)
            this.$message({
              type: 'success',
              message: 'Welcome to log in！'
            })
            // 需要修改，根据返回数据push不同的路径
            this.$router.push('/patientDataPanel')
            this.reload()
          }
        })
        .catch((error) => {
          console.log(error)
          if (error.message === 'Request failed with status code 403') {
            this.$message({
              type: 'error',
              message: '用户id或密码错误，请重试。'
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
