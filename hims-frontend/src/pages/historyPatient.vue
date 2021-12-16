<template>
  <el-container>
    <el-header><NavHeader></NavHeader></el-header>
    <el-container>
      <el-aside><NavAside></NavAside></el-aside>
      <el-main>
        <el-table
          :data="historyPatientForm"
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
          <el-table-column
            prop="date"
            label="诊断日期">
          </el-table-column>
          <el-table-column align="right">
            <template slot-scope="scope">
              <el-button
                size="mini"
                @click="getPatientInfo(scope.index, scope.row)"
              >详情</el-button>
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
  name: 'HistoryPatient',
  components: {NavAside, NavHeader},
  data () {
    return {
      user: {},
      historyPatientForm: []
    }
  },
  created () {
    this.handleUserData()
    this.loadHistoryPatient()
  },
  methods: {
    handleUserData () {
      if (this.$store.state.user) {
        this.user = this.$store.state.user
      }
    },
    loadHistoryPatient () {
      this.$axios
        .get('/getHistoryPatient', {
          params: { id: this.user.id }
        })
        .then((resp) => {
          resp.data.historyPatient.forEach((element) => {
            this.historyPatientForm.push({
              id: element.id,
              name: element.name,
              date: element.date
            })
          })
        })
        .catch((error) => {
          console.log(error)
          this.$message.error('请求错误，请重试')
        })
    },
    getPatientInfo (index, row) {
      this.$router.push('/patientInfo' + row.id)
    }
  }
}
</script>
