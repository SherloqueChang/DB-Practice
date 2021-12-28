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
              :model="medicalRecForm"
              status-icon
              ref="medicalRecForm"
              v-loading="loading"
            >
              <el-form-item label="医生" prop="doctor">
                <el-input v-model="medicalRecForm.doctor"></el-input>
              </el-form-item>
              <el-form-item label="患者" prop="patient">
                <el-input v-model="medicalRecForm.patient"></el-input>
              </el-form-item>
              <el-form-item label="日期" prop="date">
                <div class="block">
                  <el-date-picker
                    value-format="yyyy-MM-dd"
                    v-model="medicalRecForm.date"
                    type="date"
                    placeholder="选择日期">
                  </el-date-picker>
                </div>
              </el-form-item>
              <el-form-item label="诊断结果" prop="issue">
                <el-input v-model="medicalRecForm.issue"></el-input>
              </el-form-item>
              <el-form-item label="诊断病症" prop="diagnosed_disease">
                <el-input v-model="medicalRecForm.diagnosed_disease"></el-input>
              </el-form-item>
              <el-form-item label="过敏史" prop="allergens">
                <el-input v-model="medicalRecForm.allergens"></el-input>
              </el-form-item>
              <el-form-item>
                <el-button
                  native-type="submit"
                  type="primary"
                  @click="submitForm('medicalRecForm')"
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
  name: 'MedicalRecForm',
  components: { NavHeader, NavAside },
  data () {
    return {
      user: {},
      medicalRecForm: {
        doctor: '',
        patient: '',
        date: '',
        issue: '',
        // TODO: 待删除属性
        diagnosed_disease: '',
        allergens: ''
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
            doctor: this.medicalRecForm.doctor,
            patient: this.medicalRecForm.patient,
            issue: this.medicalRecForm.issue,
            diagnosed_disease: this.medicalRecForm.diagnosed_disease,
            allergens: this.medicalRecForm.allergens
          }
        })
        .then((resp) => {
          this.loading = false
          if (resp.status === 200) {
            this.$message({
              type: 'success',
              message: '病历编辑完成'
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
