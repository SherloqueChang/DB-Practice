<template>
  <el-container>
    <el-header>
      <el-menu mode="horizontal" router>
        <el-menu-item @click="handleLogout" style="float: right">退出</el-menu-item>
      </el-menu>
    </el-header>
  </el-container>
</template>

<script>
export default {
  name: 'NavHeader',
  data () {
    return {
      id: '',
      password: ''
    }
  },
  methods: {
    handleLogout () {
      this.$store.commit('logout')
      this.$router.push('/login')
    }
  }
}
</script>
