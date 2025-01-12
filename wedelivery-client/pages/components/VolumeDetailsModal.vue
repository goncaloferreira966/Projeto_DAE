<template>
  <div v-if="selectedVolume" class="modal fade show d-block" tabindex="-1" role="dialog">
    <div class="modal-dialog custom-modal-width">
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

                  <p v-if="sensor.type == 'Temperature'" class="card-text"><strong>Type:</strong> <i class="bi bi-thermometer-half"></i> {{ sensor.type }} (ºC)</p>
                  <p v-else-if="sensor.type == 'Humidity'" class="card-text"><strong>Type:</strong> <i class="bi bi-droplet-fill"></i> {{ sensor.type }} (%)</p>
                  <p v-else-if="sensor.type == 'GPS'" class="card-text"><strong>Type:</strong> <i class="bi bi-geo-alt-fill"></i> {{ sensor.type }} (°)</p>
                  <p v-else-if="sensor.type == 'Accelerometer'" class="card-text"><strong>Type:</strong> <i class="bi bi-speedometer"></i> {{ sensor.type }} (m/s²)</p>

                  <p class="card-text"><strong>Current Value:</strong> {{ sensor.currentValue }}</p>
                  <buton @click="toggleHistory(sensor.id)" style="width: 100%;" class="btn btn-secondary"><i class="bi bi-eye-fill"></i> History </buton>
                 <!--Div que mostra o historico do sensor-->
                  <div v-show="showHistory[sensor.id]" class="history-div mt-3">
                    <p v-for="(sensorValue, index) in sensor.sensorValues" class="card-text"><strong>{{ formatDate(sensorValue.date) }}:</strong> {{ sensorValue.value }}</p>
                  </div>
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
  data() {
    return {
      showHistory: {},
    };
  },
  methods: {
    formatDate(timestamp) {
      if (!timestamp) return "N/A";
      return new Date(timestamp).toLocaleString("pt-PT");
    },
    //Mostra e oculta o histórico
    toggleHistory(sensorId) {
      this.showHistory[sensorId] = !this.showHistory[sensorId];
    },
  },
};

</script>

<style scoped>
.custom-modal-width {
  max-width: 45%; /* Ajuste o valor conforme necessário */
}
</style>