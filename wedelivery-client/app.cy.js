import { mount } from '@cypress/vue';
import DefaultLayout from './layouts/default.vue'; // Ajuste o caminho conforme necessário
import Navbar from './pages/components/Navbar.vue'; // Ajuste o caminho conforme necessário
import Footer from './pages/components/Footer.vue'; // Ajuste o caminho conforme necessário
import { createRouter, createWebHistory } from 'vue-router';
import 'bootstrap/dist/css/bootstrap.min.css';
import 'bootstrap';

const routes = [
  { path: '/', component: { template: '<div>Home Page</div>' } },
  { path: '/manager', component: { template: '<div>Manager Page</div>' } },
  { path: '/e-commerce', component: { template: '<div>E-Commerce Page</div>' } },
  { path: '/clients', component: { template: '<div>Clients Page</div>' } },
];

// Crie o router com as rotas
const router = createRouter({
  history: createWebHistory(),
  routes,
});

describe('Navbar Navigation', () => {
  it('should navigate to Manager page', () => {
    mount(DefaultLayout, {
      global: {
        plugins: [router],
      },
    });
    cy.viewport(1280, 720);
    cy.get('nuxt-link').contains('Orders').click();
    cy.url().should('include', '/manager');
  });
});

