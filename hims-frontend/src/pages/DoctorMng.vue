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
      this.$router.push({
        name: 'UserInfo',
        id: row.id,
        type: 'doctor'
      })
    }
  }
}
</script>
