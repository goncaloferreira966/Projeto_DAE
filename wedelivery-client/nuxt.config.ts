// https://nuxt.com/docs/api/configuration/nuxt-config
export default defineNuxtConfig({
  compatibilityDate: "2024-04-03",
  css: [
    'bootstrap/dist/css/bootstrap.min.css'
  ],
  devtools: { enabled: true },
  runtimeConfig: {
    public: {
      API_URL: process.env.API_URL || "http://localhost:8080/wedelivery/api",
    },
  },
});
