<template>
  <el-container>
    <el-header><NavHeader></NavHeader></el-header>
    <el-container>
      <el-aside><NavAside></NavAside></el-aside>
      <el-main>
        <el-table
          :data="tableData"
          height="250"
          border
          empty-text="暂无预约记录"
          style="width: 100%">
          <el-table-column
            prop="date"
            label="日期">
          </el-table-column>
          <el-table-column
            prop="department"
            label="科室">
          </el-table-column>
          <el-table-column
            prop="doctor"
            label="医生">
          </el-table-column>
          <el-table-column
            prop="status"
            label="状态">
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
  name: 'AppointmentInfo',
  components: { NavHeader, NavAside },
  data () {
    return {
      user: {},
      tableData: [],
      laoding: false
    }
  },
  created () {
    this.handleUserData()
    this.loadTableData()
  },
  methods: {
    handleUserData () {
      if (this.$store.state.user) {
        this.user = this.$store.state.user
      }
    },
    loadTableData () {
      this.$axios
        .get('/patientAppointmentInfo', {
          params: { id: this.user.id }
        })
        .then((resp) => {
          if (resp.status === 200) {
            resp.data.patientAppointment.forEach((element) => {
              this.tableData.push({
                date: element.date,
                department: element.department,
                doctor: element.doctor,
                status: element.status
              })
            })
          }
        })
        .catch((error) => {
          this.$message.error('请求错误，请重试')
        })
    }
  }
}
</script>
