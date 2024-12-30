<template>
  <div class="container mt-5">
    <div v-if="client" class="card shadow-lg">
      <div class="card-header bg-secondary text-white d-flex justify-content-between align-items-center">
        <nuxt-link to="/clients" class="btn btn-light text-blue">Back</nuxt-link>
        <h2 class="mb-0">Details of Client - {{ client.name }} ({{ client.username }})</h2>
      </div>
      <div class="card-body">
        <!-- Informações do Cliente -->
        <div class="container" style="display: flex;">
          <div class="col-md-4">
            <div class="card">
              <img src="/img_avatar.png" alt="Avatar" class="card-img-top" style="width: 100%">
              <div class="card-body">
                <h3 class="card-title"> <strong>{{ client.name }}</strong></h3>

                <p class="card-text"><strong>Email:</strong> {{ client.email }}
                  <br /><strong>Address:</strong> {{ client.address }}
                  <br /><strong>Postal Code:</strong> {{ client.postalCode }}
                  <br /><strong>City:</strong> {{ client.city }}
                  <br /><strong>Country:</strong> {{ client.country }}
                </p>
              </div>
            </div>
          </div>
          <!-- Informações das Ordens em pares -->
          <div class="col-md-8" style="padding-left: 2%;">
            <div class="card">
              <div class="card-header bg-secondary">
                <ul class="nav nav-tabs card-header-tabs">
                  <li class="nav-item">
                    <a class="nav-link active " aria-current="true" href="#">All</a>
                  </li>
                  <li class="nav-item">
                    <a class="nav-link text-white" href="#">Completed</a>
                  </li>
                  <li class="nav-item ">
                    <a class="nav-link text-white " href="#" tabindex="-1" aria-disabled="true">In Distribution</a>
                  </li>
                  <li class="nav-item ">
                    <a class="nav-link text-white " href="#" tabindex="-1" aria-disabled="true">Pending</a>
                  </li>
                  <li class="nav-item ">
                    <a class="nav-link text-white " href="#" tabindex="-1" aria-disabled="true">Shipped</a>
                  </li>
                </ul>
              </div>
              <div class="row" style="padding: 1%;">
                <div v-for="(order) in client.orders" :key="order.code" class="col-md-6 mb-3">
                  <div class="card border-secondary">
                    <div class="card-header bg-secondary text-white">
                      <h6><strong>Order Code:</strong> {{ order.code }}</h6>
                    </div>
                    <div class="card-body">
                      <p><strong>Purchase Date:</strong> {{ order.purchaseDate }}</p>
                      <strong>State:</strong>
                      <span :class="getStateClass(order.state)" class="badge"
                        style="display: block; text-align: center;">
                        {{ order.state }}
                      </span>
                      <br>
                      <!-- <p><strong>Operator:</strong> {{ order.usernameOperator }}</p> -->
                      <!-- <nuxt-link :to="`/clients/${client.username}/${order.code}`"
                        class="btn btn-dark btn-block btn-sm">
                        <i class="bi bi-eye-fill"></i> View Details
                      </nuxt-link> -->
                     <!-- <nuxt-link :to="`/manager/${order.code}`" class="btn btn-dark btn-block"><i class="bi bi-eye-fill"></i> View Details</nuxt-link>-->
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>

      <div class="card-footer text-center">
        <button @click.prevent="refresh" class="btn btn-info ml-2">
          <i class="bi bi-arrow-clockwise"></i> Refresh Data
        </button>
      </div>
    </div>

    <div v-else>
      <h2 class="text-danger mt-4">Error loading client details.</h2>
      <p>{{ messages }}</p>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useRoute } from 'vue-router'
import { useRuntimeConfig } from '#imports'

definePageMeta({
  layout: 'default'
});

const route = useRoute();
const username = route.params.username;
const config = useRuntimeConfig();
const api = config.public.API_URL;

const messages = ref([]);  // Armazenar mensagens de erro
const client = ref(null);  // Armazenar os detalhes do cliente

// Função assíncrona para carregar os dados do cliente
const loadClientData = async () => {
  try {
    // Usar $fetch para obter os dados do cliente
    const token = localStorage.getItem('AccessToken');
    const response = await $fetch(`${api}/clients/${username}`, {
      headers: {
        Authorization: `Bearer ${token}`
      }
    });

    // Verifica se a resposta inclui orders
    if (!response.orders) {
      throw new Error("Orders data not found in response");
    }

    client.value = response;  // Armazena o cliente e suas ordens
  } catch (err) {
    messages.value.push("Unexpected error: " + err.message);
    console.error(err); // Logar erro no console para depuração
  }
};

const formatDate = (dateString) => {
  const options = { year: 'numeric', month: '2-digit', day: '2-digit' };
  return new Date(dateString).toLocaleDateString('en-US', options);
}

// Chama a função para carregar os dados ao montar o componente

onMounted(() => {
  loadClientData();
});

// Função para obter a classe baseada no estado
const getStateClass = (state) => {
  switch (state) {
    case 'In distribution':
      return 'badge bg-light-blue'; // Azul claro para "In distribution"
    case 'Pending':
      return 'badge bg-warning'; // Cor de aviso para "Pending"
    case 'Shipped':
      return 'badge bg-blue'; // Cor para "Shipped"
    case 'Delivered':
      return 'badge bg-success'; // Cor de sucesso para "Delivered"
    default:
      return 'badge bg-secondary'; // Default
  }
};
</script>

<style scoped>
.card {
  margin-top: 20px;
}

/* Cores das etiquetas */
.bg-light-blue {
  background-color: #ADD8E6;
  /* Azul claro */
}

.bg-warning {
  background-color: #FFC107;
  /* Amarelo (Warning) */
}

.bg-blue {
  background-color: #007BFF;
  /* Azul */
}

.bg-success {
  background-color: #28A745;
  /* Verde (Sucesso) */
}
</style>
