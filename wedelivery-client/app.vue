<!-- app.vue -->
<template>
  <div>
    <NuxtLayout>
      <NuxtPage />
    </NuxtLayout>
  </div>
</template>

<script setup>
import 'bootstrap/dist/css/bootstrap.min.css'; // CSS pode ser carregado em qualquer lado

// Importar o JavaScript do Bootstrap apenas no lado do cliente
import { onMounted } from 'vue';

onMounted(() => {
  import('bootstrap/dist/js/bootstrap.bundle.min.js');
});
</script>

<style scoped>
/* Global styles can go here */
</style>
