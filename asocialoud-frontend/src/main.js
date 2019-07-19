import Vue from 'vue'
import App from './App.vue'
//import VueRouter from 'vue-router'
import router from './router'

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

//Vue.use(VueRouter);
Vue.use(VueLogger, options);


new Vue({
    router,
    render: h => h(App),
}).$mount('#app')
