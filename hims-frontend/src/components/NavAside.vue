<template>
  <el-container>
    <el-aside>
      <el-menu class="el-menu-vertical-demo" router>
        <el-menu-item index="/userInfo" v-if="!isAdmin">
          <i class="el-icon-user"></i><span slot="title">个人信息</span>
        </el-menu-item>
        <el-submenu index="1" v-if="isAdmin || isPatient">
          <template slot="title">
            <i class="el-icon-info"></i><span>就医信息</span>
          </template>
          <el-menu-item index="/medicalInfo">就诊记录</el-menu-item>
          <el-menu-item index="/appointmentInfo">预约记录</el-menu-item>
        </el-submenu>
        <el-menu-item index="/appointmentOnline" v-if="isPatient || isAdmin">
          <i class="el-icon-circle-plus-outline"></i>线上预约
        </el-menu-item>
        <el-menu-item index="/prevPatient" v-if="isDoctor || isAdmin">
          <i class="el-icon-user-solid"></i>历史患者
        </el-menu-item>
        <el-menu-item index="/appointmentToday" v-if="isDoctor || isAdmin">
          <i class="el-icon-edit-outline"></i>今日预约
        </el-menu-item><el-menu-item index="/departmentMng" v-if="isLeader || isAdmin">
          <i class="el-icon-s-home"></i>科室管理
        </el-menu-item><el-menu-item index="/medicineMng" v-if="isAdmin">
          <i class="el-icon-first-aid-kit"></i>药品管理
        </el-menu-item>
      </el-menu>
    </el-aside>
  </el-container>
</template>

<script>
export default {
  name: 'NavAside',
  data () {
    return {
      user: {},
      id: '',
      password: '',
      isPatient: false,
      isDoctor: false,
      isLeader: false,
      isAdmin: false
    }
  },
  created () {
    this.handleUserData()
  },
  methods: {
    handleUserData () {
      if (this.$store.state.user) {
        this.user = this.$store.state.user
        this.isPatient = true
        // this.isPatient = this.user.u_type === 'patient'
        this.isDoctor = this.user.u_type === 'doctor'
        this.isLeader = this.user.u_type === 'leader'
        this.isAdmin = this.user.u_type === 'admin'
      }
    }
  }
}
</script>

<style>
.aside {
  /*width: 15%;*/
}
</style>
