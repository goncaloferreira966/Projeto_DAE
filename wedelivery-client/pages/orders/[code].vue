<template>
  <!-- Navbar -->
  <div class="container mt-5">
    <div v-if="order" class="card shadow-lg">
      <div class="card-header bg-secondary text-white d-flex justify-content-between align-items-center">
        <button @click="goBack" class="btn btn-light text-secondary">Back</button>
        <h2 class="mb-0">Details of Order - {{ order.username }} ({{ order.code }})</h2>
      </div>
      <div class="card-body">
        <table class="table table-striped table-hover">
          <thead class="thead-dark">
            <tr>
              <th>Code</th>
              <th>Purchase Date</th>
              <th>State</th>
              <th>Delivery Date</th>
            </tr>
          </thead>
          <tbody>
            <tr>
              <td>{{ order.code }}</td>
              <td>{{ formatDate(order.purchaseDate) }}</td>
              <td>{{ order.state }}</td>
              <td>{{ formatDate(order.deliveryDate) }}</td>
            </tr>
          </tbody>
        </table>
        <div class="container" style="display: flex;">

          <div v-if="client" style="display: flex;" class="col-md-3">
            <div class="card">
              <h5>Client Information</h5>
              <img src="/img_avatar.png" alt="Avatar" style="width: 100%">
              <div class="container">
                <div v-if="client">
                  <h4>{{ client.name }}</h4>
                  <p>{{ client.email }}</p>
                  <p>{{ client.address }}</p>
                  <p>{{ client.postalCode }}</p>
                  <p>{{ client.city }}</p>
                  <p>{{ client.country }}</p>
                </div>
                <div v-else>
                  <p>Loading client information...</p>
                </div>
              </div>
            </div>
          </div>

          <div v-for="(volume, index) in order.volumes" :key="index" class="col-md-3" style="padding-left: 1%;">
            <div class="card">
              <div class="card-body">
                <h4>Volume ID: {{ volume.id }}</h4>
                <p><strong>State:</strong> {{ volume.state }}</p>
                <p><strong>Creation Date:</strong> {{ volume.creationDate }}</p>
                <button @click="showVolumeDetails(volume)" class="btn btn-dark btn-block"><i class="bi bi-eye-fill"></i> View Details</button>
              </div>
            </div>
          </div>
        </div>

        <!-- Importando o componente do modal -->
        

        <VolumeDetailsModal :selectedVolume="selectedVolume" @close="closeModal" />

      </div>
      <div class="card-footer text-center">
        <button @click.prevent="refresh" class="btn btn-info ml-2"><i class="bi bi-arrow-clockwise"></i> Refresh Data</button>
      </div>
    </div>

    <div v-else>
      <h2 class="text-danger mt-4">Error loading order details.</h2>
      <p>{{ messages }}</p>
    </div>
  </div>

</template>

<script setup>
import { ref } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import VolumeDetailsModal from '../components/VolumeDetailsModal.vue' // Importe o novo componente

const route = useRoute()
const router = useRouter() 
const code = route.params.code
const config = useRuntimeConfig()
const api = config.public.API_URL
const goBack = () => {
  router.back()
}

const messages = ref([]) // Armazenar mensagens de erro
const order = ref(null)  // Armazenar os detalhes do pedido
const client = ref(null) // Armazenar os detalhes do cliente

// Função assíncrona para carregar dados do pedido e cliente
const loadOrderAndClient = async () => {
  try {
    const token = localStorage.getItem('AccessToken');
    const { data: orderData, error: orderError } = await useFetch(`${api}/orders/${code}`
      , {
        headers: {
          Authorization: `Bearer ${token}`
        }
      });
    if (orderError.value) {
      messages.value.push(orderError.value)
      return
    }
    order.value = orderData.value // Armazena o pedido

    const { data: clientData, error: clientError } = await useFetch(`${api}/clients/${order.value.username}`
    , {
        headers: {
          Authorization: `Bearer ${token}`
        }
      });
    if (clientError.value) {
      messages.value.push(clientError.value)
      return
    }
    client.value = clientData.value // Armazenar cliente
  } catch (err) {
    messages.value.push("Unexpected error: " + err.message)
  }
}

const formatDate = (timestamp) => {
  if (!timestamp) return "N/A"; 
  return new Date(timestamp).toLocaleString("pt-PT"); 
};

const selectedVolume = ref(null); // Volume selecionado para exibir no modal

const loadVolumeDetails = async (orderCode, volumeId) => {
  try {
    const token = localStorage.getItem('AccessToken');
    const { data: volumeData, error: volumeError } = await useFetch(`${api}/orders/${orderCode}/volume/${volumeId}`,
      {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      }
    );
    if (volumeError.value) {
      messages.value.push(`Error fetching volume details: ${volumeError.value.message}`);
      return null;
    }
    return volumeData.value;
  } catch (err) {
    messages.value.push(`Unexpected error: ${err.message}`);
    return null;
  }
};

const showVolumeDetails = async (volume) => {
  console.log("Clicou no botão para ver detalhes do volume", volume); // Verifique se isso aparece no console
  selectedVolume.value = await loadVolumeDetails(order.value.code, volume.id);
  console.log("Detalhes do volume carregados:", selectedVolume.value); // Verifique se os dados estão corretos
};
const closeModal = () => {
  selectedVolume.value = null;
};

// Chama a função para carregar os dados
await loadOrderAndClient()
</script>
