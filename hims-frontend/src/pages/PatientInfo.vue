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
              :content="'患者详情：' + this.patientInfoForm.name"
            >
            </el-page-header>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="8">
            <el-form>
              <el-form-item label="姓名">
                {{ this.patientInfoForm.name }}
              </el-form-item>
              <el-form-item label="出生日期">
                {{ this.patientInfoForm.birthdate }}
              </el-form-item>
              <el-form-item label="身份证号">
                {{ this.patientInfoForm.idcard }}
              </el-form-item>
              <el-form-item label="性别">
                {{ this.patientInfoForm.gender }}
              </el-form-item>
              <el-form-item label="电话">
                {{ this.patientInfoForm.phone }}
              </el-form-item>
              <el-form-item label="Email">
                {{ this.patientInfoForm.email }}
              </el-form-item>
              <el-form-item label="身份">
                {{ this.patientInfoForm.u_type }}
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
  name: 'PatientInfo',
  components: { NavAside, NavHeader },
  data () {
    return {
      // 患者应该还有病历表、处方表信息可以被查看，这里没有涉及
      // 后期可以设置三个tab，分别对应个人信息，病历表格和处方表格
      user: {},
      patientInfoForm: {
        name: '',
        birthdate: '',
        idcard: '',
        gender: '',
        phone: '',
        email: '',
        u_type: ''
      }
    }
  },
  created () {
    this.loadPatientData()
  },
  methods: {
    goBack () {
      // this.$router.push('/appointmentOnline')
      this.$router.back(-1)
    },
    loadPatientData () {
      this.$axios
        .get('/getPatientInfo', {
          // 通过路由传参，获得患者的id
          params: { id: this.$route.params.p_id }
        })
        .then((resp) => {
          if (resp.status === 200) {
            this.patientInfoForm.name = resp.data.name
            this.patientInfoForm.birthdate = resp.data.birthdate
            this.patientInfoForm.idcard = resp.data.idcard
            this.patientInfoForm.gender = resp.data.gender
            this.patientInfoForm.phone = resp.data.phone
            this.patientInfoForm.email = resp.data.email
            this.patientInfoForm.u_type = resp.data.u_type
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
