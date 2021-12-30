<template>
  <el-container>
    <el-header><NavHeader></NavHeader></el-header>
    <el-container>
      <el-aside><NavAside></NavAside></el-aside>
      <el-main>
        <el-tabs type="border-card" v-model="activeName">
          <el-tab-pane label="发热门诊" name="first">
            <el-form :inline="true" class="demo-form-inline">
              <el-form-item>
                <div class="block">
                  <el-date-picker
                    value-format="yyyy-MM-dd"
                    v-model="date"
                    type="date"
                    placeholder="选择日期">
                  </el-date-picker>
                </div>
              </el-form-item>
              <el-form-item>
                <el-button type="primary" @click="submitQuery">查询</el-button>
              </el-form-item>
            </el-form>
            <el-table
              :data="appointmentQueryTable"
              height="250"
              border
              style="width: 100%">
              <el-table-column
                prop="id"
                label="医生编号">
              </el-table-column>
              <el-table-column
                prop="doctor"
                label="医生姓名">
              </el-table-column>
              <el-table-column
                prop="availableAppointment"
                label="剩余预约名额">
              </el-table-column>
              <el-table-column align="right">
                <template slot-scope="scope">
                  <el-button
                    size="mini"
                    @click="getDoctorInfo(scope.index, scope.row)"
                  >详情</el-button>
                  <el-button
                    size="mini"
                    @click="finalSubmit(scope.index, scope.row)"
                  >预约</el-button>
                </template>
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
  name: 'FeverAppointment',
  components: { NavAside, NavHeader },
  data () {
    return {
      user: {},
      date: '',
      department: '', // 就是发热科室
      doctor: '',
      // 可能需要列出key
      appointmentQueryTable: [],
      activeName: 'first'
    }
  },
  created () {
    this.handleUserData()
  },
  methods: {
    handleUserData () {
      if (this.$store.state.user) {
        this.user = this.$store.state.user
      }
    },
    handleClick (tab) {
      if (tab.index === '0') {
        this.submitQuery()
      }
    },
    submitQuery () {
      // 获取某一日期，某一科室中的有空余预约名额的医生信息
      this.$axios
        .get('/appointmentQuery', {
          params: { date: this.date, department: "5" }
        })
        .then((resp) => {
          this.appointmentQueryTable = []
          resp.data.appointmentQuery.forEach((element) => {
            this.appointmentQueryTable.push({
              id: element.id,
              doctor: element.doctor,
              availableAppointment: element.availableAppointment
            })
          })
        })
        .catch((error) => {
          console.log(error)
          this.$message.error('请求错误，请重试')
        })
    },
    getDoctorInfo (index, row) {
      // 查看发热门诊中某一医生的详细信息
      this.$router.push({
        name: 'DoctorInfo',
        params: { d_id: row.id }
      })
    },
    finalSubmit (index, row) {
      // 预约发热门诊医生
      this.$axios
        .post('/finalSubmit', null, {
          params: {
            patient_id: this.user.id,
            date: this.date,
            department: "d5",
            doctor_name: row.doctor
          }
        })
        .then((resp) => {
          if (resp.status === 200) {
            this.dialogFormVisible = false
            this.$message({
              type: 'success',
              message: '预约成功！'
            })
            this.activeName = 'first'
          //  是否需要reload
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
