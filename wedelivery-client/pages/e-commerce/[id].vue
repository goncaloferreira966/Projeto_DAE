<template>
  <!-- Navbar -->


  <div class="container mt-5">
    <div v-if="messages.length" class="alert alert-danger mt-4">
      <ul>
        <li v-for="(msg, index) in messages" :key="index">{{ msg }}</li>
      </ul>
    </div>

    <div v-else class="card shadow-lg">
      <div class="card-header bg-secondary text-white d-flex justify-content-between align-items-center">
        <nuxt-link to="/e-commerce" class="btn btn-light text-secondary">Back</nuxt-link>
        <h2 class="mb-0">{{ product.name }}</h2>
      </div>
      <div class="card-body d-flex">
        <div class="col-md-6">
          <h5 class="text-center">Product Details</h5>
          <table class="table table-bordered">
            <tbody>
              <tr>
                <th>ID</th>
                <td>{{ product.id }}</td>
              </tr>
              <tr>
                <th>Description</th>
                <td>{{ product.description }}</td>
              </tr>
              <tr>
                <th>Price</th>
                <td>€{{ product.price.toFixed(2) }}</td>
              </tr>
              <tr>
                <th>Available</th>
                <td>{{ product.available ? 'Yes' : 'No' }}</td>
              </tr>
              <tr>
                <th>Warehouse</th>
                <td>{{ product.warehouseName }}</td>
              </tr>
              <tr>
                <th>Quantity</th>
                <td>{{ product.quantity }}</td>
              </tr>
              <tr>
                <th>Sensor</th>
                <td>{{ product.haveSensor ? 'Yes' : 'No' }}</td>
              </tr>
            </tbody>
          </table>
        </div>
        <div class="col-md-6 text-center">
          <h5>Product Image</h5>
          <img :src="`${config.public.URL}/images/${product.image}`" alt="Product Image" class="img-fluid" />
        </div>
      </div>
      <div class="card-footer text-center">
        <button @click.prevent="refresh" class="btn btn-info ml-2"><i class="bi bi-arrow-clockwise"></i> Refresh Data</button>
      </div>
    </div>
  </div>


</template>

<script setup>
import { ref } from 'vue'
import { useRoute } from 'vue-router'
import { useRuntimeConfig } from '#imports'
definePageMeta({
  layout: 'default'
});

const route = useRoute()
const config = useRuntimeConfig()
const apiUrl = `${config.public.API_URL}/products/${route.params.id}`

const messages = ref([]) // Armazenar mensagens de erro
const product = ref({}) // Armazenar detalhes do produto

// Função assíncrona para carregar os dados do produto
const loadProduct = async () => {
  try {
    const response = await fetch(apiUrl)
    if (!response.ok) {
      throw new Error('Failed to fetch product')
    }
    product.value = await response.json()
  } catch (error) {
    messages.value.push(error.message)
  }
}

// Chama a função para carregar os dados ao montar o componente
await loadProduct()
</script>

<style scoped>
.card {
  margin-top: 20px;
}

.img-fluid {
  max-width: 100%;
  height: auto;
}
</style>
