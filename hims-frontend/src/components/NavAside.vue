<template>
  <el-container>
    <el-aside>
      <el-menu class="el-menu-vertical-demo" router>
        <el-menu-item index="/userInfo" v-if="!isAdmin">
          <i class="el-icon-user"></i>个人信息
        </el-menu-item>
        <el-menu-item index="/appointmentInfo" v-if="isPatient">
          <i class="el-icon-info"></i>预约信息
        </el-menu-item>
        <el-menu-item index="/appointmentOnline" v-if="isPatient">
          <i class="el-icon-circle-plus-outline"></i>线上预约
        </el-menu-item>
        <el-menu-item index="/prevPatient" v-if="isDoctor">
          <i class="el-icon-user-solid"></i>历史患者
        </el-menu-item>
        <el-menu-item index="/appointmentToday" v-if="isDoctor">
          <i class="el-icon-edit-outline"></i>今日预约
        </el-menu-item><el-menu-item index="/departmentMng" v-if="isLeader">
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
      isPatient: true,
      isDoctor: true,
      isLeader: true,
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
        // this.isPatient = this.user.user_type === 'patient'
        this.isDoctor = this.user.user_type === 'doctor'
        this.isLeader = this.user.user_type === 'leader'
        this.isAdmin = this.user.user_type === 'admin'
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
