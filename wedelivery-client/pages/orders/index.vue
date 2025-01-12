<template>
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
          <button @click.prevent="refresh" class="btn btn-info"><i class="bi bi-arrow-clockwise"></i> Refresh
            Data</button>
        </div>

        <!-- Dropdown para filtragem por username -->
        <div v-if="role === 'Operator' || role === 'Manager'" class="col-md-2 mt-4">
          <select v-model="selectedUsername" class="form-select">
            <option value="">Select a Client</option>
            <option v-for="username in uniqueUsernames" :key="username" :value="username">
              {{ username }}
            </option>
          </select>
        </div>

        <!-- Cards com informação adicional -->
        <div class="row mt-5">
          <div v-for="(order, index) in filteredOrders" :key="index" class="col-lg-4 col-md-6 mb-4">
            <div class="card border-secondary">
              <div class="card-header bg-secondary text-white">
                Order #{{ order.code }}
              </div>
              <div class="card-body" :id="`${index}`">
                <p><strong>Username:</strong> {{ order.username }}</p>
                <p><strong>Purchase Date:</strong> {{ order.purchaseDate }}</p>
                <p><strong>State:</strong>
                  <span :class="getStateClass(order.state)" class="badge" style="display: block; text-align: center;">
                    {{ order.state }}
                  </span>
                </p>
                <div class="row">
                  <div class="col-md-6">

                    <nuxt-link :to="`/orders/${order.code}`" style="width: 100%;" class="btn btn-dark btn-block"><i
                        class="bi bi-eye-fill"></i>
                      View Details</nuxt-link>
                  </div>
                  <div class="col-md-6">

                    <button v-if="order.state === 'Pending'" @click="approveOrder(order)" style="width: 100%;"
                      class="btn btn-success btn-block"><i class="bi bi-eye-fill"></i> Approve Order</button>
                  </div>

                </div>

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
const token = localStorage.getItem('AccessToken');
const role = localStorage.getItem('Role');
const username = localStorage.getItem('Username');

//Verificação da role e chamada do endpoint correto
const endpoint = role === 'Operator' || role === 'Manager'
  ? `${api}/orders`
  : `${api}/clients/${username}/orders`;

// Chamada do endpoint
const { data: orders, error, refresh } = await useFetch(endpoint, {
  headers: {
    Authorization: `Bearer ${token}`
  }
});

const id_order = ref(0);

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

// Function to get class based on state
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
    case 'Compromised':
      return 'badge bg-danger'; // Cor de sucesso para "Compromised"
    default:
      return 'badge bg-secondary'; // Default
  }
};

const approveOrder = async (order) => {
  // Faz uma requisição POST (ou PUT, dependendo da API) para guardar o novo valor
  const { data, error } = await useFetch(`${api}/orders/${order.code}`, {
      method: 'PATCH',
      headers: {
        Authorization: `Bearer ${token}`,
        'Content-Type': 'application/json'
      },
      body: {
        state: "Shipped"
      }
    });
    order.state = "Shipped";
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
