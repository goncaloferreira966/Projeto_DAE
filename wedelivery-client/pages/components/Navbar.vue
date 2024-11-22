<template>
    <nav class="navbar navbar-expand-lg navbar-light bg-light">
        <div class="container-fluid">
            <a class="navbar-brand" href="#"><i class="bi bi-box-fill"></i> WeDelivery</a>
            <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav"
                aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
                <span class="navbar-toggler-icon"></span>
            </button>
            <div class="collapse navbar-collapse" id="navbarNav">
                <ul class="navbar-nav">
                    <li class="nav-item">
                        <nuxt-link class="nav-link" to="/"><i class="bi bi-house"></i> Home</nuxt-link>
                    </li>
                    <li v-if="isLoggedIn" class="nav-item">
                        <nuxt-link class="nav-link" to="/orders"><i class="bi bi-box-seam"></i> Orders</nuxt-link>
                    </li>
                    <li v-if="isLoggedIn" class="nav-item">
                        <nuxt-link class="nav-link" to="/e-commerce"><i class="bi bi-shop"></i> E-Commerce</nuxt-link>
                    </li>
                    <li v-if="isLoggedIn" class="nav-item">
                        <nuxt-link class="nav-link" to="/clients"><i class="bi bi-person"></i> Clients</nuxt-link>
                    </li>
                    <li v-if="isLoggedIn" class="nav-item">
                        <nuxt-link class="nav-link" to="/cart"><i class="bi bi-cart"></i> Cart</nuxt-link>
                    </li>
                </ul>
                <ul class="navbar-nav ms-auto">
                    <li v-if="!isLoggedIn" class="nav-item">
                        <nuxt-link class="nav-link" to="/login"><i class="bi bi-box-arrow-in-right"></i>
                            Login</nuxt-link>
                    </li>
                    <li v-else class="nav-item">
                        <button @click="handleLogout" class="btn nav-link"><i class="bi bi-box-arrow-right"></i> Logout</button>
                    </li>
                </ul>
            </div>
        </div>
    </nav>
</template>

<script setup>
import { ref, onMounted } from 'vue';

const isLoggedIn = ref(false);

onMounted(() => {
    // Verifica se o token de acesso está no localStorage
    isLoggedIn.value = !!localStorage.getItem('AccessToken');
});

function handleLogout() {
    // Remove o token e atualiza a variável de estado
    localStorage.removeItem('AccessToken');
    isLoggedIn.value = false;
    // Redireciona para a página de login
    window.location.href = '/login';
}


</script>

<style scoped>
/* Specific styles for the navbar, if necessary */
</style>
