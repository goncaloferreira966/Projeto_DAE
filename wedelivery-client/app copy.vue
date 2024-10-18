<template>
  <div v-if="error">
    Error: {{ error.message }}
  </div>
  <div v-else>
    <div>
      <h2>Students</h2>
    </div>

    <nuxt-link to="/create">Create a New Student</nuxt-link>

    <h3>Version Card</h3>
    <div style="display: flex;">
      <div v-for="(student, index) in students" :key="index" class="card">
        <img src="/img_avatar.png" alt="Avatar" style="width:100%">
        <div class="container">
          <h4>{{ student.name }}</h4>
          <p>{{ student.email }}</p>
          <p>{{ student.courseName }}</p>
        </div>
      </div>
    </div>


    <br>
    <br>
    <br>
    <br>

    <h3>Version Table</h3>
    <div style="display: flex;">
      <table id="academics">
        <tr>
          <th>Name</th>
          <th>E-mail</th>
          <th>Username</th>
          <th>Course</th>
        </tr>
        <tr v-for="(student, index) in students" :key="index">
          <td>{{ student.name }}</td>
          <td>{{ student.email }}</td>
          <td>{{ student.username }}</td>
          <td>{{ student.courseName }}</td>
        </tr>
      </table>
    </div>


    <br>
    <br>
    <br>
    <br>

    <h3>Version JSON</h3>
    <div style="display: flex;">
      <div>{{ students }}</div>
      <button @click.prevent="refresh">Refresh Data</button>
    </div>


  </div>
  <NuxtPage />
</template>
<script setup>
  const config = useRuntimeConfig();
  const api = config.public.API_URL;
  const { data: students, error, refresh } = await useFetch(`${api}/student`)
</script>


<style>
.card {
  box-shadow: 0 4px 8px 0 rgba(0, 0, 0, 0.2);
  transition: 0.3s;
  width: 25vh;
  flex: 1 1 calc(25vh - 16px); /* Define a largura dos cards para 25% do contêiner menos o espaço do gap */
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1); /* Sombra para os cards */
  border-radius: 8px;        /* Cantos arredondados */
  overflow: hidden;
  max-width: 25vh !important;
  margin: 25px;
}

.card:hover {
  box-shadow: 0 8px 16px 0 rgba(0, 0, 0, 0.2);
}

.container {
  padding: 2px 16px;
  display: flex;               /* Ativa o flexbox */
  flex-wrap: wrap;           /* Permite que os itens se movam para a próxima linha */
  gap: 16px; 
}

#academics {
  font-family: Arial, Helvetica, sans-serif;
  border-collapse: collapse;
  width: 100%;
  border-radius: 25px;
}

#academics td, #academics th {
  border: 1px solid #ddd;
  padding: 8px;
}

#academics tr:nth-child(even){background-color: #f2f2f2;}

#academics tr:hover {background-color: #ddd;}

#academics th {
  padding-top: 12px;
  padding-bottom: 12px;
  text-align: left;
  background-color: #04AA6D;
  color: white;
}
</style>