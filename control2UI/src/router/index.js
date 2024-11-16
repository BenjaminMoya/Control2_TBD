import { createRouter, createWebHistory } from 'vue-router';

const router = createRouter({

    history: createWebHistory(import.meta.env.BASE_URL),
    routes: [
        {
            path: '/',
            name: 'login',
            component: () => import('../views/login.vue'),
            alias: '/login',
        },
        {
            path: '/home',
            name: 'home',
            component: () => import('../views/home.vue'),
            alias: '/inicio',
        },
        {
            path: '/create',
            name: 'create',
            component: () => import('../views/createTask.vue'),
            alias: '/crear',
        },
        
        
    ],
});

export default router;