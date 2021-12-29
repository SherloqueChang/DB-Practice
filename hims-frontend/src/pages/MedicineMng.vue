<template>
  <el-container>
    <el-header><NavHeader></NavHeader></el-header>
    <el-container>
      <el-aside><NavAside></NavAside></el-aside>
      <el-main>
        <el-table
          :data="medicineTable"
          height="250"
          border
          style="width: 100%">
          <el-table-column
            prop="name"
            label="药品名称">
          </el-table-column>
          <el-table-column
            prop="num"
            label="药品数量">
          </el-table-column>
          <el-table-column align="right">
            <template slot-scope="scope">
              <el-button
                size="mini"
                @click="editMedicine(scope.index, scope.row)"
              >编辑</el-button>
            </template>
          </el-table-column>
        </el-table>
        <el-dialog title="编辑药品" :visible.sync="dialogFormVisible">
          <el-form :model="itemForm" :inline="true">
            <el-form-item label="药品名称:">
              <div>{{this.itemForm.name}}</div>
            </el-form-item>
            <el-form-item label="药品数量:">
              <el-input v-model="itemForm.num" autocomplete="off"></el-input>
            </el-form-item>
          </el-form>
          <div slot="footer" class="dialog-footer">
            <el-button @click="dialogFormVisible = false">取 消</el-button>
            <el-button type="primary" @click="submitChange">确 定</el-button>
          </div>
        </el-dialog>
      </el-main>
    </el-container>
  </el-container>
</template>

<script>

import NavHeader from '../components/NavHeader'
import NavAside from '../components/NavAside'

export default {
  name: 'MedicineMng',
  components: {NavAside, NavHeader},
  inject: ['reload'],
  data () {
    return {
      user: {},
      itemForm: {
        name: '',
        num: ''
      },
      medicineTable: [],
      dialogFormVisible: false
    }
  },
  created () {
    this.loadTableData()
  },
  methods: {
    loadTableData () {
      this.$axios
        .get('/getMedicineTable', {})
        .then((resp) => {
          if (resp.status === 200) {
            resp.data.medicine.forEach(element => {
              this.medicineTable.push({
                name: element.name,
                num: element.num
              })
            })
          }
        })
    },
    editMedicine (index, row) {
      this.itemForm.name = row.name
      this.itemForm.num = row.num
      this.dialogFormVisible = true
    },
    submitChange () {
      this.$axios
        .post('/editMedicine', null, {
          params: {
            name: this.itemForm.name,
            num: this.itemForm.num
          }
        })
        .then((resp) => {
          if (resp.status === 200) {
            this.$message({
              type: 'success',
              message: '编辑成功'
            })
            this.dialogFormVisible = false
            this.reload()
          }
        })
    }
  }
}
</script>
