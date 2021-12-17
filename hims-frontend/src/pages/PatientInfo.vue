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
        <el-tabs type="border-card" v-model="activeName" @tab-click="handleClick">
          <el-tab-pane label="患者详情" name="first">
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
          </el-tab-pane>
          <el-tab-pane label="病历记录" name="second">
            <el-table
              :data="medicalHistoryTable"
              height="250"
              border
              empty-text="暂无病历记录"
              style="width: 100%">
              <el-table-column
                prop="doctor"
                label="医生">
              </el-table-column>
              <el-table-column
                prop="date"
                label="日期">
              </el-table-column>
              <el-table-column
                prop="issue"
                label="诊断结果">
              </el-table-column>
              <el-table-column
                prop="diagnosed_disease"
                label="诊断病症">
              </el-table-column>
              <el-table-column
                prop="allergens"
                label="过敏史">
              </el-table-column>
            </el-table>
          </el-tab-pane>
          <el-tab-pane label="处方记录" name="third">
            <el-table
              :data="prescriptionTable"
              height="250"
              border
              empty-text="暂无处方记录"
              style="width: 100%">
              <el-table-column
                prop="doctor"
                label="医生">
              </el-table-column>
              <el-table-column
                prop="date"
                label="日期">
              </el-table-column>
              <el-table-column
                prop="medicine_name"
                label="药品名称">
              </el-table-column>
              <el-table-column
                prop="medicine_num"
                label="药品数量">
              </el-table-column>
            </el-table>
          </el-tab-pane>
        </el-tabs>
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
      user: {},
      patientInfoForm: {
        name: '',
        birthdate: '',
        idcard: '',
        gender: '',
        phone: '',
        email: '',
        u_type: ''
      },
      activeName: 'first',
      medicalHistoryTable: [],
      prescriptionTable: []
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
    handleClick (tab, event) {
      if (tab.index === '0') {
        this.loadPatientData()
      } else if (tab.index === '1') {
        this.loadTableData(1)
      } else if (tab.index === '2') {
        this.loadTableData(2)
      }
    },
    loadPatientData () {
      this.$axios
        .get('/getPatientInfo', {
          // 通过路由传参，获得患者的id
          params: { id: this.$route.params.p_id }
        })
        .then((resp) => {
          if (resp.status === 200) {
            this.patientInfoForm.name = resp.data.patient.name
            this.patientInfoForm.birthdate = resp.data.patient.birthdate
            this.patientInfoForm.idcard = resp.data.patient.idcard
            this.patientInfoForm.gender = resp.data.patient.gender
            this.patientInfoForm.phone = resp.data.patient.phone
            this.patientInfoForm.email = resp.data.patient.email
            this.patientInfoForm.u_type = resp.data.patient.u_type
          } else {
            this.$message.error('请求错误，请重试')
          }
        })
        .catch((error) => {
          console.log(error)
          this.$message.error('请求错误，请重试')
        })
    },
    loadTableData (index) {
      if (index === 1) {
        // 病历列表 - historyTable
        this.$axios
          .get('/patientHistoryTable', {
            params: { id: this.$route.params.p_id }
          })
          .then((resp) => {
            resp.data.patientHistoryTable.forEach((element) => {
              this.$route.params.p_id.push({
                doctor: element.doctor,
                date: element.date,
                issue: element.issue,
                diagnosed_disease: element.diagnosed_disease,
                allergens: element.allergens
              })
            })
          })
          .catch((error) => {
            this.$message.error('请求错误，请重试')
          })
      } else if (index === 2) {
        // 处方列表
        this.$axios
          .get('/patientPrescriptionTable', {
            params: { id: this.$route.params.p_id }
          })
          .then((resp) => {
            resp.data.patient.patientPrescriptionTable.forEach((element) => {
              this.prescriptionTable.push({
                doctor: element.doctor,
                date: element.date,
                medicine_name: element.medicine_name,
                medicine_num: element.medicine_num
              })
            })
          })
          .catch((error) => {
            this.$message.error('请求错误，请重试')
          })
      }
    }
  }
}
</script>
