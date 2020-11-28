<template>
  <div id="app">
    <div id="nav">
      <router-link to="/index">
        Index
      </router-link> |
      <router-link to="/login">
        Login
      </router-link>
    </div>
    <router-view />
  </div>
</template>

<script>
export default {

}
</script>

<style lang="less">
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  color: white;
  width: 100%;
  height: 100%;
}

#nav {
  padding: 30px;
  position: relative;
  z-index: 1;

  a {
    font-weight: bold;
    color: #ffffff;

    &.router-link-exact-active {
      color: #42b983;
    }
  }
}
</style>
