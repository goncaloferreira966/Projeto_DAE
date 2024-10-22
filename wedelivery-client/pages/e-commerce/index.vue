<template>
    <div class="container">
        <div v-if="messages.length" class="alert alert-danger mt-4">
            <ul>
                <li v-for="(msg, index) in messages" :key="index">{{ msg }}</li>
            </ul>
        </div>

        <div class="col-md-12 mt-5">
            <div class="d-flex justify-content-between align-items-center mb-4">
                <h2>WeDelivery - Products</h2>
                <button @click.prevent="refresh" class="btn btn-info">Refresh Data</button>
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
                            <p><strong>Description:</strong> {{ product.description }}</p>
                            <p><strong>Price:</strong> €{{ product.price.toFixed(2) }}</p>
                            <p><strong>Available Quantity:</strong> {{ product.quantity }}</p>
                            <button class="btn btn-primary" :disabled="!product.available"
                                @click="addToCart(product.id)">
                                {{ product.available ? 'Add to Cart' : 'Out of Stock' }}
                            </button>
                            <nuxt-link :to="`/e-commerce/${product.id}`"
                                class="btn btn-secondary btn-block btn-sm mt-2">View Details</nuxt-link>
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
