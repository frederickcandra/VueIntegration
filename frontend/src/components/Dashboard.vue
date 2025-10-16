<template>
  <div class="dashboard-container">
    <div class="dashboard-header">
      <!-- Bagian kiri: Logo + Judul -->
      <div style="display:flex;align-items:center;gap:16px;">
        <img
            src="/src/assets/images/dog-logo.svg"
            alt="Dog Logo"
            class="dashboard-logo"
            style="background:#fff;border-radius:50%;padding:4px;box-shadow:0 2px 8px rgba(44,62,80,0.08);"
        />
        <div class="dashboard-title">DogAPI Explorer</div>
      </div>

      <!-- Bagian kanan: Sosial + Logout -->
      <div style="display:flex;align-items:center;gap:20px;">
        <!-- 🌐 GitHub -->
        <a
            href="https://github.com/frederickcandra"
            target="_blank"
            rel="noopener noreferrer"
            title="GitHub"
            class="social-icon"
        >
          <img
              src="https://cdn.jsdelivr.net/gh/devicons/devicon/icons/github/github-original.svg"
              alt="GitHub"
          />
        </a>

        <!-- 💼 LinkedIn -->
        <a
            href="https://bit.ly/FrederickCandra"
            target="_blank"
            rel="noopener noreferrer"
            title="LinkedIn"
            class="social-icon"
        >
          <img
              src="https://cdn.jsdelivr.net/gh/devicons/devicon/icons/linkedin/linkedin-original.svg"
              alt="LinkedIn"
          />
        </a>

        <!-- 📸 Instagram -->
        <a
            href="https://www.instagram.com/frederickcandra_"
            target="_blank"
            rel="noopener noreferrer"
            title="Instagram"
            class="social-icon"
        >
          <img
              src="https://upload.wikimedia.org/wikipedia/commons/a/a5/Instagram_icon.png"
              alt="Instagram"
          />
        </a>

        <!-- 🔒 Logout -->
        <button
            @click="logout"
            class="auth-btn"
            style="height:40px;display:flex;align-items:center;justify-content:center;"
        >
          Logout
        </button>
      </div>
    </div>

    <!-- Konten Utama -->
    <div class="dashboard-content">
      <div class="text-center" style="margin-bottom:2rem;">
        <h2 style="font-size:2rem;color:var(--accent-dark);font-weight:700;">
          Discover Amazing Dogs
        </h2>
        <p style="color:var(--text-muted);max-width:600px;margin:0 auto;">
          Explore our collection of wonderful dog images from around the world.
          Each refresh brings a new furry friend to brighten your day.
        </p>
      </div>

      <div style="display:flex;flex-direction:column;align-items:center;">
        <div style="width:100%;max-width:500px;">
          <div v-if="loading" style="display:flex;align-items:center;justify-content:center;height:300px;">
            <div class="spinner"></div>
          </div>
          <img
              v-else
              :src="dogImage"
              style="width:100%;height:300px;object-fit:cover;border-radius:16px;box-shadow:var(--box-shadow);"
              alt="Random Dog"
          />
        </div>

        <button @click="fetchNewDog" class="auth-btn" style="margin-top:1.5rem;">
          🔄 Fetch New Dog
        </button>
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

/* 🌐 Styling ikon sosial */
.social-icon {
  display: flex;
  align-items: center;
  justify-content: center;
  width: 40px;
  height: 40px;
  border-radius: 50%;
  background: #fff;
  box-shadow: 0 2px 8px rgba(0,0,0,0.1);
  transition: all 0.3s ease;
}
.social-icon img {
  width: 22px;
  height: 22px;
}
.social-icon:hover {
  transform: translateY(-2px);
  box-shadow: 0 4px 12px rgba(0,0,0,0.15);
}
</style>
