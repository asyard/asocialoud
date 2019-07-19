import Vue from 'vue'
import App from './App.vue'
import VueRouter from 'vue-router'
import routes from './router'
import BootstrapVue from 'bootstrap-vue'
import 'bootstrap/dist/css/bootstrap.css'
import 'bootstrap-vue/dist/bootstrap-vue.css'

Vue.config.productionTip = false

import VueLogger from 'vuejs-logger';

const options = {
    isEnabled: true,
    logLevel: 'debug',
    stringifyArguments: false,
    showLogLevel: true,
    showMethodName: false,
    separator: '|',
    showConsoleColors: true
};

Vue.use(BootstrapVue);
Vue.use(VueRouter);
Vue.use(VueLogger, options);

const router = new VueRouter({/*mode: 'history', */routes});


new Vue({
    render: h => h(App),
    router,
}).$mount('#app')
