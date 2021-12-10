<template>
  <el-container>
    <el-header style="font-size: 30px; text-align: center">注册用户</el-header>
    <el-main>
      <el-row type="flex" justify="center">
        <el-col :span="6">
          <el-form
            @submit.native.prevent
            :model="registerForm"
            :rules="rules"
            status-icon
            ref="registerForm"
            v-loading="loading"
          >
            <el-form-item label="用户名" prop="id">
              <el-input v-model="registerForm.id"></el-input>
            </el-form-item>
            <el-form-item label="密码" prop="password">
              <el-input
                type="password"
                v-model="registerForm.password"
                autocomplete="off"
              ></el-input>
            </el-form-item>
            <el-form-item>
              <el-button
                native-type="submit"
                type="primary"
                @click="submitForm('registerForm')"
                style="width: 25%"
              >
                Register in
              </el-button>
              <el-button
                @click="$router.back(-1)"
                style="width: 25%; float: right"
              >
                BACK
              </el-button>
            </el-form-item>
          </el-form>
        </el-col>
      </el-row>
    </el-main>
  </el-container>
</template>

<script>

export default {
  name: 'Register',
  inject: ['reload'],
  data () {
    return {
      registerForm: {
        name: '',
        id: '',
        password: ''
      },
      rules: {
        name: { required: true, message: 'Please enter your name', blur: 'change' },
        id: { required: true, message: 'Please enter your id', blur: 'change' },
        password: { required: true, message: 'Please enter password', blur: 'change' }
      },
      loading: false
    }
  },
  methods: {
    submitForm (formName) {
      this.loading = true
      this.$axios
        .post('/register', {
          id: this.registerForm.id,
          password: this.registerForm.password
        })
        .then((resp) => {
          if (resp.status === 200 && resp.data.hasOwnProperty('user')) {
            this.$store.commit('register', resp.data.user)
            this.$message({
              type: 'success',
              message: 'Register success. Welcome to log in!'
            })
            this.$ruoter.push('/register')
            this.reload()
          }
        })
        // eslint-disable-next-line handle-callback-err
        .catch((error) => {
          this.$message({
            tyep: 'error',
            message: 'Service is not available. Please try later'
          })
          this.loading = false
        })
    }
  }
}
</script>

<style>

</style>
