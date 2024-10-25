<template>
  <div>
    <!-- O conteúdo específico da página vai aqui -->
    <!-- <div class="video-container d-flex justify-content-center my-5">
      <iframe class="responsive-iframe" src="https://www.youtube.com/embed/D9bwnJclcas?si=39ybdtqbnMOkh3Mi"
        title="YouTube video player" frameborder="0"
        allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share"
        referrerpolicy="strict-origin-when-cross-origin" allowfullscreen>
      </iframe>
    </div> -->
  </div>
</template>

<script setup>
definePageMeta({
  layout: 'default' // Garante que o layout padrão seja usado
});
</script>

<style scoped>
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
