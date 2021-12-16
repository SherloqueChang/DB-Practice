<template>
  <el-container>
    <el-header><NavHeader></NavHeader></el-header>
    <el-container>
      <el-aside><NavAside></NavAside></el-aside>
      <el-main>
        <el-table
          :data="appointmentTodayTable"
          height="250"
          border
          style="width: 100%">
          <el-table-column
            prop="id"
            label="患者编号">
          </el-table-column>
          <el-table-column
            prop="name"
            label="患者姓名">
          </el-table-column>
          <el-table-column align="right">
            <template slot-scope="scope">
              <el-button
                size="mini"
                @click="getPatientInfo(scope.$index, scope.row)"
              >详情</el-button>
              <el-button
                size="mini"
                @click="editMedicalForm(scope.$index, scope.row)"
              >编辑病历</el-button>
              <el-button
                size="mini"
                @click="editPrescriptionForm(scope.$index, scope.row)"
              >开具处方</el-button>
            </template>
          </el-table-column>
        </el-table>
      </el-main>
    </el-container>
  </el-container>
</template>

<script>

import NavHeader from '../components/NavHeader'
import NavAside from '../components/NavAside'

export default {
  name: 'AppointmentToday',
  components: { NavAside, NavHeader },
  data () {
    return {
      user: {},
      appointmentTodayTable: []
    }
  },
  created () {
    this.handleUserData()
    this.loadPatientData()
  },
  methods: {
    handleUserData () {
      if (this.$store.state.user) {
        this.user = this.$store.state.user
      }
    },
    loadPatientData () {
      this.$axios
        .get('/getPatientTodayInfo', {
          // 通过路由传参，获得患者的id
          params: { p_id: this.$route.params.p_id, d_id: this.user.id }
        })
        .then((resp) => {
          if (resp.status === 200) {
            resp.data.patientToday.forEach((element) => {
              this.appointmentTodayTable.push({
                id: element.id,
                name: element.name
              })
            })
          }
        })
        .catch((error) => {
          this.$message.error('请求错误，请重试')
        })
    },
    getPatientInfo (index, row) {
      this.$router.push('/patientInfo' + row.id)
    },
    editMedicalForm (index, row) {
      // 编辑病历表
      this.$router.push('/medicalRecForm' + row.id)
    },
    editPrescriptionForm (index, row) {
      // 编辑处方
      this.$router.push('/prescriptionForm' + row.id)
    }
  }
}
</script>
