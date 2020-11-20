import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import axios from 'axios'
import ElementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'
import './assets/index.css'

axios.defaults.baseURL = 'http://localhost:8443/api'

Vue.prototype.$axios = axios
Vue.config.productionTip = false
Vue.use(ElementUI)

router.beforeEach((to, from, next) => {
  if (to.meta.requireAuth) {
    if (store.state.user.username) {
      next()
    } else {
      next({
        path: 'login',
        query: { redirect: to.fullPath }
      })
    }
  } else {
    next()
  }
}
)

new Vue({
  router,
  store,
  render: h => h(App)
}).$mount('#app')

// 为什么不添加router任意路径都会匹配到index.html
/**
 * 起作用的选项：devServer.historyApiFallback
 * 设置的位置：node_modules\@vue\vue-cli-serivice\commands\serve.js 中
 */
