import Vue from 'vue'
import App from './App.vue'
import router from './router'
import BootstrapVue from 'bootstrap-vue'
import 'bootstrap/dist/css/bootstrap.css'
import 'bootstrap-vue/dist/bootstrap-vue.css'
import store from './store'
import i18n from "./i18n";


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
Vue.use(VueLogger, options);
Vue.use(require('vue-moment'));

new Vue({
    router,
    store,
    i18n,
    render: h => h(App),
}).$mount('#app')
