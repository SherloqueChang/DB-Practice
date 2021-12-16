<template>
  <el-container>
    <el-header><NavHeader></NavHeader></el-header>
    <el-container>
      <el-aside><NavAside></NavAside></el-aside>
      <el-main>
        <el-tabs type="border-card" v-model="activeName" @tab-click="handleClick">
          <el-tab-pane label="病历" name="first">
            <el-table
              :data="historyTable"
              height="250"
              border
              empty-text="暂无病历记录"
              v-if="(activeName==='first')"
              style="width: 100%">
              <el-table-column
                prop="date"
                label="日期">
              </el-table-column>
              <el-table-column
                prop="doctor"
                label="医生姓名">
              </el-table-column>
              <el-table-column
                prop="issue"
                label="评估诊断">
              </el-table-column>
              <el-table-column
                prop="diagnosed_disease"
                label="既往史">
              </el-table-column>
              <el-table-column
                prop="allergens"
                label="过敏史">
              </el-table-column>
            </el-table>
          </el-tab-pane>
          <el-tab-pane label="处方" name="second">
            <el-table
              :data="prescriptionTable"
              height="250"
              border
              empty-text="暂无处方记录"
              v-if="(activeName==='second')"
              style="width: 100%">
              <el-table-column
                prop="date"
                label="日期">
              </el-table-column>
              <el-table-column
                prop="doctor"
                label="医生姓名">
              </el-table-column>
              <el-table-column
                prop="medicine_name"
                label="药品编号">
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
  name: 'MedicalInfo',
  components: { NavAside, NavHeader },
  data () {
    return {
      user: {},
      activeName: 'first',
      historyTable: [],
      prescriptionTable: []
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
    handleClick (tab, event) {
      if (tab.index === '0') {
        this.loadTableData(0)
      } else if (tab.index === '1') {
        this.loadTableData(1)
      }
    },
    loadTableData (index) {
      if (index === 0) {
        // 病历列表 - historyTable
        this.$axios
          .get('/patientHistoryTable', {
            params: { id: this.user.id }
          })
          .then((resp) => {
            resp.data.patientHistoryTable.forEach((element) => {
              this.historyTable.push({
                date: element.date,
                doctor: element.doctor,
                issue: element.issue,
                diagnosed_disease: element.diagnosed_disease,
                allergens: element.allergens
              })
            })
          })
          .catch((error) => {
            this.$message.error('请求错误，请重试')
          })
      } else if (index === 1) {
        // 处方列表
        this.$axios
          .get('/patientPrescriptionTable', {
            params: { id: this.user.id }
          })
          .then((resp) => {
            resp.data.patientPrescriptionTable.forEach((element) => {
              this.historyTable.push({
                date: element.date,
                doctor: element.doctor,
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
