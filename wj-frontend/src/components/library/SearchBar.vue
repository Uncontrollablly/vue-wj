<template>
  <div class="search-bar">
    <el-input
      v-model="keywords"
      placeholder="通过书名或作者搜索..."
      prefix-icon="el-icon-search"
      size="small"
      style="width: 400px;margin-right: 10px"
      @keyup.enter.native="searchClick"
    />
    <el-button
      size="small"
      type="primary"
      icon="el-icon-search"
      @click="searchClick"
    >
      搜索
    </el-button>
  </div>
</template>

<script>
export default {
  name: 'SearchBar',
  data () {
    return {
      keywords: ''
    }
  },
  methods: {
    searchClick () {
      this.$emit('search', this.keywords)
    }
  }
}
</script>

<style scoped>
.search-bar {
  margin-bottom: 30px;
  display: flex;
  justify-content: center;
  align-items: center
}
</style>
