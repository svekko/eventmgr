<script setup lang="ts">
import Menubar from "primevue/menubar";
import { computed, inject, watch } from "vue";
import { useRouter } from "vue-router";
import Auth from "./auth/auth";

const auth = inject<Auth>("auth");
const router = useRouter();

const navItems = computed(() => {
  const elems = [
    {
      label: "Events",
      command: () => {
        router.push("/events");
      },
    },
  ];

  if (auth?.isAuthenticated()) {
    elems.push({
      label: "Logout",
      command: () => {
        logout();
      },
    });
  } else {
    elems.push({
      label: "Login",
      command: () => {
        router.push("/login");
      },
    });
  }

  return elems;
});

const logout = () => {
  auth?.logout();
};

watch([auth?.token], () => {
  console.log("token changed");
});
</script>

<template>
  <div class="page-container">
    <Menubar :model="navItems" />
    <div class="page-content">
      <RouterView />
    </div>
  </div>
</template>

<style>
* {
  font-family: Arial, Helvetica, sans-serif;
}
</style>

<style scoped>
.page-content {
  padding: 2rem;
}
</style>
