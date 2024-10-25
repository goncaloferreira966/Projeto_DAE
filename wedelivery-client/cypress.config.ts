import { defineConfig } from "cypress";
import viteDevServer from "@cypress/vite-dev-server"; // Importação padrão
import viteConfig from "./vite.config"; // Importar a configuração do Vite

export default defineConfig({
  e2e: {
    setupNodeEvents(on, config) {
      // Use a função devServer do pacote
      on('dev-server:start', (options) => viteDevServer.devServer({...options, viteConfig})); // Passar a configuração do Vite
      return config;
    },
    baseUrl: "http://localhost:3000",
  },

  component: {
    devServer: {
      framework: "vue",
      bundler: "vite",
    },
  },
});
