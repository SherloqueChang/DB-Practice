<template>
  <el-container>
    <el-header><NavHeader></NavHeader></el-header>
    <el-container>
      <el-aside><NavAside></NavAside></el-aside>
      <el-main>
        <el-table
          :data="doctorTable"
          height="250"
          border
          style="width: 100%">
          <el-table-column
            prop="id"
            label="医生id">
          </el-table-column>
          <el-table-column
            prop="name"
            label="医生姓名">
          </el-table-column>
          <el-table-column align="right">
            <template slot-scope="scope">
              <el-button
                size="mini"
                @click="checkDoctor(scope.index, scope.row)"
              >详情</el-button>
              <el-button
                size="mini"
                @click="deleteDoctor(scope.index, scope.row)"
              >删除</el-button>
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
  name: 'DoctorMng',
  components: {NavAside, NavHeader},
  inject: ['reload'],
  data () {
    return {
      doctorTable: []
    }
  },
  created () {
    this.loadTableData()
  },
  methods: {
    loadTableData () {
      this.$axios
        .get('/getDoctorTable', {})
        .then((resp) => {
          if (resp.status === 200) {
            resp.data.doctor.forEach(element => {
              this.doctorTable.push({
                id: element.id,
                name: element.name
              })
            })
          }
        })
    },
    checkDoctor (index, row) {
      this.$axios
        .get('/getDoctorInfo', {
          params: { id: row.id }
        })
        .then((resp) => {
          if (resp.status === 200) {
            this.$router.push({
              name: 'UserInfo',
              params: {
                id: row.id,
                name: resp.data.doctor.name,
                birthdate: resp.data.doctor.birthdate,
                idcard: resp.data.doctor.idcard,
                gender: resp.data.doctor.gender,
                phone: resp.data.doctor.phone,
                email: resp.data.doctor.email,
                u_type: resp.data.doctor.u_type,
                graduate_school: resp.data.doctor.graduate_school,
                department: resp.data.doctor.department,
                job_title: resp.data.doctor.job_title,
                specialties: resp.data.doctor.specialties
              }
            })
          }
        })
    },
    deleteDoctor (index, row) {
      this.$axios
        .post('/deleteDoctor', null, {
          params: { id: row.id }
        })
        .then((resp) => {
          if (resp.status === 200) {
            this.$message({
              type: 'success',
              message: '删除成功'
            })
            this.reload()
          }
        })
    }
  }
}
</script>
