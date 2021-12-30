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
              :model="userInfo"
              :rules="rules"
              status-icon
              ref="userInfo"
            >
              <el-form-item label="密码" prop="password">
                <el-input
                  type="password"
                  v-model="userInfo.password"
                  autocomplete="off"
                ></el-input>
              </el-form-item>
              <el-form-item label="电话" prop="phone">
                <el-input v-model="userInfo.phone"></el-input>
              </el-form-item>
              <el-form-item label="Email" prop="email">
                <el-input v-model="userInfo.email"></el-input>
              </el-form-item>
              <el-form-item>
                <el-button
                  native-type="submit"
                  type="primary"
                  @click="submitForm('userInfo')"
                  >提交信息</el-button
                >
              </el-form-item>
            </el-form>
            <el-form v-if="isReading">
              <el-form-item label="用户名" prop="id">
                {{ this.userInfo.id }}
              </el-form-item>
              <el-form-item label="姓名" prop="name">
                {{ this.userInfo.name }}
              </el-form-item>
              <el-form-item label="出生日期">
                {{ this.userInfo.birthdate }}
              </el-form-item>
              <el-form-item label="身份证号">
                {{ this.userInfo.idcard }}
              </el-form-item>
              <el-form-item label="性别">
                {{ this.userInfo.gender }}
              </el-form-item>
              <el-form-item label="电话">
                {{ this.userInfo.phone }}
              </el-form-item>
              <el-form-item label="Email">
                {{ this.userInfo.email }}
              </el-form-item>
              <el-form-item label="身份">
                {{ this.parseUType(this.userInfo.u_type) }}
              </el-form-item>
              <el-form-item label="毕业院校" v-if="isDoctor">
                {{ this.userInfo.graduate_school }}
              </el-form-item><el-form-item label="部门" v-if="isDoctor">
                {{ this.userInfo.department }}
              </el-form-item><el-form-item label="技术职称" v-if="isDoctor">
                {{ this.userInfo.job_title }}
              </el-form-item><el-form-item label="专业特长" v-if="isDoctor">
                {{ this.userInfo.specialties }}
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
      isDoctor: false,
      userInfo: {
        id: '',
        name: '',
        birthdate: '',
        idcard: '',
        gender: '',
        phone: '',
        email: '',
        u_type: '',
        graduate_school: '',
        department: '',
        job_title: '',
        specialties: ''
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
        if (this.$route.params.u_type !== undefined && this.$route.params.u_type !== 'admin') {
          this.userInfo.id = this.$route.params.id,
          this.userInfo.name = this.$route.params.name,
          this.userInfo.birthdate = this.$route.params.birthdate,
          this.userInfo.idcard = this.$route.params.idcard,
          this.userInfo.gender = this.$route.params.gender,
          this.userInfo.phone = this.$route.params.phone,
          this.userInfo.email = this.$route.params.email,
          this.userInfo.u_type = this.$route.params.u_type,
          this.userInfo.graduate_school = this.$route.params.graduate_school,
          this.userInfo.department = this.$route.params.department,
          this.userInfo.job_title = this.$route.params.job_title,
          this.userInfo.specialties = this.$route.params.specialties
          this.isDoctor = (this.$route.params.u_type === 'doctor')
        }  else {
          // 管理员
          // this.userInfo.id = this.$store.state.user.id
          this.userInfo.id = 'admin'
          this.userInfo.name = this.$store.state.user.name
          this.userInfo.birthdate = this.$store.state.user.birthdate
          this.userInfo.idcard = this.$store.state.user.idcard
          this.userInfo.gender = this.$store.state.user.gender
          this.userInfo.phone = this.$store.state.user.phone
          this.userInfo.email = this.$store.state.user.email
          this.userInfo.u_type = this.$store.state.user.u_type
          this.userInfo.graduate_school = this.$store.state.user.graduate_school
          this.userInfo.department = this.$store.state.user.department
          this.userInfo.job_title = this.$store.state.user.job_title
          this.userInfo.specialties = this.$store.state.user.specialties
          this.isDoctor = (this.user.u_type === 'doctor' || this.user.u_type === 'leader')
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
          password: this.userInfo.password,
          email: this.userInfo.email,
          phone: this.userInfo.phone
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
