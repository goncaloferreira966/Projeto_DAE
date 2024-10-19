<template>
  <!-- Navbar -->
  <nav class="navbar navbar-expand-lg navbar-light bg-light">
    <div class="container-fluid">
      <a class="navbar-brand" href="#">WeDelivery</a>
      <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav"
        aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
      </button>
      <div class="collapse navbar-collapse" id="navbarNav">
        <ul class="navbar-nav">
          <li class="nav-item">
            <nuxt-link class="nav-link" to="/">Home</nuxt-link>
          </li>
          <li class="nav-item">
            <nuxt-link class="nav-link" to="/manager">Orders</nuxt-link>
          </li>
        </ul>
      </div>
    </div>
  </nav>

  <!-- Video Section -->
  <div class="video-container d-flex justify-content-center my-5">
    <iframe class="responsive-iframe" src="https://www.youtube.com/embed/D9bwnJclcas?si=39ybdtqbnMOkh3Mi" 
      title="YouTube video player" frameborder="0" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share" 
      referrerpolicy="strict-origin-when-cross-origin" allowfullscreen></iframe>
  </div>

  <!-- Footer -->
  <footer class="mt-5 bg-light text-center py-4">
    <p class="mb-0">© {{ new Date().getFullYear() }} WeDelivery. All rights reserved.</p>
    <p>
      <nuxt-link class="text-secondary">Privacy Policy</nuxt-link> |
      <nuxt-link class="text-secondary">Contact Us</nuxt-link>
    </p>
  </footer>
</template>

<style scoped>
.video-container {
  /* Centralizar o vídeo */
  display: flex;
  justify-content: center;
  align-items: center;
}

/* Tornar o iframe responsivo com largura máxima e mínima */
.responsive-iframe {
  width: 80%;           /* Ocupa 80% da largura da tela */
  max-width: 900px;      /* Máximo de 900px para telas grandes */
  height: auto;          /* Mantém a proporção original */
  aspect-ratio: 16 / 9;  /* Mantém proporção de vídeo widescreen */
  border-radius: 10px;   /* Bordas arredondadas */
  box-shadow: 0 4px 10px rgba(0, 0, 0, 0.1); /* Sombra sutil */
}
</style>
