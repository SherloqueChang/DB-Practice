<template>
  <div id="app">
    <router-view v-if="isRouterAlive"></router-view>
  </div>
</template>

<script>
export default {
  name: 'App',
  // 在最顶级的组件设置provider，在子组件中通过inject注入，然后在需要刷新的地方调用reload方法
  provide () {
    return {
      reload: this.reload
    }
  },
  data () {
    return {
      isRouterAlive: true
    }
  },
  methods: {
    reload () {
      this.isRouterAlive = false
      this.$nextTick(function () {
        this.isRouterAlive = true
      })
    }
  }
}
</script>

<style>
/* 解决el-table表头与内容列不对齐 */
.el-table th.gutter {
  display: table-cell !important;
}
</style>
