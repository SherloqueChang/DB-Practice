<template>
  <el-container>
    <el-header><NavHeader></NavHeader></el-header>
    <el-container>
      <el-aside><NavAside></NavAside></el-aside>
      <el-main>
      </el-main>
    </el-container>
  </el-container>
</template>

<script>

import NavHeader from '../components/NavHeader'
import NavAside from '../components/NavAside'

export default {
  name: 'PatientPanel',
  components: { NavHeader, NavAside },
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
