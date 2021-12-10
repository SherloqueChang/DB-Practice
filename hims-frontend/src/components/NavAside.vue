<template>
  <el-container>
    <el-aside>
      <el-menu class="el-menu-vertical-demo" router>
        <el-menu-item index="/appointmentInfo">
          <i class="el-icon-info"></i>预约信息
        </el-menu-item>
        <el-menu-item index="/appointmentOnline">
          <i class="el-icon-circle-plus-outline"></i>线上预约
        </el-menu-item><el-menu-item index="/prevPatient">
          <i class="el-icon-user-solid"></i>历史患者
        </el-menu-item><el-menu-item index="/appointmentToday">
          <i class="el-icon-user"></i>今日预约
        </el-menu-item><el-menu-item index="/departmentMng">
          <i class="el-icon-s-home"></i>科室管理
        </el-menu-item><el-menu-item index="/medicineMng">
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
      id: '',
      password: ''
    }
  }
}
</script>

<style>
.aside {
  /*width: 15%;*/
}
</style>
