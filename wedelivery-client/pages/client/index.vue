<template>
  <div v-if="error">Error: {{ error.message }}</div>
  <div v-else>
    <nuxt-link to="/create">Create a New Student</nuxt-link>
    <h2>Clients</h2>
    <table>
      <thead>
        <tr>
          <th>Username</th>
          <th>Name</th>
          <th>E-mail</th>
          <th>Course</th>
          <th>Details</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="client in clients">
          <td>{{ client.username }}</td>
          <td>{{ client.name }}</td>
          <td>{{ client.email }}</td>
          <td><nuxt-link :to="`/clients/${client.username}`">Details</nuxt-link></td>
        </tr>
      </tbody>
    </table>
  </div>
  <button @click.prevent="refresh">Refresh Data</button>
</template>
<script setup>
const config = useRuntimeConfig()
const api = config.public.API_URL
const { data: clients, error, refresh } = await useFetch(`${api}/clients`)
</script>