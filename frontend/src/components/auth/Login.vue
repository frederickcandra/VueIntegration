<template>
  <div class="auth-container">
    <div class="auth-box">
      <img src="../../assets/images/dog-logo.svg" alt="Dog Logo" class="auth-logo" />
      <div class="auth-title">Welcome Back! 🐾</div>
      <form class="auth-form" @submit.prevent="handleLogin">
        <input
          v-model="username"
          type="text"
          class="auth-input"
          placeholder="Username"
          required
        />
        <input
          v-model="password"
          type="password"
          class="auth-input"
          placeholder="Password"
          required
        />
        <button type="submit" class="auth-btn">Login</button>
        <div v-if="error" class="auth-error">{{ error }}</div>
      </form>
      <router-link to="/register" class="auth-link">Don't have an account? Register</router-link>
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import axios from 'axios'

const router = useRouter()
const username = ref('')
const password = ref('')
const error = ref('')

const handleLogin = async () => {
  try {
    error.value = ''
    const response = await axios.post('http://localhost:8080/api/auth/login', {
      username: username.value,
      password: password.value
    })
    localStorage.setItem('token', response.data.token)
    router.push('/')
  } catch (err) {
    error.value = err.response?.data || 'An error occurred during login'
  }
}
</script>

<style scoped>
.auth-container {
  display: flex;
  align-items: center;
  justify-content: center;
  min-height: 100vh;
  background-color: #f0f4f8;
}

.auth-box {
  background-color: #ffffff;
  padding: 2rem;
  border-radius: 1rem;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
  width: 100%;
  max-width: 400px;
  text-align: center;
}

.auth-logo {
  width: 80px;
  height: 80px;
  margin-bottom: 1rem;
}

.auth-title {
  font-size: 1.5rem;
  font-weight: 600;
  margin-bottom: 1.5rem;
  color: #333;
}

.auth-form {
  display: flex;
  flex-direction: column;
  gap: 1rem;
}

.auth-input {
  padding: 0.75rem;
  border: 1px solid #d1d5db;
  border-radius: 0.5rem;
  font-size: 1rem;
  transition: border-color 0.3s;
}

.auth-input:focus {
  border-color: #6366f1;
  outline: none;
}

.auth-btn {
  padding: 0.75rem;
  background-color: #6366f1;
  color: #ffffff;
  border: none;
  border-radius: 0.5rem;
  font-size: 1rem;
  cursor: pointer;
  transition: background-color 0.3s;
}

.auth-btn:hover {
  background-color: #4f46e5;
}

.auth-error {
  margin-top: 1rem;
  color: #dc2626;
  font-size: 0.875rem;
}

.auth-link {
  margin-top: 1rem;
  color: #6366f1;
  text-decoration: none;
  font-size: 0.875rem;
}

.auth-link:hover {
  text-decoration: underline;
}
</style>
