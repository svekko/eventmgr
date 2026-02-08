<script setup lang="ts">
import type { Event } from "@/dto/event";
import { type AxiosInstance } from "axios";
import Button from "primevue/button";
import Dialog from "primevue/dialog";
import FloatLabel from "primevue/floatlabel";
import InputText from "primevue/inputtext";
import { inject, ref } from "vue";

const axios = inject<AxiosInstance>("axios");

const props = defineProps<{
  event: Event | null;
}>();

const visible = defineModel<boolean>("visible", { default: false });

const emit = defineEmits<{
  close: [];
  register: [];
}>();

const idCode = ref("");
const firstName = ref("");
const lastName = ref("");

async function handleRegister() {
  await axios?.post("/events/register", {
    eventId: props.event?.id,
    idCode: idCode.value,
    firstName: firstName.value,
    lastName: lastName.value,
  });

  clearData();
  emit("register");
}

function clearData() {
  idCode.value = "";
  firstName.value = "";
  lastName.value = "";
}
</script>

<template>
  <Dialog v-model:visible="visible" v-if="props.event" :header="`Register for ${props.event.title}`" modal @hide="clearData">
    <form @submit.prevent="handleRegister" class="registration-form">
      <FloatLabel>
        <InputText id="reg-id-code" v-model="idCode" fluid required />
        <label for="reg-id-code">ID code</label>
      </FloatLabel>

      <FloatLabel>
        <InputText id="reg-firstName" v-model="firstName" fluid required />
        <label for="reg-firstName">First name</label>
      </FloatLabel>

      <FloatLabel>
        <InputText id="reg-lastName" v-model="lastName" fluid required />
        <label for="reg-lastName">Last name</label>
      </FloatLabel>

      <div class="dialog-actions">
        <Button type="button" label="Cancel" severity="secondary" @click="visible = false" />
        <Button type="submit" label="Register" />
      </div>
    </form>
  </Dialog>
</template>

<style scoped>
.registration-form {
  display: flex;
  flex-direction: column;
  gap: 1.75rem;
  margin-top: 1.25rem;
  min-width: 420px;
}

.dialog-actions {
  display: flex;
  justify-content: flex-end;
  gap: 0.5rem;
}
</style>
