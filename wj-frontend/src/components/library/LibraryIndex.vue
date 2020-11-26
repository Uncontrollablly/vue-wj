<template>
  <el-container>
    <el-aside
      width="150px"
      style="margin-top: 20px;"
    >
      <!-- <switch /> -->
      <SideMenu />
    </el-aside>
    <el-main>
      <Content class="books-area" />
    </el-main>
  </el-container>
</template>

<script>
import SideMenu from './SideMenu'
import Content from './Content'

export default {
  name: 'AppLibrary',
  components: { SideMenu, Content }
}
</script>

<style scoped>

</style>
