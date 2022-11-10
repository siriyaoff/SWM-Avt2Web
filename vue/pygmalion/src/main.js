import Vue from 'vue'
import App from './App.vue'
import { router } from './routes/index.js'
import { BootstrapVue } from 'bootstrap-vue'
import 'bootstrap/dist/css/bootstrap.min.css'
import 'bootstrap-vue/dist/bootstrap-vue.css'
import unity from 'vue-unity-webgl'

Vue.config.productionTip = false
Vue.use(BootstrapVue)
new Vue({
  render: h => h(App),
  router,
  unity
}).$mount('#app')