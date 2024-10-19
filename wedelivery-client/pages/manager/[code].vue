<template>
  <!-- Navbar -->
  <nav class="navbar navbar-expand-lg navbar-light bg-light">
    <div class="container-fluid">
      <a class="navbar-brand" href="#">WeDelivery</a>
      <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav"
        aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
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
            </tr>
          </thead>
          <tbody>
            <tr>
              <td>{{ order.code }}</td>
              <td>{{ order.purchaseDate }}</td>
            </tr>
          </tbody>
        </table>
        <div class="container" style="display: flex;">

          <div style="display: flex;" class="col-md-2">
            <div class="card">
              <h5>Client Information</h5>
              <img src="/img_avatar.png" alt="Avatar" style="width: 100%">
              <div class="container">
                <h4>{{ client.name }}</h4>
                <p>{{ client.email }}</p>
                <p>{{ client.address }}</p>
                <p>{{ client.postalCode }}</p>
                <p>{{ client.city }}</p>
                <p>{{ client.country }}</p>
              </div>
            </div>
          </div>
          <div style="display: flex;" class="col-md-1">
            
          </div>
          <div style="display: flex;" class="col-md-9">
            <div class="card">
              <h5>Order Information and Content</h5>
              
            </div>
          </div>
        </div>
        
      </div>
      <div class="card-footer text-center">
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
        <nuxt-link class="text-secondary">Privacy Policy</nuxt-link> |
        <nuxt-link class="text-secondary">Contact Us</nuxt-link>
      </p>
    </footer>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import { useRoute } from 'vue-router'
import { useRuntimeConfig, useFetch } from '#imports'

const route = useRoute()
const code = route.params.code
const config = useRuntimeConfig()
const api = config.public.API_URL

const messages = ref([]) // Armazenar mensagens de erro
const order = ref(null)  // Armazenar os detalhes do pedido
const client = ref(null) // Armazenar os detalhes do cliente

// Função assíncrona para carregar dados do pedido e cliente
const loadOrderAndClient = async () => {
  try {
    // Primeiro, carrega os dados do pedido
    const { data: orderData, error: orderError } = await useFetch(`${api}/orders/${code}`)
    if (orderError.value) {
      messages.value.push(orderError.value)
      return
    }

    order.value = orderData.value // Armazena o pedido

    // Agora, carrega os dados do cliente com base no 'username' do pedido
    const { data: clientData, error: clientError } = await useFetch(`${api}/clients/${order.value.username}`)
    if (clientError.value) {
      messages.value.push(clientError.value)
      return
    }

    client.value = clientData.value // Armazena o cliente

  } catch (err) {
    messages.value.push("Unexpected error: " + err.message)
  }
}

// Chama a função para carregar os dados
await loadOrderAndClient()
</script>
