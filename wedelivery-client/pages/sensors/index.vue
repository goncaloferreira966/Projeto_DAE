<template>
  <div class="container">
    <div v-if="error" class="alert alert-danger mt-4">Error: {{ error.message }}</div>

    <div v-else class="col-md-12 mt-5">
      <div v-if="!sensors" class="d-flex justify-content-center">
        <!-- Spinner enquanto os dados são carregados -->
        <div class="spinner-border text-primary" role="status">
          <span class="sr-only">Loading...</span>
        </div>
      </div>
      <div v-else>
        <!-- Cabeçalho da página -->
        <div class="d-flex justify-content-between align-items-center mb-4">
          <h2><i class="bi bi-box-seam"></i> WeDelivery - Sensors</h2>
          <button @click.prevent="refresh" class="btn btn-info"><i class="bi bi-arrow-clockwise"></i> Refresh
            Data</button>
        </div>

        <div class="container" style="text-align: center;">
          <table class="table table-bordered table-striped rounded">
            <thead class="thead-dark">
              <tr>
                <th>ID</th>
                <th>Tipo</th>
                <th>Valor Atual</th>
                <th><i class="bi bi-pencil-fill"></i></th>
              </tr>
            </thead>
            <tbody>
              <tr v-for="sensor in sensors" :key="sensor.id">
                <td>{{ sensor.id }}</td>

                <td v-if="sensor.type == 'Temperature'"><i class="bi bi-thermometer-half"></i> {{ sensor.type }}</td>
                <td v-else-if="sensor.type == 'Humidity'"><i class="bi bi-droplet-fill"></i> {{ sensor.type }}</td>
                <td v-else-if="sensor.type == 'GPS'"><i class="bi bi-geo-alt-fill"></i> {{ sensor.type }}</td>
                <td v-else-if="sensor.type == 'Accelerometer'"><i class="bi bi-speedometer"></i> {{ sensor.type }}</td>

                <td v-if="editedSensorId !== sensor.id">{{ sensor.currentValue }}</td>
                <td v-else>
                  <input v-if="sensor.type === 'Temperature'" v-model="editedValue" min="-20" max="50" type="number" class="form-control form-control-sm" />
                  <input v-if="sensor.type === 'Humidity'" v-model="editedValue" min="0" max="100" type="number" class="form-control form-control-sm" />
                  <input v-if="sensor.type === 'Accelerometer'" v-model="editedValue" min="0" max="15" type="number" class="form-control form-control-sm" />
                  <input v-else-if="sensor.type === 'GPS'" v-model="editedValue" type="text" class="form-control form-control-sm" />
                </td>
                <td>
                  <button v-if="editedSensorId !== sensor.id" @click="startEditing(sensor)"
                    class="btn btn-sm btn-primary">
                    <i class="bi bi-pencil-fill"></i> Editar
                  </button>
                  <div v-else>
                    <button @click="saveValue(sensor)" class="btn btn-sm btn-success me-2">
                      <i class="bi bi-floppy-fill"></i> Guardar
                    </button>
                    <button @click="cancelEditing" class="btn btn-sm btn-danger">
                      <i class="bi bi-x-circle-fill"></i> Cancelar
                    </button>
                  </div>
                </td>
              </tr>
            </tbody>
          </table>
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

// Variáveis para edição
const editedSensorId = ref(null);
const editedValue = ref(null);


const config = useRuntimeConfig();
const api = config.public.API_URL;
const token = localStorage.getItem('AccessToken');

//Verificação da role e chamada do endpoint correto
const endpoint = `${api}/sensors`;

// Chamada do endpoint
const { data: sensors, error, refresh } = await useFetch(endpoint, {
  headers: {
    Authorization: `Bearer ${token}`
  }
});

// Inicia o modo de edição para um sensor
const startEditing = (sensor) => {
  editedSensorId.value = sensor.id;
  editedValue.value = sensor.currentValue;
};

// Cancela a edição
const cancelEditing = () => {
  editedSensorId.value = null;
  editedValue.value = null;
};

// Guarda o novo valor atual do sensor
const saveValue = async (sensor) => {
  try {
    // Faz uma requisição POST (ou PUT, dependendo da API) para salvar o novo valor
    /*await axios.post(`http://localhost/api/sensors/${sensor.id}`, {
      currentValue: editedValue.value,
    });*/

    // Atualiza o valor na tabela
    sensor.currentValue = editedValue.value;

    // Sai do modo de edição
    editedSensorId.value = null;
    editedValue.value = null;
  } catch (error) {
    console.error('Erro ao salvar o valor:', error);
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


.table {
  border-radius: 10px;
  /* Ajuste o valor como preferir */
  overflow: hidden;
  /* Necessário para garantir que os cantos arredondados sejam aplicados corretamente */
}
</style>
