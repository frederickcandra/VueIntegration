<template>
  <div class="container mt-5">
    <h2 class="text-center mb-4">👤 Detail User</h2>

    <div v-if="loading" class="text-center">
      <div class="spinner-border text-primary" role="status" style="width: 3rem; height: 3rem;">
        <span class="visually-hidden">Loading...</span>
      </div>
    </div>

    <div v-else-if="user" class="card shadow-sm p-4 text-center">
      <img
          :src="`https://picsum.photos/300/200?random=${user.id}`"
          class="rounded mb-3"
          alt="User Image"
      >
      <h3>{{ user.name }}</h3>
      <p class="text-muted">{{ user.email }}</p>

      <router-link to="/" class="btn btn-secondary btn-sm">⬅ Kembali</router-link>
    </div>

    <div v-else class="text-center text-danger">
      <p>❌ User tidak ditemukan</p>
    </div>
  </div>
</template>

<script>
import axios from "axios";

export default {
  name: "UserDetail",
  props: ["id"],
  data() {
    return {
      user: null,
      loading: true
    };
  },
  mounted() {
    axios.get(`http://localhost:8080/api/users/${this.id}`)
        .then(response => {
          this.user = response.data;
        })
        .catch(error => {
          console.error("Error ambil detail user:", error);
        })
        .finally(() => {
          this.loading = false;
        }, 1000);
  }
};
</script>
