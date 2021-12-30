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
              <el-form-item label="药品种类数量">
                <el-input  v-model="total_m_num"></el-input>
              </el-form-item>
              <el-form-item v-if="parseInt(total_m_num)>0">
                <el-select v-model="prescriptionForm.medicine_name1" placeholder="选择药品">
                  <el-option
                    v-for="item in options"
                    :key="item.value"
                    :label="item.label"
                    :value="item.value">
                  </el-option>
                </el-select>
              </el-form-item>
              <el-form-item label="药品数量" prop="medicine_num1" v-if="parseInt(total_m_num)>0">
                <el-input v-model="prescriptionForm.medicine_num1"></el-input>
              </el-form-item>
              <el-form-item v-if="parseInt(total_m_num)>1">
                <el-select v-model="prescriptionForm.medicine_name2" placeholder="选择药品">
                  <el-option
                    v-for="item in options"
                    :key="item.value"
                    :label="item.label"
                    :value="item.value">
                  </el-option>
                </el-select>
              </el-form-item>
              <el-form-item label="药品数量" prop="medicine_num2" v-if="parseInt(total_m_num)>1">
                <el-input v-model="prescriptionForm.medicine_num2"></el-input>
              </el-form-item>
              <el-form-item v-if="parseInt(total_m_num)>2">
                <el-select v-model="prescriptionForm.medicine_name3" placeholder="选择药品">
                  <el-option
                    v-for="item in options"
                    :key="item.value"
                    :label="item.label"
                    :value="item.value">
                  </el-option>
                </el-select>              
              </el-form-item>
              <el-form-item label="药品数量" prop="medicine_num3" v-if="parseInt(total_m_num)>2">
                <el-input v-model="prescriptionForm.medicine_num3"></el-input>
              </el-form-item>
              <el-form-item v-if="parseInt(total_m_num)>3">
                <el-select v-model="prescriptionForm.medicine_name4" placeholder="选择药品">
                  <el-option
                    v-for="item in options"
                    :key="item.value"
                    :label="item.label"
                    :value="item.value">
                  </el-option>
                </el-select>              
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
      total_m_num: '1',
      options: []
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
      this.$axios
        .get('/getMedicineInfo', {
          // 通过路由传参，获得药品列表
          params: {}
        })
        .then((resp) => {
          if (resp.status === 200) {
            this.options = resp.data.options
          }
        })
        .catch((error) => {
          console.log(error)
          this.$message.error('获取药品列表失败')
        })
    },
    submitForm (formName) {
      this.$axios
        .post('/editMedicalRecForm', null, {
          params: {
            d_id: this.user.id,
            p_id: this.$route.params.p_id,
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
