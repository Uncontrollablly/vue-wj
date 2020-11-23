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
      <Content />
      <AddBook />
    </el-main>
  </el-container>
</template>

<script>
import SideMenu from './SideMenu'
import Content from './Content'
import AddBook from './AddBook'

export default {
  name: 'AppLibrary',
  components: { SideMenu, Content, AddBook }
}
</script>

<style scoped>

</style>
