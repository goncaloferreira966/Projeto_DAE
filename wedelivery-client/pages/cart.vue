<template>
    <section class="h-100 h-custom">
        <div class="container">
            <h2><i class="bi bi-cart-fill"></i> Shopping Cart</h2>

            <div v-if="items.length === 0" class="alert alert-warning mt-4">
                Your cart is empty.
            </div>
            <div v-else>
                <div class="row d-flex justify-content-center align-items-start mt-5" style="background-color: #eee; padding-top: 2%; padding-bottom: 2%;">
                    <div class="col-lg-7">
                        <h5 class="mb-3">
                            <router-link to="/e-commerce" class="text-body">
                                <i class="fas fa-long-arrow-alt-left me-2"></i> Continue shopping
                            </router-link>
                        </h5>
                        <hr>
                        <div class="d-flex justify-content-between align-items-center mb-4">
                            <div>
                                <p class="mb-1">Shopping cart</p>
                                <p class="mb-0">You have {{ items.length }} items in your cart</p>
                            </div>
                            <div>
                                <p class="mb-0">
                                    <span class="text-muted">Sort by:</span>
                                    <a href="#!" class="text-body">price <i class="fas fa-angle-down mt-1"></i></a>
                                </p>
                            </div>
                        </div>
                        <div class="row mt-5">
                            <div class="col-md-12" v-for="item in items" :key="item.id">
                                <div class="card mb-3" style="box-shadow: 0 4px 10px rgba(0, 0, 0, 0.1);">
                                    <div class="row no-gutters">
                                        <div class="col-md-1">
                                            <img :src="`/images/${item.image}`" alt="Product Image" class="card-img" style="max-height: 100px; width: auto; height: auto; object-fit: contain;">
                                        </div>
                                        <div class="col-md-6" style="padding-left: 3%;">
                                            <div class="card-body d-flex align-items-center" style="height: 100%;">
                                                <h5>{{ item.name }}</h5>
                                                <p class="small mb-0">Price: €{{ item.price.toFixed(2) }}</p>
                                            </div>
                                        </div>
                                        <div class="col-md-5">
                                            <div class="card-body d-flex justify-content-end align-items-center" style="height: 100%;">
                                                <h5 class="col-4 mb-0">{{ item.quantity }}</h5>
                                                <h5 class="col-4 mb-0">€{{ (item.price * item.quantity).toFixed(2) }}</h5>
                                                <h5 class="col-4 mb-0 text-end">
                                                    <a href="#!" class="text-danger" @click.prevent="removeItem(item.id)">
                                                        <i class="bi bi-trash"></i>
                                                    </a>
                                                </h5>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="col-lg-5">
                        <div class="card bg-primary text-white rounded-3">
                            <div class="card-body">
                                <h5 class="mb-4">Card details</h5>
                                <form @submit.prevent="validateAndSend">
                                    <div class="form-outline form-white mb-4">
                                        <input type="text" id="typeName" class="form-control form-control-lg" placeholder="Cardholder's Name" required />
                                        <label class="form-label" for="typeName">Cardholder's Name</label>
                                    </div>
                                    <div class="form-outline form-white mb-4">
                                        <input type="text" id="typeText" class="form-control form-control-lg" placeholder="1234 5678 9012 3457" minlength="19" maxlength="19" required />
                                        <label class="form-label" for="typeText">Card Number</label>
                                    </div>
                                    <div class="row mb-4">
                                        <div class="col-md-6">
                                            <div class="form-outline form-white">
                                                <input type="text" id="typeExp" class="form-control form-control-lg" placeholder="MM/YYYY" minlength="7" maxlength="7" required />
                                                <label class="form-label" for="typeExp">Expiration</label>
                                            </div>
                                        </div>
                                        <div class="col-md-6">
                                            <div class="form-outline form-white">
                                                <input type="password" id="typeCvv" class="form-control form-control-lg" placeholder="&#9679;&#9679;&#9679;" minlength="3" maxlength="3" required />
                                                <label class="form-label" for="typeCvv">CVV</label>
                                            </div>
                                        </div>
                                    </div>
                                    <hr class="my-4">
                                    <div class="d-flex justify-content-between">
                                        <p class="mb-2">Subtotal</p>
                                        <p class="mb-2">€{{ totalPrice.toFixed(2) }}</p>
                                    </div>
                                    <div class="d-flex justify-content-between">
                                        <p class="mb-2">Shipping</p>
                                        <p class="mb-2">€20.00</p>
                                    </div>
                                    <div class="d-flex justify-content-between mb-4">
                                        <p class="mb-2">Total (Incl. taxes)</p>
                                        <p class="mb-2">€{{ (totalPrice + 20).toFixed(2) }}</p>
                                    </div>
                                    <button type="submit" class="btn btn-info btn-block btn-lg">
                                        <div class="d-flex justify-content-between">
                                            <span>€{{ (totalPrice + 20).toFixed(2) }}</span>
                                            <span>Checkout <i class="fas fa-long-arrow-alt-right ms-2"></i></span>
                                        </div>
                                    </button>
                                </form>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </section>
</template>

<script setup>
import { useCartStore } from '../stores/cart';
import { computed } from 'vue';
const config = useRuntimeConfig();

const cartStore = useCartStore();
const items = computed(() => cartStore.items);
const totalPrice = computed(() => cartStore.totalPrice);
const notify = (message) => {
    alert(message);
};

const removeItem = (productId) => {
    cartStore.removeFromCart(productId);
};

const validateAndSend = async () => {
    // Perform validation (add your own validation logic)
    // If valid, proceed to checkout
    if (items.value.length === 0) {
        console.error('No items in cart to send.');
        alert('Your cart is empty. Please add items to proceed.');
        return;
    }
    console.log('Proceeding to checkout with items:', items.value);
    for (const item of items.value) {
        if (!item.id || !item.name || item.quantity <= 0) {
            console.error('Invalid item:', item);
            alert('Some items in your cart are invalid. Please check your cart.');
            return;
        }
    }
    await sendItems();
};
const sendItems = async () => {
    try {
        console.log('Enviando itens...', items.value, totalPrice.value);
        const response = await fetch(config.public.API_URL + '/orders', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({
                items: items.value,
                totalPrice: totalPrice.value + 20,
            }),
        });
       
        if (!response.ok) {
            const errorData = await response.json();
            throw new Error(`Erro ${response.status}: ${errorData.message || 'Erro desconhecido'}`);
        }
        alert('Pedido criado com sucesso. ID: ' + data.orderId);
        const data = await response.json();
    } catch (error) {
        console.error('Erro na operação fetch:', error);
    }
};

</script>

<style scoped>
@media (min-width: 1025px) {
    .h-custom {
        height: 100vh !important;
    }
}
</style>
