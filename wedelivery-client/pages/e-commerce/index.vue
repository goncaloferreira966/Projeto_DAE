<template>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons/font/bootstrap-icons.css" rel="stylesheet">
    <div class="container">
        <div v-if="messages.length" class="alert alert-danger mt-4">
            <ul>
                <li v-for="(msg, index) in messages" :key="index">{{ msg }}</li>
            </ul>
        </div>

        <div class="col-md-12 mt-5">
            <div class="d-flex justify-content-between align-items-center mb-4">
                <h2><i class="bi bi-shop"></i> WeDelivery - Products</h2>
                <button @click.prevent="refresh" class="btn btn-info"><i class="bi bi-arrow-clockwise"></i> Refresh
                    Data</button>
            </div>

            <!-- Campo de Pesquisa -->
            <div class="mb-4">
                <input type="text" class="form-control" F placeholder="Search products by name..." v-model="searchQuery"
                    @input="searchProducts" />
            </div>

            <!-- Cards com informação dos produtos -->
            <div class="row mt-5">
                <div class="col-lg-4 col-md-6 mb-4" v-for="product in products" :key="product.id">
                    <div class="card border-secondary">
                        <div class="card-header bg-secondary text-white">
                            {{ product.name }}
                        </div>
                        <div class="card-body">
                            <div class="row">
                                <div class="col-md-6">
                                    <p><strong>Description:</strong> {{ product.description }}</p>
                                    <p><strong>Price:</strong> €{{ product.price.toFixed(2) }}</p>
                                    <p>
                                        <span v-if="product.quantity === 0" class="text-danger">
                                            <i class="bi bi-x-circle-fill"></i>
                                            <strong> Stock ({{ product.quantity }})</strong>
                                        </span>
                                        <span v-else-if="product.quantity > 0 && product.quantity <= 5"
                                            class="text-warning">
                                            <i class="bi bi-exclamation-triangle-fill"></i>
                                            <strong> Stock ({{ product.quantity }})</strong>
                                        </span>
                                        <span v-else class="text-success">
                                            <i class="bi bi-check-circle-fill"></i>
                                            <strong> Stock ({{ product.quantity }})</strong>
                                        </span>
                                    </p>
                                </div>
                                <div class="col-md-6">
                                    <img :src="`${config.public.URL}/images/${product.image}`" alt="Product Image"
                                        class="img-fluid" style="max-height: 11vh;" />
                                </div>
                            </div>
                            <button style="float: right" class="btn btn-primary mt-3" :disabled="!product.quantity"
                                @click="addToCart(product.id)">
                                <i class="bi bi-bag-plus"></i>
                                {{ product.quantity ? 'Add to Cart' : 'Out of Stock' }}
                            </button>
                            <nuxt-link style="float: left;" :to="`/e-commerce/${product.id}`"
                                class="btn btn-dark btn-block mt-3"><i class="bi bi-eye-fill"></i> View Details</nuxt-link>
                        </div>

                    </div>
                </div>
            </div>
        </div>
    </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useRuntimeConfig } from '#imports'
definePageMeta({
    layout: 'default'
});
const config = useRuntimeConfig()
const apiUrl = `${config.public.API_URL}/products`
const searchApiUrl = `${config.public.API_URL}/products/name/`

const products = ref([])
const messages = ref([])
const searchQuery = ref('')

// Função para carregar os produtos da API
const loadProducts = async () => {
    try {
        const response = await fetch(apiUrl)
        if (!response.ok) {
            throw new Error('Failed to fetch products')
        }
        products.value = await response.json()
    } catch (error) {
        messages.value.push(error.message)
    }
}

// Função para pesquisar produtos
const searchProducts = async () => {
    if (searchQuery.value.trim() === '') {
        await loadProducts() // Se a pesquisa estiver vazia, recarrega todos os produtos
        return
    }

    try {
        const response = await fetch(`${searchApiUrl}${searchQuery.value}`)
        if (!response.ok) {
            throw new Error('Failed to fetch products')
        }
        products.value = await response.json()
    } catch (error) {
        messages.value.push(error.message)
    }
}

// Função para adicionar o produto ao carrinho
const addToCart = (productId) => {
    alert(`Product ${productId} added to cart!`)
}

// Carrega os produtos ao montar o componente
onMounted(loadProducts)
</script>

<style scoped>
.card {
    margin-top: 20px;
}
</style>
