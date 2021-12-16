<template>
  <el-container>
    <el-header><NavHeader></NavHeader></el-header>
    <el-container>
      <el-aside><NavAside></NavAside></el-aside>
      <el-main>
        <el-tabs type="border-card" v-model="activeName" @tab-click="handleClick">
          <el-tab-pane label="查询信息" name="first">
            <el-form :inline="true" class="demo-form-inline">
              <el-form-item>
                <div class="block">
                  <el-date-picker
                    value-format="yyyy-MM-dd"
                    v-model="date"
                    type="date"
                    placeholder="选择日期">
                  </el-date-picker>
                </div>
              </el-form-item>
              <el-form-item>
                <el-select v-model="department" placeholder="选择科室">
                  <el-option
                    v-for="item in options"
                    :key="item.value"
                    :label="item.label"
                    :value="item.value">
                  </el-option>
                </el-select>
              </el-form-item>
              <el-form-item>
                <el-button type="primary" @click="submitQuery">查询</el-button>
              </el-form-item>
            </el-form>
            <el-table
              :data="appointmentQueryTable"
              height="250"
              border
              style="width: 100%">
              <el-table-column
                prop="id"
                label="医生编号">
              </el-table-column>
              <el-table-column
                prop="doctor"
                label="医生姓名">
              </el-table-column>
              <el-table-column
                prop="availableAppointment"
                label="剩余预约名额">
              </el-table-column>
              <el-table-column align="right">
                <template slot-scope="scope">
                  <el-button
                    size="mini"
                    @click="getDoctorInfo(scope.$index, scope.row)"
                    >详情</el-button>
                </template>
              </el-table-column>
            </el-table>
          </el-tab-pane>
          <el-tab-pane label="新建预约" name="second">
            <el-form ref="form" :model="surveyForm" label-width="330px">
              <el-form-item label="患者姓名">
                <el-input v-model="surveyForm.name"></el-input>
              </el-form-item>
              <el-form-item label="日期">
                <el-col :span="11">
                  <el-date-picker
                    value-format="yyyy-MM-dd"
                    type="date"
                    placeholder="选择日期"
                    v-model="surveyForm.cur_date"
                    style="width: 100%;"></el-date-picker>
                </el-col>
              </el-form-item>
              <el-form-item label="性别">
                <el-select v-model="surveyForm.gender" placeholder="请选择性别">
                  <el-option label="男" value="male"></el-option>
                  <el-option label="女" value="female"></el-option>
                </el-select>
              </el-form-item>
              <el-form-item label="身份证号">
                <el-input v-model="surveyForm.idcard"></el-input>
              </el-form-item>
              <el-form-item label="电话">
                <el-input v-model="surveyForm.phone"></el-input>
              </el-form-item>
              <el-form-item label="地址">
                <el-input v-model="surveyForm.address"></el-input>
              </el-form-item>
              <el-form-item label="14天内是否有发热或呼吸道症状">
                <el-radio-group v-model="surveyForm.whether_14days_fever">
                  <el-radio label="是"></el-radio>
                  <el-radio label="否"></el-radio>
                </el-radio-group>
              </el-form-item>
              <el-form-item label="发热描述">
                <el-input v-model="surveyForm.fever_info"></el-input>
              </el-form-item>
              <el-form-item label="14日内是否到过国内中高风险地区">
                <el-radio-group v-model="surveyForm.whether_14days_area">
                  <el-radio label="是"></el-radio>
                  <el-radio label="否"></el-radio>
                </el-radio-group>
              </el-form-item>
              <el-form-item label="地区信息">
                <el-input v-model="surveyForm.area_info"></el-input>
              </el-form-item>
              <el-form-item label="14天内是否与新冠患者密切接触">
                <el-radio-group v-model="surveyForm.whether_14days_contact">
                  <el-radio label="是"></el-radio>
                  <el-radio label="否"></el-radio>
                </el-radio-group>
              </el-form-item>
              <el-form-item label="接触者信息">
                <el-input v-model="surveyForm.contact_info"></el-input>
              </el-form-item>
              <el-form-item label="14天内是否与中高风险地区或境外人员有密切接触">
                <el-radio-group v-model="surveyForm.whether_14days_contact_area">
                  <el-radio label="是"></el-radio>
                  <el-radio label="否"></el-radio>
                </el-radio-group>
              </el-form-item>
              <el-form-item label="中高风险地区信息">
                <el-input v-model="surveyForm.contact_area_info"></el-input>
              </el-form-item>
              <el-form-item>
                <el-button type="primary" @click="submitForm">提交</el-button>
                <el-button @click="goBack">取消</el-button>
              </el-form-item>
            </el-form>
          </el-tab-pane>
        </el-tabs>
        <el-dialog title="预约就医" :visible.sync="dialogFormVisible">
          <el-form :model="appoinmentForm" :inline="true">
            <el-form-item label="预约日期" :label-width="formLabelWidth">
              <div class="block">
                <el-date-picker
                  value-format="yyyy-MM-dd"
                  v-model="appoinmentForm.date"
                  type="date"
                  placeholder="选择日期">
                </el-date-picker>
              </div>
            </el-form-item>
            <el-form-item label="预约科室" :label-width="formLabelWidth">
              <el-select v-model="appoinmentForm.department" placeholder="请选择科室">
                <el-option label="内科" value="d1"></el-option>
                <el-option label="外科" value="d2"></el-option>
                <el-option label="妇产科" value="d3"></el-option>
                <el-option label="儿科" value="d4"></el-option>
                <el-option label="其他" value="d5"></el-option>
              </el-select>
            </el-form-item>
            <el-form-item label="预约医生" :label-width="formLabelWidth">
              <el-input v-model="appoinmentForm.doctor" autocomplete="off"></el-input>
            </el-form-item>
          </el-form>
          <div slot="footer" class="dialog-footer">
            <el-button @click="dialogFormVisible = false">取 消</el-button>
            <el-button type="primary" @click="finalSubmit">确 定</el-button>
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
  name: 'AppointmentOnline',
  components: { NavAside, NavHeader },
  data () {
    return {
      user: {},
      date: '',
      department: '',
      // 可能需要列出key
      appointmentQueryTable: [],
      activeName: 'first',
      surveyForm: {
        cur_date: '',
        patient: '',
        gender: '',
        idcard: '',
        phone: '',
        address: '',
        whether_14days_fever: '',
        fever_info: '',
        whether_14days_area: '',
        area_info: '',
        whether_14days_contact: '',
        contact_info: '',
        whether_14days_contact_area: '',
        contact_area_info: ''
      },
      rules: {
        patient: { required: true, message: '请输入姓名', blur: 'change' },
        cur_date: { required: true, message: '请输入日期', blur: 'change' },
        gender: { required: true, message: '请输入性别', blur: 'change' },
        idcard: { required: true, message: '请输入身份证号', blur: 'change' },
        phone: { required: true, message: '请输入电话', blur: 'change' },
        address: { required: true, message: '请输入地址', blur: 'change' },
        whether_14days_fever: { required: true, message: '请输入姓名', blur: 'change' },
        fever_info: { required: false, blur: 'change' },
        whether_14days_area: { required: true, blur: 'change' },
        area_info: { required: false, blur: 'change' },
        whether_14days_contact: { required: true, message: '请输入姓名', blur: 'change' },
        contact_info: { required: false, blur: 'change' },
        whether_14days_contact_area: { required: true, message: '请输入姓名', blur: 'change' },
        contact_area_info: { required: false, blur: 'change' }
      },
      options: [{
        // 之后应从数据库中获取科室类别
        value: '1',
        label: '内科'
      }, {
        value: '2',
        label: '外科'
      }, {
        value: '3',
        label: '妇产科'
      }, {
        value: '4',
        label: '儿科'
      }, {
        value: '5',
        label: '其他'
      }],
      dialogFormVisible: false,
      appoinmentForm: {
        date: '',
        department: '',
        doctor: ''
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
    handleClick (tab) {
      if (tab.index === '0') {
        this.submitQuery()
      }
    },
    submitQuery () {
      // 获取某一日期，某一科室中的有空余预约名额的医生信息
      this.$axios
        .get('/appointmentQuery', {
          params: { date: this.date, department: this.department }
        })
        .then((resp) => {
          resp.data.appointmentQuery.forEach((element) => {
            this.appointmentQueryTable.push({
              id: element.id,
              doctor: element.doctor,
              availableAppointment: element.availableAppointment
            })
          })
        })
        .catch((error) => {
          console.log(error)
          this.$message.error('请求错误，请重试')
        })
    },
    getDoctorInfo (index, row) {
      // 查看表中某一医生的详细信息
      this.$router.push('/doctorInfo' + row.id)
    },
    submitForm () {
      // 提交流调表
      this.$axios
        .post('/submitSurveyForm', {
          params: {
            name: this.surveyForm.patient,
            cur_date: this.surveyForm.cur_date,
            gender: this.surveyForm.gender,
            idcard: this.surveyForm.idcard,
            phone: this.surveyForm.phone,
            address: this.surveyForm.address,
            whether_14days_fever: this.surveyForm.whether_14days_fever,
            fever_info: this.surveyForm.fever_info,
            whether_14days_area: this.surveyForm.whether_14days_area,
            area_info: this.surveyForm.area_info,
            whether_14days_contact: this.surveyForm.whether_14days_contact,
            contact_info: this.surveyForm.contact_info,
            whether_14days_contact_area: this.surveyForm.whether_14days_contact_area,
            contact_area_info: this.surveyForm.contact_area_info
          }
        })
        .then((resp) => {
          if (resp.status === 200 && resp.data.hasOwnProperty('ok')) {
            // 进行正常预约流程
            this.dialogFormVisible = true
          } else if (resp.status === 200 && resp.data.hasOwnProperty('risk')) {
            // 转到专门的发热门诊
            this.$message.error('请预约发热门诊')
            this.$router.push('/feverAppointment')
          }
        })
        .catch((error) => {
          console.log(error)
          this.$message.error('请求错误，请重试')
        })
    },
    goBack () {
      this.activeName = 'first'
    },
    finalSubmit () {
      // 提交最终的预约信息
      this.$axios
        .post('/finalSubmit', {
          params: {
            date: this.appoinmentForm.date,
            department: this.appoinmentForm.department,
            doctor: this.appoinmentForm.doctor
          }
        })
        .then((resp) => {
          if (resp.status === 200) {
            this.dialogFormVisible = false
            this.$message({
              type: 'success',
              message: '预约成功！'
            })
            this.activeName = 'first'
          //  是否需要reload
          }
        })
        .catch((error) => {
          console.log(error)
          this.$message.error('请求错误，请重试')
        })
    }
  }
}
</script>
