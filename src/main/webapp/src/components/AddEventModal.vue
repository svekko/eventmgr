<script setup lang="ts">
import type { AxiosInstance } from "axios";
import Button from "primevue/button";
import DatePicker from "primevue/datepicker";
import Dialog from "primevue/dialog";
import FloatLabel from "primevue/floatlabel";
import InputNumber from "primevue/inputnumber";
import InputText from "primevue/inputtext";
import { inject, ref } from "vue";

const axios = inject<AxiosInstance>("axios");

const visible = defineModel<boolean>("visible", { default: false });

const emit = defineEmits<{
  add: [];
}>();

const title = ref("");
const maxEnrolments = ref<number | null>(null);
const datetime = ref<Date | null>(null);

async function handleAdd() {
  await axios?.post("/events", {
    title: title.value,
    maxEnrolments: maxEnrolments.value,
    eventDatetime: datetime.value,
  });

  clearData();
  emit("add");
}

function clearData() {
  title.value = "";
  maxEnrolments.value = null;
  datetime.value = null;
}
</script>

<template>
  <Dialog v-model:visible="visible" header="Add New Event" modal @hide="clearData">
    <form @submit.prevent="handleAdd" class="add-event-form">
      <FloatLabel>
        <InputText id="event-title" v-model="title" fluid required />
        <label for="event-title">Title</label>
      </FloatLabel>

      <FloatLabel>
        <InputNumber id="event-max-enrolments" v-model="maxEnrolments" :min="1" fluid required />
        <label for="event-max-enrolments">Max Enrolments</label>
      </FloatLabel>

      <FloatLabel>
        <DatePicker id="event-datetime" v-model="datetime" showTime dateFormat="yy-mm-dd" hourFormat="24" fluid required />
        <label for="event-datetime">Event Date & Time</label>
      </FloatLabel>

      <div class="dialog-actions">
        <Button type="button" label="Cancel" severity="secondary" @click="visible = false" />
        <Button type="submit" label="Add Event" />
      </div>
    </form>
  </Dialog>
</template>

<style scoped>
.add-event-form {
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
