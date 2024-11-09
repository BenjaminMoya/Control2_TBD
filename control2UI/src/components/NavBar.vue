<template>
  <header class="header">
    <h1>My App</h1>
    <nav>
      <button @click="handleLogin">Iniciar Sesión</button>
    </nav>
  </header>
</template>

<script>
export default {
  name: 'NavBar',
  methods: {
    handleLogin() {
      this.$router.push('/login');
    }
  }
}
</script>

<style scoped>
.header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 1rem;
  background-color: #f5f5f5;
  border-bottom: 1px solid #ddd;
  width: 100%;
  position: fixed;
  top: 0;
  left: 0;
  z-index: 1000;
  box-sizing: border-box;
}

.header h1 {
  margin: 0;
}

.header nav {
  display: flex;
}

.header nav button {
  margin-left: 1rem;
}
</style>