<template>
  <el-container>
    <el-aside
      width="150px"
      style="margin-top: 20px;"
    >
      <!-- <switch /> -->
      <SideMenu
        :options="options"
        @select-category="changeCategory"
      />
      <!-- 1 -->
      <!-- <SideMenu
        :options="[{
                     label: '文学',
                     value: '1'
                   },
                   {
                     label: '流行',
                     value: '2'
                   }]"
        @select-category="listByCategory"
      /> -->
      <!-- 2 -->
      <!-- <SideMenu
        :options="options"
        @select-category="listByCategory"
      /> -->
    </el-aside>
    <el-main>
      <Content
        class="books-area"
        :category="selectedCategory"
      />
    </el-main>
  </el-container>
</template>

<script>
import SideMenu from './SideMenu'
import Content from './Content'
import { OPTIONS } from '../../constants/index'

// 2
// const options = [{
//   label: '文学',
//   value: '1'
// },
// {
//   label: '流行',
//   value: '2'
// }]

export default {
  name: 'AppLibrary',
  components: { SideMenu, Content },
  data () {
    return {
      options: OPTIONS,
      selectedCategory: ''
    }
  },
  methods: {
    changeCategory (cid) {
      this.selectedCategory = cid
    }
  }
}
</script>

<style scoped>

</style>
