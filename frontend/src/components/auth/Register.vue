<template>
  <div class="auth-container">
    <div class="auth-box">
      <img src="../../assets/images/dog-logo.svg" alt="Dog Logo" class="auth-logo" />
      <div class="auth-title">Create Account ✨</div>
      <form class="auth-form" @submit.prevent="handleRegister">
        <input
          v-model="username"
          type="text"
          class="auth-input"
          placeholder="Username"
          required
        />
        <input
          v-model="email"
          type="email"
          class="auth-input"
          placeholder="Email"
          required
        />
        <input
          v-model="password"
          type="password"
          class="auth-input"
          placeholder="Password"
          required
        />
        <button type="submit" class="auth-btn">Register</button>
        <div v-if="error" class="auth-error">{{ error }}</div>
      </form>
      <router-link to="/login" class="auth-link">Already have an account? Login</router-link>
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import axios from 'axios'

const router = useRouter()
const username = ref('')
const email = ref('')
const password = ref('')
const error = ref('')

const handleRegister = async () => {
  try {
    error.value = ''
    await axios.post('http://localhost:8080/api/auth/register', {
      username: username.value,
      email: email.value,
      password: password.value
    })
    router.push('/login')
  } catch (err) {
    error.value = err.response?.data || 'An error occurred during registration'
  }
}
</script>

<style scoped>
</style>
