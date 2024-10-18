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
    <div class="container mt-5">
      <!-- Botão para voltar à lista de ordens -->
      <div class="mb-4">
        <nuxt-link to="/manager" class="btn btn-secondary">Back to Orders List</nuxt-link>
      </div>
  
      <div v-if="order" class="card shadow-lg">
        <div class="card-header bg-secondary text-white">
          <h2 class="mb-0">Details of Order - {{ order.username }} ({{ order.code }})</h2>
        </div>
        <div class="card-body">
          <table class="table table-striped table-hover">
            <thead class="thead-dark">
              <tr>
                <th>Code</th>
                <th>Purchase Date</th>
                <th>Delivery Date</th>
                <th>Username</th>
              </tr>
            </thead>
            <tbody>
              <tr>
                <td>{{ order.code }}</td>
                <td>{{ order.purchaseDate }}</td>
                <td>{{ order.deliveryDate }}</td>
                <td>{{ order.username }}</td>
              </tr>
            </tbody>
          </table>
        </div>
        <div class="card-footer text-center">
          <nuxt-link :to="`/manager/${order.code}`" class="btn btn-dark m-2">View Order Details</nuxt-link>
          <button @click.prevent="refresh" class="btn btn-info ml-2">Refresh Data</button>
        </div>
      </div>
  
      <div v-else>
        <h2 class="text-danger mt-4">Error loading order details.</h2>
        <p>{{ messages }}</p>
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
  const route = useRoute()
  const code = route.params.code
  const config = useRuntimeConfig()
  const api = config.public.API_URL
  const { data: order, error: codeErr } = await useFetch(`${api}/orders/${code}`)
  const messages = ref([])
  if (codeErr.value) messages.value.push(codeErr.value)
  </script>
  