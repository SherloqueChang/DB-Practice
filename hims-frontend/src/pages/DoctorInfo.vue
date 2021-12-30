<template>
  <el-container>
    <el-header><NavHeader></NavHeader></el-header>
    <el-container>
      <el-aside><NavAside></NavAside></el-aside>
      <el-main>
        <el-row type="flex" justify="center">
          <el-col align="left">
            <el-page-header
              @back="goBack"
              :content="'医生详情：' + this.doctorInfoForm.name"
            >
            </el-page-header>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="8">
            <el-form>
              <el-form-item label="姓名">
                {{ this.doctorInfoForm.name }}
              </el-form-item>
              <el-form-item label="出生日期">
                {{ this.doctorInfoForm.birthdate }}
              </el-form-item>
              <el-form-item label="身份证号">
                {{ this.doctorInfoForm.idcard }}
              </el-form-item>
              <el-form-item label="性别">
                {{ this.doctorInfoForm.gender }}
              </el-form-item>
              <el-form-item label="电话">
                {{ this.doctorInfoForm.phone }}
              </el-form-item>
              <el-form-item label="Email">
                {{ this.doctorInfoForm.email }}
              </el-form-item>
              <el-form-item label="身份">
                {{ this.doctorInfoForm.u_type }}
              </el-form-item>
              <el-form-item label="毕业院校">
                {{ this.doctorInfoForm.gradSchool }}
              </el-form-item><el-form-item label="部门">
              {{ this.doctorInfoForm.department }}
            </el-form-item><el-form-item label="技术职称">
              {{ this.doctorInfoForm.techTitle }}
            </el-form-item><el-form-item label="专业特长">
              {{ this.doctorInfoForm.specialty }}
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
  name: 'DoctorInfo',
  components: { NavAside, NavHeader },
  data () {
    return {
      user: {},
      doctorInfoForm: {
        name: '',
        birthdate: '',
        idcard: '',
        gender: '',
        phone: '',
        email: '',
        u_type: '',
        gradSchool: '',
        department: '',
        techTitle: '',
        specialty: ''
      }
    }
  },
  created () {
    // this.handleUserData()
    this.loadDoctorData()
  },
  methods: {
    // handleUserData () {
    //   if (this.$store.state.user) {
    //     this.user = this.$store.state.user
    //   }
    // },
    goBack () {
      // this.$router.push('/appointmentOnline')
      this.$router.back(-1)
    },
    loadDoctorData () {
      this.$axios
        .get('/getDoctorInfo', {
          // 通过路由传参，获得医生的id
          params: { id: this.$route.params.d_id }
        })
        .then((resp) => {
          if (resp.status === 200) {
            this.doctorInfoForm.name = resp.data.doctor.name
            this.doctorInfoForm.birthdate = resp.data.doctor.birthdate
            this.doctorInfoForm.idcard = resp.data.doctor.idcard
            this.doctorInfoForm.gender = resp.data.doctor.gender
            this.doctorInfoForm.phone = resp.data.doctor.phone
            this.doctorInfoForm.email = resp.data.doctor.email
            this.doctorInfoForm.u_type = resp.data.doctor.u_type
            this.doctorInfoForm.gradSchool = resp.data.doctor.graduate_school
            this.doctorInfoForm.department = resp.data.doctor.department
            this.doctorInfoForm.techTitle = resp.data.doctor.job_title
            this.doctorInfoForm.specialty = resp.data.doctor.specialties
          } else {
            this.$message.error('请求错误，请重试')
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
