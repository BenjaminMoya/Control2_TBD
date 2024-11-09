import { createRouter, createWebHistory } from 'vue-router';

const router = createRouter({

    history: createWebHistory(import.meta.env.BASE_URL),
    routes: [
        {
            path: '/',
            name: 'home',
            component: () => import('../views/home.vue'),
            alias: '/inicio',
        },
        {
            path: '/login',
            name: 'login',
            component: () => import('../views/login.vue'),
            alias: '/login',
        },
        
        
    ],
});

export default router;