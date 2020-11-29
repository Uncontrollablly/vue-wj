<template>
  <el-container>
    <el-aside
      width="150px"
      style="margin-top: 20px;"
    >
      <!-- <switch /> -->
      <SideMenu
        @select-category="listByCategory"
      />
    </el-aside>
    <el-main>
      <Content
        ref="booksArea"
        class="books-area"
      />
    </el-main>
  </el-container>
</template>

<script>
import SideMenu from './SideMenu'
import Content from './Content'

export default {
  name: 'AppLibrary',
  components: { SideMenu, Content },
  methods: {
    listByCategory (cid) {
      const url = 'categories/' + cid + '/books'
      this.$axios.get(url).then(resp => {
        if (resp && resp.status === 200) {
          this.$refs.booksArea.books = resp.data
        }
      })
    }
  }
}
</script>

<style scoped>

</style>
