<template>
  <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons/font/bootstrap-icons.css" rel="stylesheet">
  <div class="container">
    <div v-if="error" class="alert alert-danger mt-4">Error: {{ error.message }}</div>
    
    <div v-else class="col-md-12 mt-5">
      <div v-if="!orders" class="d-flex justify-content-center">
        <!-- Spinner enquanto os dados são carregados -->
        <div class="spinner-border text-primary" role="status">
          <span class="sr-only">Loading...</span>
        </div>
      </div>
      <div v-else>
        <!-- Cabeçalho da página -->
        <div class="d-flex justify-content-between align-items-center mb-4">
          <h2><i class="bi bi-box-seam"></i> WeDelivery - Orders</h2>
          <button @click.prevent="refresh" class="btn btn-info"><i class="bi bi-arrow-clockwise"></i> Refresh Data</button>
        </div>

        <!-- Dropdown para filtragem por username -->
        <div class="col-md-2 mt-4">
          <select v-model="selectedUsername" class="form-select">
            <option value="">Select a Client</option>
            <option v-for="username in uniqueUsernames" :key="username" :value="username">
              {{ username }}
            </option>
          </select>
        </div>
        
        <!-- Cards com informação adicional -->
        <div class="row mt-5">
          <div class="col-lg-4 col-md-6 mb-4" v-for="order in filteredOrders" :key="order.code">
            <div class="card border-secondary">
              <div class="card-header bg-secondary text-white">
                Order #{{ order.code }}  
              </div>
              <div class="card-body">
                <p><strong>Username:</strong> {{ order.username }}</p>
                <p><strong>Purchase Date:</strong> {{ order.purchaseDate }}</p>
                <p><strong>State:</strong> {{ order.state }}</p>
                <nuxt-link :to="`/manager/${order.code}`" class="btn btn-dark btn-block"><i class="bi bi-eye-fill"></i> View Details</nuxt-link>
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
const { data: orders, error, refresh } = await useFetch(`${api}/orders`);

const selectedUsername = ref('');

// Computed property for unique usernames
const uniqueUsernames = computed(() => {
  const usernames = new Set();
  orders.value.forEach(order => {
    usernames.add(order.username);
  });
  return Array.from(usernames);
});

// Computed property for filtered orders
const filteredOrders = computed(() => {
  if (!selectedUsername.value) {
    return orders.value; // Return all orders if no filter is applied
  }
  return orders.value.filter(order =>
    order.username === selectedUsername.value
  );
});
</script>

<style scoped>
.card {
  margin-top: 20px;
}
</style>
