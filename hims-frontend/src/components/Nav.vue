<script src="../router/index.js"></script>
<template>
  <el-container>
    <el-header>
      <el-menu mode="horizontal" router>
        <el-menu-item index="0"><strong>医院信息管理系统</strong></el-menu-item>
        <el-menu-item index="/workerDataPanel" v-if="isHeadNurse || isDoctor"
          >医护面板</el-menu-item
        >
        <el-menu-item index="/patientDataPanel">病人面板</el-menu-item>
        <el-menu-item index="/bedDataPanel" v-if="isHeadNurse"
          >床位面板</el-menu-item
        >
        <el-menu-item index="/messages">消息</el-menu-item>
        <el-menu-item @click="handleLogout" style="float: right">登出</el-menu-item>
        <el-menu-item index="/userInfo" style="float: right">我的</el-menu-item>
      </el-menu>
    </el-header>
  </el-container>
</template>

<script>
export default {
  name: 'navmenu',
  data () {
    return {
      user: {},
      isHeadNurse: false,
      isDoctor: false
    }
  },
  created () {
    this.handleUserData()
  },
  methods: {
    handleUserData () {
      if (this.$store.state.user) {
        this.user = this.$store.state.user
        this.isHeadNurse = this.user.user_type === 'h_nurse'
        this.isDoctor = this.user.user_type === 'doctor'
      }
    },
    handleLogout () {
      // 需要向后端发送请求，关闭数据库连接
      this.$store.commit('logout')
      this.$router.push('/login')
    }
  }
}
</script>

<style>
</style>
