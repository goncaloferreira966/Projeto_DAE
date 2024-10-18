<template>
  <!-- Navbar -->
  <nav class="navbar navbar-expand-lg navbar-light bg-light">
    <div class="container-fluid">
      <a  class="navbar-brand" href="#">WeDelivery</a>
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
  
  <div>
    <footer class="mt-5 bg-light text-center py-4">
      <p class="mb-0">© {{ new Date().getFullYear() }} WeDelivery. All rights reserved.</p>
      <p>
        <nuxt-link class="text-secondary">Privacy Policy</nuxt-link> |
        <nuxt-link class="text-secondary">Contact Us</nuxt-link>
      </p>
    </footer>
  </div>
</template>