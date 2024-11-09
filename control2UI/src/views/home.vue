<template>
  <div>
    <NavBar />
    <main class="main-content">
      <h1 class="title">Agenda Usachin</h1>
      <p class="description">Esta es una aplicación de agendas de tareas que te ayuda a organizar y gestionar tus actividades diarias de manera eficiente.</p>
    </main>
  </div>
</template>

<script>
import NavBar from '../components/NavBar.vue';

export default {
  name: 'Home',
  components: {
    NavBar
  }
}
</script>

<style scoped>
.main-content {
  margin-top: 4rem; /* Adjust this value if the header height changes */
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  height: 100vh;
  text-align: center;
  box-sizing: border-box;
}

.title {
  font-size: 2rem;
  margin-bottom: 1rem;
}

.description {
  font-size: 1.2rem;
  color: #666;
}
</style>