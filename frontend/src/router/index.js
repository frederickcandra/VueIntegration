import { createRouter, createWebHistory } from "vue-router";
import Login from "../components/auth/Login.vue";
import Register from "../components/auth/Register.vue";
import Dashboard from "../components/Dashboard.vue";

const routes = [
    {
        path: "/login",
        component: Login,
        meta: { requiresGuest: true }
    },
    {
        path: "/register",
        component: Register,
        meta: { requiresGuest: true }
    },
    {
        path: "/",
        component: Dashboard,
        meta: { requiresAuth: true }
    }
];

const router = createRouter({
    history: createWebHistory(),
    routes,
});

router.beforeEach((to, from, next) => {
    const token = localStorage.getItem('token');

    if (to.meta.requiresAuth && !token) {
        next('/login');
    } else if (to.meta.requiresGuest && token) {
        next('/');
    } else {
        next();
    }
});

export default router;
