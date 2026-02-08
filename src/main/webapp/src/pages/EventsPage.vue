<script setup lang="ts">
import Auth from "@/auth/auth";
import type { Event } from "@/dto/event";
import type { AxiosInstance } from "axios";
import Button from "primevue/button";
import Column from "primevue/column";
import DataTable from "primevue/datatable";
import { inject, onMounted, ref } from "vue";
import AddEventModal from "../components/AddEventModal.vue";
import EventRegistrationModal from "../components/EventRegistrationModal.vue";

const axios = inject<AxiosInstance>("axios");
const auth = inject<Auth>("auth");

const showRegisterModal = ref(false);
const showAddModal = ref(false);
const loading = ref(false);
const selectedEvent = ref<Event | null>(null);
const events = ref<Event[]>([]);

function openRegistrationModal(event: Event) {
  selectedEvent.value = event;
  showRegisterModal.value = true;
}

function openNewEventModal() {
  showAddModal.value = true;
}

function onRegisterToEvent() {
  showRegisterModal.value = false;
  selectedEvent.value = null;
  loadEvents();
}

function onAddEvent() {
  showAddModal.value = false;
  loadEvents();
}

async function loadEvents() {
  loading.value = true;

  try {
    const resp = await axios?.get("/events");
    events.value = resp?.data;
  } finally {
    loading.value = false;
  }
}

onMounted(() => {
  loadEvents();
});
</script>

<template>
  <div class="events-page">
    <div class="events-header">
      <h1>Events</h1>
      <Button label="Add Event" severity="info" @click="openNewEventModal" v-if="auth?.isAuthenticated()" />
    </div>

    <p v-if="loading">Loading...</p>

    <DataTable :value="events" stripedRows v-else-if="events.length">
      <Column field="title" header="Title" />
      <Column field="maxEnrolments" header="Max enrolments" />
      <Column field="noOfEnrolments" header="Number of enrolments" />
      <Column field="eventDatetime" header="Datetime" />
      <Column header="Actions">
        <template #body="{ data }">
          <Button label="Register" severity="success" size="small" @click="openRegistrationModal(data)" />
        </template>
      </Column>
    </DataTable>

    <p v-else>No events found</p>

    <EventRegistrationModal v-model:visible="showRegisterModal" :event="selectedEvent" @register="onRegisterToEvent" />
    <AddEventModal v-model:visible="showAddModal" @add="onAddEvent" />
  </div>
</template>

<style scoped>
.events-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 1rem;
}
</style>
