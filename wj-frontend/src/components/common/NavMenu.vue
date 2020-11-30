<template>
  <el-menu
    :default-active="$route.path"
    router
    mode="horizontal"
    background-color="white"
    text-color="#222"
    active-text-color="#ff0000"
    style="min-width: 1300px"
  >
    <el-menu-item
      v-for="(item,i) in navList"
      :key="i"
      :index="item.name"
    >
      {{ item.navItem }}
    </el-menu-item>
    <a
      href="#nowhere"
      style="color: #222;float: right;padding: 20px;"
    >更多功能</a>
    <i
      class="el-icon-menu"
      style="float:right;font-size: 45px;color: #222;padding-top: 8px"
    />
    <span
      style="color: #222;position: absolute;padding-top: 20px;right: 43%;font-size: 20px;font-weight: bold"
    >
      White Jotter
    </span>
  </el-menu>
</template>

<script>
export default {
  name: 'NavMenu',
  data () {
    return {
      navList: [
        { name: '/index', navItem: '首页' },
        { name: '/jotter', navItem: '笔记本' },
        { name: '/library', navItem: '图书馆' },
        { name: '/admin', navItem: '个人中心' }
      ]
    }
  }
}
</script>

<style scoped>
  a{
    text-decoration: none;
  }

  span {
    /* 元素永远不会成为鼠标事件的target。
    但是，当其后代元素的pointer-events属性指定其他值时，鼠标事件可以指向后代元素，
    在这种情况下，鼠标事件将在捕获或冒泡阶段触发父元素的事件侦听器。
    可继承，初始值auto
    */
    pointer-events: none;
  }
</style>
