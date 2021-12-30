<template>
  <el-container>
    <el-header><NavHeader></NavHeader></el-header>
    <el-container>
      <el-aside><NavAside></NavAside></el-aside>
      <el-main>
        <el-table
          :data="departmentTable"
          height="250"
          border
          style="width: 100%">
          <el-table-column
            prop="name"
            label="科室名称">
          </el-table-column>
          <el-table-column
            prop="id"
            label="科长">
          </el-table-column>
          <el-table-column
            prop="doctor_num"
            label="医生数量">
          </el-table-column>
          <el-table-column align="right">
            <template slot-scope="scope">
              <el-button
                size="mini"
                @click="checkDepartment(scope.index, scope.row)"
              >详情</el-button>
              <el-button
                size="mini"
                @click="deleteDepartment(scope.index, scope.row)"
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
  name: 'DepartmentMng_admin',
  components: {NavAside, NavHeader},
  inject: ['reload'],
  data () {
    return {
      d_id: '',
      departmentTable: []
    }
  },
  created () {
    this.loadTableData()
  },
  methods: {
    loadTableData () {
      this.$axios
        .get('/getDepartmentTable', {})
        .then((resp) => {
          if (resp.status === 200) {
            resp.data.department.forEach(element => {
              this.departmentTable.push({
                name: element.name,
                id: element.id,
                doctor_num: element.doctor_num
              })
            })
          }
        })
    },
    checkDepartment (index, row) {
      this.$router.push({
        name: 'DepartmentMng',
        params: {
          id: row.id,
          dept: row.name,
          u_type: 'admin'
        }
      })
    },
    deleteDepartment (index, row) {
      this.$axios
        .post('/deleteDepartment', null, {
          params: { name: row.name }
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
