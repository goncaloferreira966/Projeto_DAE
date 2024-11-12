<template>
    <div class="container d-flex align-items-center justify-content-center min-vh-100">
      <div class="col-md-7">
        <div class="card shadow-lg border-0 rounded-3">
          <div class="card-header bg-secondary text-white text-center py-4">
            <h3>Login</h3>
          </div>
          <div class="card-body p-4">
            <form @submit.prevent="handleLogin">
              <div class="form-group mb-3 position-relative">
                <label for="username" class="form-label">Username</label>
                <div class="input-group">
                  <span class="input-group-text bg-light"><i class="bi bi-person-fill"></i></span>
                  <input
                    type="text"
                    id="username"
                    v-model="username"
                    class="form-control"
                    placeholder="Enter your username"
                    required
                  />
                </div>
              </div>
              <div class="form-group mb-4 position-relative">
                <label for="password" class="form-label">Password</label>
                <div class="input-group">
                  <span class="input-group-text bg-light"><i class="bi bi-lock-fill"></i></span>
                  <input
                    type="password"
                    id="password"
                    v-model="password"
                    class="form-control"
                    placeholder="Enter your password"
                    required
                  />
                </div>
              </div>
              <button type="submit" class="btn btn-primary w-100 btn-lg">Login</button>
            </form>
          </div>
        </div>
      </div>
    </div>
  </template>
  
  <script>
  import axios from 'axios';

  export default {
    data() {
      return {
        username: '',
        password: ''
      };
    },
    methods: {
        async handleLogin() {
      try {
        const response = await axios.post("http://localhost:8080/wedelivery/api/auth/login", {
          username: this.username,
          password: this.password,
        }, {
          headers: {
            Accept: "application/json",
          },
        });

        const token = response.data;
        console.log(token)
        // Se o login for bem-sucedido, armazena o token e emite o evento de sucesso
        localStorage.setItem("AccessToken", token);

        this.$emit("login-success");
        window.location.href = '/e-commerce';

        // Decodifica o payload do token

      } catch (error) {
        this.errorMessage = "Credenciais Inválidas.";
      }
    },
    }
  };
  </script>
  
  <style scoped>
  .container {
    max-width: 500px;
  }
  .card {
    border-radius: 10px;
  }
  .card-header {
    border-top-left-radius: 10px;
    border-top-right-radius: 10px;
  }
  .input-group-text {
    border: none;
  }
  </style>
  