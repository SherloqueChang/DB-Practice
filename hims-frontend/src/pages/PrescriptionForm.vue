<template>
  <el-container>
    <el-header><NavHeader></NavHeader></el-header>
    <el-container>
      <el-aside><NavAside></NavAside></el-aside>
      <el-main>
        <el-row>
          <el-col :span="8">
            <el-form
              @submit.native.prevent
              :model="prescriptionForm"
              status-icon
              ref="prescriptionForm"
              v-loading="loading"
            >
              <el-form-item label="医生" prop="doctor">
                <el-input v-model="prescriptionForm.doctor"></el-input>
              </el-form-item>
              <el-form-item label="患者" prop="patient">
                <el-input v-model="prescriptionForm.patient"></el-input>
              </el-form-item>
              <el-form-item label="日期" prop="date">
                <div class="block">
                  <el-date-picker
                    value-format="yyyy-MM-dd"
                    v-model="prescriptionForm.date"
                    type="date"
                    placeholder="选择日期">
                  </el-date-picker>
                </div>
              </el-form-item>
              <el-form-item label="药品名称" prop="medicine_name">
                <el-input v-model="prescriptionForm.issue"></el-input>
              </el-form-item>
              <el-form-item label="药品数量" prop="medicine_num">
                <el-input v-model="prescriptionForm.diagnosed_disease"></el-input>
              </el-form-item>
              <el-form-item>
                <el-button
                  native-type="submit"
                  type="primary"
                  @click="submitForm('prescriptionForm')"
                >编辑完成</el-button
                >
              </el-form-item>
            </el-form>
          </el-col>
        </el-row>
      </el-main>
    </el-container>
  </el-container>
</template>

<script>
import NavHeader from '../components/NavHeader'
import NavAside from '../components/NavAside'

export default {
  name: 'PrescriptionForm',
  components: { NavHeader, NavAside },
  data () {
    return {
      user: {},
      prescriptionForm: {
        doctor: '',
        patient: '',
        date: '',
        // TODO: 处方作为多值属性如何在表格中表示(暂时以分隔符区分)
        medicine_name: '',
        medicine_num: ''
      }
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
    submitForm (formName) {
      this.loading = true
      this.$axios
        .post('/editMedicalRecForm', null, {
          params: {
            d_id: this.user.id,
            p_id: this.$route.params.p_id,
            doctor: this.prescriptionForm.doctor,
            patient: this.prescriptionForm.patient,
            medicine_name: this.prescriptionForm.medicine_name,
            medicine_num: this.prescriptionForm.medicine_num
          }
        })
        .then((resp) => {
          this.loading = false
          if (resp.status === 200) {
            this.$message({
              type: 'success',
              message: '处方编辑完成'
            })
            this.$router.back(-1)
          }
        })
        .catch((error) => {
          this.$message.error('请求错误，请重试')
        })
    }
  }
}
</script>
