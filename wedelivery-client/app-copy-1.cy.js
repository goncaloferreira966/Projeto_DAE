import App from './app.vue'
import { mount } from '@cypress/vue'
import { createRouter, createWebHistory } from 'vue-router'
import 'bootstrap/dist/css/bootstrap.min.css'
import 'bootstrap'
import Navbar from './pages/components/Navbar.vue'
import Footer from './pages/components/Footer.vue'
import { createApp } from 'vue'
import { RouterView } from 'vue-router'
describe('<App />', () => {
  it('renders', () => {
    see: http://localhost:3000/
    cy.mount(App)
  })
})