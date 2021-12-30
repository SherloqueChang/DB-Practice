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
              :content="'医生病历记录'"
            >
            </el-page-header>
          </el-col>
        </el-row>
        <el-table
          :data="medicalHistoryTable"
          height="250"
          border
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
            prop="issue"
            label="诊断结果">
          </el-table-column>
          <el-table-column
            prop="allergens"
            label="过敏史">
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
        <el-dialog title="编辑病历" :visible.sync="dialogFormVisible">
          <el-form :model="itemForm" :inline="true">
            <el-form-item label="既往史">
              <el-input v-model="itemForm.issue" autocomplete="off"></el-input>
            </el-form-item>
            <el-form-item label="评估诊断">
              <el-input v-model="itemForm.diagnosed_disease"></el-input>
            </el-form-item>
            <el-form-item label="过敏史">
              <el-input v-model="itemForm.allergens" autocomplete="off"></el-input>
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
  name: 'DoctorMedicalHistory',
  components: { NavAside, NavHeader },
  inject: ['reload'],
  data () {
    return {
      user: {},
      medicalHistoryTable: [],
      itemForm: {
        issue: '',
        diagnosed_disease: '',
        allergens: '',
        p_id: '',
        date: ''
      },
      dialogFormVisible: false
    }
  },
  created () {
    this.handleUserData()
    this.loadTable()
  },
  methods: {
    handleUserData () {
      if (this.$route.params.d_id !== undefined) {
        this.user.id = this.$route.params.d_id
      }
    },
    goBack () {
      this.$router.push('/departmentMng')
    },
    loadTable () {
      this.$axios
        .get('/getDoctorMedicalHistory', {
          params: { id: this.user.id }
        })
        .then((resp) => {
          if (resp.status === 200) {
            this.medicalHistoryTable=[]
            resp.data.leader.forEach((element) => {
              this.medicalHistoryTable.push({
                date: element.treat_date,
                p_id: element.patient_id,
                issue: element.treat_issue,
                diagnosed_disease: element.diagnosed_disease,
                allergens: element.allergens
              })
            })
          }
        })
    },
    editItem (index, row) {
      this.itemForm.issue = row.issue
      this.itemForm.diagnosed_disease = row.diagnosed_disease
      this.itemForm.allergens = row.allergens
      this.itemForm.p_id = row.p_id
      this.itemForm.date = row.date
      this.dialogFormVisible = true
    },
    deleteItem (index, row) {
      this.$axios
        .post('/deleteMedicalItem', null, {
          params: {
            d_id: this.user.id,
            p_id: row.p_id,
            date: row.date
          }
        })
        .then((resp) => {
          if (resp.status === 200) {
            this.$message({
              type: 'success',
              message: '操作成功'
            })
            this.loadTable()
            // TODO: 重新加载界面可能需要调试
            // this.reload()
          }
        })
    },
    submitItem () {
      this.$axios
        .post('/editMedicalItem', null, { 
          params: {
            d_id: this.user.id,
            p_id: this.itemForm.p_id,
            date: this.itemForm.date,
            issue: this.itemForm.issue,
            diagnosed_disease: this.itemForm.diagnosed_disease,
            allergens: this.itemForm.allergens
          }
        })
        .then((resp) => {
          if (resp.status === 200) {
            this.$message({
              type: 'success',
              message: '操作成功'
            })
            this.loadTable()
            this.dialogFormVisible = false
          }
        })
    }
  }
}
</script>
