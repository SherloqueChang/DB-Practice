<template>
  <el-container>
    <el-header style="font-size: 30px; text-align: center">注册用户</el-header>
    <el-main>
      <el-row type="flex" justify="center">
        <el-col :span="6">
          <el-form
            @submit.native.prevent
            :model="registerInfoForm"
            :rules="rules"
            status-icon
            ref="registerInfoForm"
            v-loading="loading"
          >
            <el-form-item label="姓名" prop="name">
              <el-input v-model="registerInfoForm.name"></el-input>
            </el-form-item>
            <el-form-item label="出生日期" prop="birthdate"><br/>
              <div class="block">
                <el-date-picker
                  value-format="yyyy-MM-dd"
                  v-model="registerInfoForm.birthdate"
                  type="date"
                  placeholder="选择日期"
                  style="width: 100%">
                </el-date-picker>
              </div>
            </el-form-item>
            <el-form-item label="身份证号" prop="idcard">
              <el-input v-model="registerInfoForm.idcard"></el-input>
            </el-form-item>
            <el-form-item label="性别" prop="gender"><br/>
              <el-select v-model="registerInfoForm.gender" placeholder="选择性别" style="width: 100%">
                <el-option
                  v-for="item in options"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value">
                </el-option>
              </el-select>
            </el-form-item>
            <el-form-item label="电话" prop="phone">
              <el-input v-model="registerInfoForm.phone"></el-input>
            </el-form-item>
            <el-form-item label="电子邮件" prop="email">
              <el-input v-model="registerInfoForm.email"></el-input>
            </el-form-item>
            <el-form-item>
              <el-button
                native-type="submit"
                type="primary"
                @click="submitForm('registerInfoForm')"
                style="width: 25%"
              >提交
              </el-button>
              <el-button
                @click="$router.back(-1)"
                style="width: 25%; float: right"
              >
                返回
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
  name: 'RegisterInfo',
  inject: ['reload'],
  data () {
    return {
      registerInfoForm: {
        name: '',
        birthdate: null,
        idNumber: '',
        gender: '',
        phone: '',
        email: ''
      },
      options: [
        {
          value: 'male',
          label: '男'
        },
        {
          value: 'female',
          label: '女'
        }
      ],
      rules: {
        name: { required: true, message: '请输入姓名', blur: 'change' },
        birthdate: { required: true, message: '请输入出生日期', blur: 'change' },
        idcard: { required: true, message: '请输入身份证号', blur: 'change' },
        gender: { required: true, message: '请输入性别', blur: 'change' },
        phone: { required: true, message: '请输入电话号码', blur: 'change' },
        email: { required: false, blur: 'change' }
      },
      loading: false
    }
  },
  methods: {
    submitForm (formName) {
      this.loading = true
      this.$axios
        .post('/registerInfo', {
          id: this.$route.params.id,
          name: this.registerInfoForm.name,
          birthdate: this.registerInfoForm.birthdate,
          idcard: this.registerInfoForm.idcard,
          gender: this.registerInfoForm.gender,
          phone: this.registerInfoForm.phone,
          email: this.registerInfoForm.email
        })
        .then((resp) => {
          if (resp.status === 200) {
            this.$store.commit('registerInfo', resp.data.user)
            this.$message({
              type: 'success',
              message: '提交成功，欢迎登录！'
            })
            this.$ruoter.push('/login')
            this.reload()
          }
        })
        // eslint-disable-next-line handle-callback-err
        .catch((error) => {
          this.$message({
            tyep: 'error',
            message: '服务不可用，请稍后再试'
          })
          this.loading = false
        })
    }
  }
}
</script>

<style>

</style>
