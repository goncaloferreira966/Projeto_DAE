<template>
  <div class="container">
    <div v-if="error" class="alert alert-danger mt-4">Error: {{ error.message }}</div>

    <div v-else class="col-md-12 mt-5">
      <div v-if="!clients" class="d-flex justify-content-center">
        <!-- Spinner enquanto os dados são carregados -->
        <div class="spinner-border text-primary" role="status">
          <span class="sr-only">Loading...</span>
        </div>
      </div>
      <div v-else>
        <!-- Cabeçalho da página -->
        <div class="d-flex justify-content-between align-items-center mb-4">
          <h2><i class="bi bi-people"></i> WeDelivery - Clients</h2>
          <button @click.prevent="refresh" class="btn btn-info"><i class="bi bi-arrow-clockwise"></i> Refresh Data</button>
        </div>

        <!-- Dropdown para filtragem por nome de cliente -->
        <div class="col-md-2 mt-4">
          <select v-model="selectedUsername" class="form-select">
            <option value="">Select a Client</option>
            <option v-for="username in uniqueUsernames" :key="username" :value="username">
              {{ username }}
            </option>
          </select>
        </div>

        <!-- Cards com informação dos clientes -->
        <div class="row mt-5">
          <div class="col-lg-4 col-md-6 mb-4" v-for="client in filteredClients" :key="client.username">
            <div class="card border-secondary">
              <div class="card-header bg-secondary text-white">
                {{ client.name }}  
              </div>
              <div class="card-body">
                <p><strong>Email:</strong> {{ client.email }}</p>
                <p><strong>City:</strong> {{ client.city }}</p>
                <p><strong>Username:</strong> {{ client.username }}</p>
                <nuxt-link :to="`clients/${client.username}`" class="btn btn-dark btn-block btn-sm">
                  <i class="bi bi-eye-fill"></i> View Details
                </nuxt-link>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, computed } from 'vue';
definePageMeta({
  layout: 'default'
});

const config = useRuntimeConfig();
const api = config.public.API_URL;
const { data: clients, error, refresh } = await useFetch(`${api}/clients`);

const selectedUsername = ref('');

// Computed property for unique usernames
const uniqueUsernames = computed(() => {
  const usernames = new Set();
  clients.value.forEach(client => {
    usernames.add(client.username);
  });
  return Array.from(usernames);
});

// Computed property for filtered clients
const filteredClients = computed(() => {
  if (!selectedUsername.value) {
    return clients.value; // Return all clients if no filter is applied
  }
  return clients.value.filter(client =>
    client.username === selectedUsername.value
  );
});
</script>

<style scoped>
.card {
  margin-top: 20px;
}
</style>
