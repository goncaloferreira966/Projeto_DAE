<!-- layouts/default.vue -->
<template>
  <div>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons/font/bootstrap-icons.css" rel="stylesheet">
    <Navbar />
    <main>
      <slot></slot> <!-- O conteúdo da página será renderizado aqui -->
    </main>
    <Footer></Footer>
  </div>
</template>

<script setup>
import Navbar from '../pages/components/Navbar.vue'; // Ajuste se necessário
import Footer from '../pages/components/Footer.vue'; // Ajuste se necessário
</script>

<style scoped>
main {
  padding: 20px; /* Espaçamento para o conteúdo */
  min-height: calc(100vh - 100px); /* Ajuste a altura mínima conforme necessário */
}
.video-container {
  display: flex;
  justify-content: center;
  align-items: center;
}

.responsive-iframe {
  width: 90%;
  max-width: 1200px;
  height: auto;
  aspect-ratio: 16 / 9;
  border-radius: 10px;
  box-shadow: 0 4px 10px rgba(0, 0, 0, 0.1);
}
</style>
