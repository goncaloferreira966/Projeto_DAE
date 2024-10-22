<!-- layouts/default.vue -->
<template>
  <div>
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
</style>
