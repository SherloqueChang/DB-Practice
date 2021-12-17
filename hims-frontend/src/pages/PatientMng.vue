<template>
  <el-container>
    <el-header><NavHeader></NavHeader></el-header>
    <el-container>
      <el-aside><NavAside></NavAside></el-aside>
      <el-main>
        <el-table
          :data="patientTable"
          height="250"
          border
          style="width: 100%">
          <el-table-column
            prop="id"
            label="患者id">
          </el-table-column>
          <el-table-column
            prop="name"
            label="患者姓名">
          </el-table-column>
          <el-table-column align="right">
            <template slot-scope="scope">
              <el-button
                size="mini"
                @click="checkPatient(scope.index, scope.row)"
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
  name: 'PatientMng',
  components: {NavAside, NavHeader},
  data () {
    return {
      patientTable: []
    }
  },
  created () {
    this.loadTableData()
  },
  methods: {
    loadTableData () {
      this.$axios
        .get('/getPatientTable', {})
        .then((resp) => {
          if (resp.status === 200) {
            resp.data.patient.forEach(element => {
              this.patientTable.push({
                id: element.id,
                name: element.name
              })
            })
          }
        })
    },
    checkPatient (index, row) {
      this.$router.push({
        name: 'UserInfo',
        id: row.id,
        type: 'patient'
      })
    }
  }
}
</script>
