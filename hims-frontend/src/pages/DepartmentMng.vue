<template>
  <el-container>
    <el-header><NavHeader></NavHeader></el-header>
    <el-container>
      <el-aside><NavAside></NavAside></el-aside>
      <el-main>
        <el-tabs type="border-card" v-model="activeName" @tab-click="handleClick">
          <el-tab-pane label="科室描述" name="first">
            <el-form>
              <el-form-item>
                <el-input type="textarea" v-model="departmentDesc"></el-input>
              </el-form-item>
              <el-form-item>
                <el-button type="primary" @click="submitChange">保存修改</el-button>
              </el-form-item>
            </el-form>
          </el-tab-pane>
          <el-tab-pane label="医生信息" name="second">
            <el-table
              :data="departmentDoctorTable"
              height="250"
              border
              style="width: 100%">
              <el-table-column
                prop="id"
                label="医生编号">
              </el-table-column>
              <el-table-column
                prop="name"
                label="医生姓名">
              </el-table-column>
              <el-table-column align="right">
                <template slot-scope="scope">
                  <el-button
                    size="mini"
                    @click="getDoctorInfo(scope.index, scope.row)"
                  >详情</el-button>
                  <el-button
                    size="mini"
                    @click="checkDoctorMedicalHistory(scope.index, scope.row)"
                  >病历</el-button>
                  <el-button
                    size="mini"
                    @click="checkDoctorPrescription(scope.index, scope.row)"
                  >处方</el-button>
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
  name: 'DepartmentMng',
  components: {NavAside, NavHeader},
  data () {
    return {
      user: {},
      departmentDesc: '',
      departmentDoctorTable: [],
      activeName: 'first'
    }
  },
  created () {
    this.handleUserData()
    this.loadDepartmentDesc()
  },
  methods: {
    handleUserData () {
      if (this.$store.state.user) {
        this.user = this.$store.state.user
      }
      if (this.user.u_type === 'admin') {
        // 不同路由跳转(管理员登录)
        this.user.id = this.$route.params.d_id
      }
    },
    loadDepartmentDesc () {
      this.$axios
        .get('/getDepartmentDesc', {
          params: { id: this.user.id }
        })
        .then((resp) => {
          if (resp.status === 200) {
            this.departmentDesc = resp.data.leader.departmentDesc
          }
        })
    },
    submitChange () {
      this.$axios
        .post('/modifyDepartmentDesc', null, {
          param: {
            id: this.user.id,
            desc: this.departmentDesc
          }
        })
        .then((resp) => {
          if (resp.status === 200) {
            this.$message({
              type: 'success',
              message: '成功修改科室描述'
            })
          }
        })
    },
    handleClick (tab) {
      if (tab.index === '0') {
        this.loadDepartmentDesc()
      } else if (tab.index === '1') {
        this.loadDoctorTable()
      }
    },
    loadDoctorTable () {
      this.$axios
        .get('/getDepartmentDoctor', {
          params: { id: this.user.id }
        })
        .then((resp) => {
          if (resp.status === 200) {
            resp.data.leader.forEach((element) => {
              this.departmentDoctorTable.push({
                id: element.id,
                name: element.name
              })
            })
          }
        })
    },
    getDoctorInfo (index, row) {
      // 查看表中某一医生的详细信息
      this.$router.push('/doctorInfo' + row.id)
    },
    checkDoctorMedicalHistory (index, row) {
      this.$router.push('/checkDoctorMedicalHistory')
    },
    checkDoctorPrescription (index, row) {
      this.$router.push('/checkDoctorPrescription')
    }
  }
}
</script>
