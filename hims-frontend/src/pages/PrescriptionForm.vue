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
              <el-form-item label="药品种类数量">
                <el-input  v-model="total_m_num"></el-input>
              </el-form-item>
              <el-form-item label="药品名称" prop="medicine_name1" v-if="parseInt(total_m_num)>0">
                <el-input v-model="prescriptionForm.medicine_name1"></el-input>
              </el-form-item>
              <el-form-item label="药品数量" prop="medicine_num1" v-if="parseInt(total_m_num)>0">
                <el-input v-model="prescriptionForm.medicine_num1"></el-input>
              </el-form-item>
              <el-form-item label="药品名称" prop="medicine_name2" v-if="parseInt(total_m_num)>1">
                <el-input v-model="prescriptionForm.medicine_name2"></el-input>
              </el-form-item>
              <el-form-item label="药品数量" prop="medicine_num2" v-if="parseInt(total_m_num)>1">
                <el-input v-model="prescriptionForm.medicine_num2"></el-input>
              </el-form-item>
              <el-form-item label="药品名称" prop="medicine_name3" v-if="parseInt(total_m_num)>2">
                <el-input v-model="prescriptionForm.medicine_name3"></el-input>
              </el-form-item>
              <el-form-item label="药品数量" prop="medicine_num3" v-if="parseInt(total_m_num)>2">
                <el-input v-model="prescriptionForm.medicine_num3"></el-input>
              </el-form-item>
              <el-form-item label="药品名称" prop="medicine_name4" v-if="parseInt(total_m_num)>3">
                <el-input v-model="prescriptionForm.medicine_name4"></el-input>
              </el-form-item>
              <el-form-item label="药品数量" prop="medicine_num4" v-if="parseInt(total_m_num)>3">
                <el-input v-model="prescriptionForm.medicine_num4"></el-input>
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
        doctor: this.$route.params.d_id,
        patient: this.$route.params.p_id,
        date: '',
        // TODO: 处方作为多值属性如何在表格中表示(暂时以分隔符区分)
        medicine_name1: '',
        medicine_num1: '',
        medicine_name2: '',
        medicine_num2: '',
        medicine_name3: '',
        medicine_num3: '',
        medicine_name4: '',
        medicine_num4: ''
      },
      total_m_num: '0'
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
      this.$axios
        .post('/editMedicalRecForm', null, {
          params: {
            d_id: this.user.id,
            p_id: this.$route.params.p_id,
            doctor: this.prescriptionForm.doctor,
            patient: this.prescriptionForm.patient,
            total_m_num: this.total_m_num,
            medicine_name1: this.prescriptionForm.medicine_name1,
            medicine_num1: this.prescriptionForm.medicine_num1,
            medicine_name2: this.prescriptionForm.medicine_name2,
            medicine_num2: this.prescriptionForm.medicine_num2,
            medicine_name3: this.prescriptionForm.medicine_name3,
            medicine_num3: this.prescriptionForm.medicine_num3,
            medicine_name4: this.prescriptionForm.medicine_name4,
            medicine_num4: this.prescriptionForm.medicine_num4
          }
        })
        .then((resp) => {
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
