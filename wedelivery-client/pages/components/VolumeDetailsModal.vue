<template>
  <div v-if="selectedVolume" class="modal fade show d-block" tabindex="-1" role="dialog">
    <div class="modal-dialog">
      <div class="modal-content">
        <div class="modal-header">
          <h5 class="modal-title">Volume Details - {{ selectedVolume.id }}</h5>
          <button type="button" class="btn-close" @click="$emit('close')"></button>
        </div>
        <div class="modal-body">
          <p><strong>State:</strong> {{ selectedVolume.state }}</p>
          <p><strong>Creation Date:</strong> {{ formatDate(selectedVolume.creationDate) }}</p>

          <!-- Sensores e Produtos -->
          <div class="row">
            <!-- Sensores -->
            <div class="col-md-6">
              <h4>Sensors</h4>
              <div v-for="(sensor, index) in selectedVolume.sensors" :key="index" class="card mb-3">
                <div class="card-body">
                  <h5 class="card-title">Sensor ID: {{ sensor.id }}</h5>
                  <p class="card-text"><strong>Type:</strong> {{ sensor.type }}</p>
                  <p class="card-text"><strong>Current Value:</strong> {{ sensor.currentValue }}</p>
                </div>
              </div>
            </div>

            <!-- Produtos -->
            <div class="col-md-6">
              <h4>Products</h4>
              <div v-for="(product, index) in selectedVolume.products" :key="index" class="card mb-3">
                <div class="card-body">
                  <h5 class="card-title">Product ID: {{ product.id }}</h5>
                  <p class="card-text"><strong>Name:</strong> {{ product.name }}</p>
                  <p class="card-text"><strong>Description:</strong> {{ product.description }}</p>
                  <p class="card-text"><strong>Price:</strong> €{{ product.price.toFixed(2) }}</p>
                 <!-- <p class="card-text"><strong>Quantity:</strong> {{ product.quantity }}</p>-->
                </div>
              </div>
            </div>
          </div>
        </div>
        <div class="modal-footer">
          <button type="button" class="btn btn-secondary" @click="$emit('close')">Close</button>
        </div>
      </div>
    </div>
  </div>
  <div v-if="selectedVolume" class="modal-backdrop fade show"></div>
</template>

<script>
export default {
  props: {
    selectedVolume: {
      type: Object,
      required: true,
    },
  },
  methods: {
    formatDate(timestamp) {
      if (!timestamp) return "N/A";
      return new Date(timestamp).toLocaleString("pt-PT");
    },
  },
};
</script>

<style>
/* Personalize o estilo aqui, se necessário */
</style>
