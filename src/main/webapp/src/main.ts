import Aura from '@primeuix/themes/aura';
import axios from "axios";
import PrimeVue from "primevue/config";
import { createApp } from 'vue';
import { createRouter, createWebHistory } from "vue-router";
import App from './App.vue';
import Auth from "./auth/auth";
import EventsPage from "./pages/EventsPage.vue";
import LoginPage from "./pages/LoginPage.vue";

const app = createApp(App);

app.use(PrimeVue, {
  theme: {
    preset: Aura,
  },
});

const axiosInstance = axios.create({
  baseURL: import.meta.env.VITE_API_ENDPOINT,
});

const authInstance = new Auth(axiosInstance);

axiosInstance.interceptors.request.use(
  (config) => {
    const token = authInstance.getActiveToken();
    if (token) {
      config.headers["Authorization"] = `Bearer ${token.originalValue}`;
    }

    return config;
  }
);

axiosInstance.interceptors.response.use(
  (resp) => {
    return resp;
  },
  (err) => {
    alert(err.response?.data?.detail ?? err.message);
    throw err;
  },
)

const routes = [
  { path: '/', component: LoginPage },
  { path: '/login', component: LoginPage },
  { path: '/events', component: EventsPage },
]

export const router = createRouter({
  history: createWebHistory(),
  routes,
});

app.provide("axios", axiosInstance);
app.provide("auth", authInstance);

app.use(router);

app.mount("#app");
