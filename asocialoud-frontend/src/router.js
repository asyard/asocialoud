import Vue from 'vue'
import Router from 'vue-router'
import Register from './components/Register'
import Login from '@/components/Login'
import HelloWorld from '@/components/HelloWorld'

Vue.use(Router);

const router = new Router({
    mode: 'history', // uris without hashes #, see https://router.vuejs.org/guide/essentials/history-mode.html#html5-history-mode
    routes: [
        { path: '/', component: HelloWorld},
        { path: '/register', component: Register},
        { path: '/login', component: Login },
        /*{
            path: '/protected',
            component: Protected,
            meta: {
                requiresAuth: true
            }
        },*/

        // otherwise redirect to home
        { path: '*', redirect: '/' }
    ]
});

/*router.beforeEach((to, from, next) => {
    if (to.matched.some(record => record.meta.requiresAuth)) {
        // this route requires auth, check if logged in
        // if not, redirect to login page.
        if (!store.getters.isLoggedIn) {
            next({
                path: '/login'
            })
        } else {
            next();
        }
    } else {
        next(); // make sure to always call next()!
    }
});*/

export default router;