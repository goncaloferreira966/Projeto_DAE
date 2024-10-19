<template>
    <!-- Navbar -->
    <nav class="navbar navbar-expand-lg navbar-light bg-light">
      <div class="container-fluid">
        <a class="navbar-brand" href="#">WeDelivery</a>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
          <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarNav">
          <ul class="navbar-nav">
            <li class="nav-item">
              <nuxt-link class="nav-link" to="/">Home</nuxt-link>
            </li>
            <li class="nav-item">
              <nuxt-link class="nav-link" to="/manager">Orders</nuxt-link>
            </li>
          </ul>
        </div>
      </div>
    </nav>
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
          <h2>WeDelivery - Orders</h2>
          <button @click.prevent="refresh" class="btn btn-info">Refresh Data</button>
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
                <nuxt-link :to="`/manager/${order.code}`" class="btn btn-dark btn-block btn-sm">View Details</nuxt-link>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
    <footer class="mt-5 bg-light text-center py-4">
      <p class="mb-0">© {{ new Date().getFullYear() }} WeDelivery. All rights reserved.</p>
      <p>
        <nuxt-link  class="text-secondary">Privacy Policy</nuxt-link> |
        <nuxt-link class="text-secondary">Contact Us</nuxt-link>
      </p>
    </footer>
  </div>
</template>

<script setup>
import { ref, computed } from 'vue';

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
