<template>
  <el-container>
    <el-header><NavHeader></NavHeader></el-header>
    <el-container>
      <el-aside><NavAside></NavAside></el-aside>
      <el-main>
        <el-row type="flex" justify="center">
          <el-col align="left">
            <el-page-header
              @back="goBack"
              :content="'医生处方记录'"
            >
            </el-page-header>
          </el-col>
        </el-row>
        <el-table
          :data="prescriptionTable"
          height="250"
          border
          v-loading="loading"
          style="width: 100%">
          <el-table-column
            prop="date"
            label="日期">
          </el-table-column>
          <el-table-column
            prop="p_id"
            label="病人编号">
          </el-table-column>
          <el-table-column
            prop="medicine_name"
            label="药品名称">
          </el-table-column>
          <el-table-column
            prop="medicine_num"
            label="药品数量">
          </el-table-column>
          <el-table-column align="right">
            <template slot-scope="scope">
              <el-button
                size="mini"
                @click="editItem(scope.index, scope.row)"
              >编辑</el-button>
              <el-button
                size="mini"
                @click="deleteItem(scope.index, scope.row)"
              >删除</el-button>
            </template>
          </el-table-column>
        </el-table>
        <el-dialog title="编辑处方" :visible.sync="dialogFormVisible">
          <el-form :model="itemForm" :inline="true">
            <el-form-item label="药品名称" :label-width="formLabelWidth">
              <el-input v-model="itemForm.medicine_name" autocomplete="off"></el-input>
            </el-form-item>
            <el-form-item label="药品数量" :label-width="formLabelWidth">
              <el-input v-model="itemForm.medicine_num" autocomplete="off"></el-input>
            </el-form-item>
          </el-form>
          <div slot="footer" class="dialog-footer">
            <el-button @click="dialogFormVisible = false">取 消</el-button>
            <el-button type="primary" @click="submitItem">确 定</el-button>
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
  name: 'DoctorPrescription',
  components: { NavAside, NavHeader },
  inject: ['reload'],
  data () {
    return {
      user: {},
      prescriptionTable: [],
      // TODO: 药品信息如何表示还需要处理
      itemForm: {
        medicine_name: '',
        medicine_num: ''
      },
      dialogFormVisible: false,
      loading: false
    }
  },
  created () {
    this.handleUserData()
    this.loadTable()
  },
  methods: {
    handleUserData () {
      if (this.$store.state.user) {
        this.user = this.$store.state.user
      }
    },
    goBack () {
      this.$router.push('/departmentMng')
    },
    loadTable () {
      this.loading = true
      this.$axios
        .get('/getDoctorPrescription', {
          params: { id: this.user.id }
        })
        .then((resp) => {
          if (resp.status === 200) {
            resp.data.leader.forEach((element) => {
              this.prescriptionTable.push({
                date: element.date,
                p_id: element.p_id,
                medicine_name: element.medicine_name,
                medicine_num: element.medicine_num
              })
            })
          }
        })
    },
    editItem (index, row) {
      this.itemForm.medicine_name = row.medicine_name
      this.itemForm.medicine_num = row.medicine_num
      this.dialogFormVisible = true
    },
    deleteItem (index, row) {
      this.loading = true
      this.$axios
        .post('/deletePrescriptionItem', {
          id: this.user.id,
          p_id: row.p_id
        })
        .then((resp) => {
          if (resp.status === 200) {
            this.$message({
              type: 'success',
              message: '操作成功'
            })
            // this.loadTable()
            // TODO: 重新加载界面可能需要调试
            this.reload()
          }
        })
    },
    submitItem () {
      this.$axios
        .post('/editPrescriptionItem', null, {
          id: this.user.id,
          p_id: this.prescriptionTable.p_id,
          medicine_name: this.itemForm.medicine_name,
          medicine_num: this.itemForm.medicine_num
        })
        .then((resp) => {
          if (resp.status === 200) {
            this.$message({
              type: 'success',
              message: '操作成功'
            })
            this.dialogFormVisible = false
          }
        })
    }
  }
}
</script>
