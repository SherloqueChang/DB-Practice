<template>
  <el-container>
    <el-header><NavHeader></NavHeader></el-header>
    <el-container>
      <el-aside><NavAside></NavAside></el-aside>
      <el-main>
        <el-row>
          <el-col :span="8">
            <el-form
              v-if="isUpdating"
              @submit.native.prevent
              :model="userInfoForm"
              :rules="rules"
              status-icon
              ref="userInfoForm"
            >
              <el-form-item label="密码" prop="password">
                <el-input
                  type="password"
                  v-model="userInfoForm.password"
                  autocomplete="off"
                ></el-input>
              </el-form-item>
              <el-form-item label="电话" prop="phone">
                <el-input v-model="userInfoForm.phone"></el-input>
              </el-form-item>
              <el-form-item label="Email" prop="email">
                <el-input v-model="userInfoForm.email"></el-input>
              </el-form-item>
              <el-form-item>
                <el-button
                  native-type="submit"
                  type="primary"
                  @click="submitForm('userInfoForm')"
                  >提交信息</el-button
                >
              </el-form-item>
            </el-form>
            <el-form v-if="isReading">
              <el-form-item label="用户名">
                {{ this.user.id }}
              </el-form-item>
              <el-form-item label="姓名">
                {{ this.user.name }}
              </el-form-item>
              <el-form-item label="出生日期">
                {{ this.user.birthdate }}
              </el-form-item>
              <el-form-item label="身份证号">
                {{ this.user.idcard }}
              </el-form-item>
              <el-form-item label="性别">
                {{ this.user.gender }}
              </el-form-item>
              <el-form-item label="电话">
                {{ this.user.phone }}
              </el-form-item>
              <el-form-item label="Email">
                {{ this.user.email }}
              </el-form-item>
              <el-form-item label="身份">
                {{ this.parseUType(this.user.u_type) }}
              </el-form-item>
              <el-form-item label="毕业院校" v-if="isDoctor">
                {{ this.user.graduate_school }}
              </el-form-item><el-form-item label="部门" v-if="isDoctor">
                {{ this.user.department }}
              </el-form-item><el-form-item label="技术职称" v-if="isDoctor">
                {{ this.user.job_title }}
              </el-form-item><el-form-item label="专业特长" v-if="isDoctor">
                {{ this.user.specialties }}
              </el-form-item>
              <el-form-item>
                <el-button @click="handleUpdate">修改信息</el-button>
              </el-form-item>
            </el-form>
          </el-col>
        </el-row>
      </el-main>
    </el-container>
  </el-container>
</template>

<script>
import NavHeader from '../components/NavHeader'
import NavAside from '../components/NavAside'

export default {
  name: 'UserInfo',
  components: { NavHeader, NavAside },
  inject: ['reload'],
  data () {
    return {
      user: [],
      isUpdating: false,
      isReading: true,
      isPatient: false,
      isDoctor: false,
      isLeader: false,

      userInfoForm: {
        password: '',
        phone: '',
        email: ''
      },
      rules: {
        password: { required: true, message: '请输入密码', blur: 'change' },
        phone: { required: true, message: '请输入电话', blur: 'change' },
        email: { required: true, message: '请输入Email', blur: 'change' }
      },
      loading: false
    }
  },
  created () {
    this.handleUserData()
  },
  methods: {
    handleUserData () {
      if (this.$store.state.user) {
        this.user = this.$store.state.user
        this.isPatient = this.$store.state.user.u_type === 'patient'
        this.isDoctor = this.$store.state.user.u_type === 'doctor'
        this.isLeader = this.$store.state.user.u_type === 'leader'
        this.isAdmin = this.$store.state.user.u_type === 'admin'
        if (this.user.u_type === 'admin') {
          this.user.id = this.$route.params.id
          this.isPatient = (this.$route.params.type === 'patient')
          this.isDoctor = (this.$route.params.type === 'doctor')
        }
      }
    },

    handleUpdate () {
      this.isReading = false
      this.isUpdating = true
    },

    parseUType (u_type) {
      switch (u_type) {
        case 'doctor':
          return '医生'
        case 'patient':
          return '患者'
        case 'leader':
          return '科长'
        case 'admin':
          return '管理员'
      }
    },

    submitForm (formName) {
      this.$axios
        .post('/modifyUserInfo', {
          id: this.user.id.toString(),
          password: this.userInfoForm.password,
          email: this.userInfoForm.email,
          phone: this.userInfoForm.phone
        }
        )
        .then((resp) => {
          if (resp.status === 200) {
            this.$message({
              type: 'success',
              message: '信息修改成功！'
            })
            this.isReading = true
            this.isUpdating = false
          }
        })
        .catch((error) => {
          console.log(error)
          this.$message.error('请求错误，请重试')
        })
    }
  }
}
</script>

<style>
</style>
