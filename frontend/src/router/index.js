import { createRouter, createWebHistory } from "vue-router";
import UserList from "../components/UserList.vue";
import UserDetail from "../components/UserDetail.vue";

const routes = [
    { path: "/", component: UserList },
    { path: "/user/:id", component: UserDetail, props: true }, // detail user
];

const router = createRouter({
    history: createWebHistory(),
    routes,
});

export default router;
