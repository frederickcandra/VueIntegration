<template>
  <div class="container mt-5">
    <h2 class="text-center mb-4">👩‍🚀 Daftar User</h2>

    <!-- Spinner saat loading -->
    <div v-if="loading" class="text-center my-5">
      <div class="spinner-border text-primary" role="status">
        <span class="visually-hidden">Loading...</span>
      </div>
    </div>

    <!-- List User -->
    <div v-else class="row">
      <div
          class="col-md-4 mb-4"
          v-for="user in users"
          :key="user.id"
      >
        <div class="card shadow-sm h-100">
          <img
              :src="`https://picsum.photos/300/200?random=${user.id}`"
              class="card-img-top"
              alt="User Image"
          />
          <div class="card-body text-center">
            <h5 class="card-title">{{ user.name }}</h5>
            <p class="text-muted">{{ user.email }}</p>

            <router-link
                :to="`/user/${user.id}`"
                class="btn btn-primary btn-sm"
            >
              Lihat Profil
            </router-link>
          </div>
        </div>
      </div>
    </div>

    <!-- Jika data kosong -->
    <div v-if="!loading && users.length === 0" class="text-center mt-5">
      <img src="https://cdn-icons-png.flaticon.com/512/748/748122.png" width="100" />
      <p class="text-muted">Tidak ada data user</p>
    </div>
  </div>
</template>

<script>
import axios from "axios";

export default {
  name: "UserList",
  data() {
    return {
      users: [],
      loading: true,
    };
  },
  mounted() {
    axios
        .get("http://localhost:8080/api/users")
        .then((response) => {
          this.users = response.data;
        })
        .catch((error) => {
          console.error("Error ambil data:", error);
        })
        .finally(() => {
          this.loading = false;
        });
  },
};
</script>
