import Vue from 'vue'
import VueRouter from 'vue-router'
import MainView from '../views/MainView.vue'
import ServiceView from '../views/ServiceView.vue'

Vue.use(VueRouter);

export const router = new VueRouter({
    mode: 'history',
    routes: [
        {
            path: '/',
            redirect: '/main',
        },
        {
            path: '/main',
            component: MainView,
            meta: {
                title: 'Pygmalion'
            }
        },
        {
            path: '/service',
            component: ServiceView,
            meta: {
                title: 'Service'
            }
        },
    ]
});

router.afterEach((to) => {
  Vue.nextTick(() => {
    document.title = to.meta.title;
  });
});