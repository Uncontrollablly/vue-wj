<template>
  <el-menu
    class="categories"
    default-active="0"
    active-text-color="red"
    @select="handleSelect"
  >
    <el-menu-item index="0">
      <i class="el-icon-menu" />
      <span slot="title">全部</span>
    </el-menu-item>
    <el-menu-item
      v-for="item in options"
      :key="item.value"
      :index="item.value"
    >
      <i class="el-icon-menu" />
      <span slot="title">{{ item.label }}</span>
    </el-menu-item>
  </el-menu>
</template>

<script>

export default {
  name: 'SideMenu',
  props: {
    options: {
      type: Array,
      default: () => []
    }
  },
  methods: {
    handleSelect (key, keyPath) {
      this.$emit('select-category', key)
    }
  }
}
</script>

<style scoped>

</style>
