<template>
  <div class="dashboard-container">
    <div class="dashboard-header">
      <img src="../../assets/images/dog-logo.svg" alt="Dog Logo" class="dashboard-logo" />
      <div class="dashboard-title">DogAPI Explorer</div>
      <button @click="logout" class="auth-btn" style="height:40px;">Logout</button>
    </div>
    <div class="dashboard-content">
      <div class="text-center" style="margin-bottom:2rem;">
        <h2 style="font-size:2rem;color:var(--accent-dark);font-weight:700;">Discover Amazing Dogs</h2>
        <p style="color:var(--text-muted);max-width:600px;margin:0 auto;">Explore our collection of wonderful dog images from around the world. Each refresh brings a new furry friend to brighten your day.</p>
      </div>
      <div style="display:flex;flex-direction:column;align-items:center;">
        <div style="width:100%;max-width:500px;">
          <div v-if="loading" style="display:flex;align-items:center;justify-content:center;height:300px;">
            <div class="spinner"></div>
          </div>
          <img v-else :src="dogImage" style="width:100%;height:300px;object-fit:cover;border-radius:16px;box-shadow:var(--box-shadow);" alt="Random Dog" />
        </div>
        <button @click="fetchNewDog" class="auth-btn" style="margin-top:1.5rem;">🔄 Fetch New Dog</button>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import axios from 'axios'
import { useRouter } from 'vue-router'

const router = useRouter()
const dogImage = ref('')
const loading = ref(false)

const fetchNewDog = async () => {
  loading.value = true
  try {
    const res = await axios.get('https://dog.ceo/api/breeds/image/random')
    dogImage.value = res.data.message
  } finally {
    loading.value = false
  }
}

const logout = () => {
  localStorage.removeItem('token')
  router.push('/login')
}

onMounted(fetchNewDog)
</script>

<style scoped>
.spinner {
  border: 4px solid #e0e3e7;
  border-top: 4px solid var(--accent);
  border-radius: 50%;
  width: 48px;
  height: 48px;
  animation: spin 1s linear infinite;
}
@keyframes spin {
  0% { transform: rotate(0deg); }
  100% { transform: rotate(360deg); }
}
</style>
