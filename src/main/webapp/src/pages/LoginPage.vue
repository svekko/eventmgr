<script setup lang="ts">
import Auth from "@/auth/auth";
import Button from "primevue/button";
import Card from "primevue/card";
import FloatLabel from "primevue/floatlabel";
import InputText from "primevue/inputtext";
import Password from "primevue/password";
import { inject, onMounted, ref } from "vue";
import { useRouter } from "vue-router";

const auth = inject<Auth>("auth");
const router = useRouter();

const username = ref("");
const password = ref("");

async function handleLogin() {
  try {
    await auth?.login(username.value, password.value);
    username.value = "";
    router.push("/events");
  } finally {
    password.value = "";
  }
}

onMounted(() => {
  if (auth?.isAuthenticated()) {
    router.push("/events");
  }
});
</script>

<template>
  <div class="login-page">
    <Card style="max-width: 400px; margin: 0 auto">
      <template #title>Login</template>
      <template #content>
        <form @submit.prevent="handleLogin" class="login-form">
          <FloatLabel>
            <InputText id="username" v-model="username" fluid required />
            <label for="username">Username</label>
          </FloatLabel>

          <FloatLabel>
            <Password id="password" v-model="password" :feedback="false" fluid required />
            <label for="password">Password</label>
          </FloatLabel>

          <Button type="submit" label="Login" />
        </form>
      </template>
    </Card>
  </div>
</template>

<style scoped>
.login-form {
  display: flex;
  flex-direction: column;
  gap: 1.75rem;
  margin-top: 1.25em;
}
</style>
