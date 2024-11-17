<template>
    <div class="container main">
      <header>
        <section class="header-section">
          <img class="main-logo" @click="handleClick()" />
          <div class="button-container1">
            <router-link to="/create">
              <div type="button" class="btn btn-secondary">
                <i class="fi fi-rr-user"></i>Crear tarea
              </div>
            </router-link>
            <div v-if="isLogged">
              <div
                type="button"
                class="btn btn-secondary"
                @click="toggleIsLogged"
              >
                <i class="fi fi-rr-user"></i>Cerrar sesión
              </div>
            </div>
            <div v-else>
              <router-link to="/login">
                <div type="button" class="btn btn-secondary">
                  <i class="fi fi-rr-user"></i>Ingreso
                </div>
              </router-link>
            </div>
          </div>
        </section>
      </header>
  
      <section>
        <nav class="navbar navbar-expand-lg navbar-light bg-light">
          <div class="container-fluid">
            <button
              class="navbar-toggler"
              type="button"
              data-bs-toggle="collapse"
              data-bs-target="#navbarSupportedContent"
              aria-controls="navbarSupportedContent"
              aria-expanded="false"
              aria-label="Toggle navigation"
            >
              <span class="navbar-toggler-icon"></span>
            </button>
            <div class="collapse navbar-collapse" id="navbarSupportedContent">
              <ul class="navbar-nav me-auto mb-2 mb-lg-0">
                <li class="nav-item dropdown">
                  <a
                    class="nav-link dropdown-toggle"
                    href="#"
                    id="navbarDropdown"
                    role="button"
                    data-bs-toggle="dropdown"
                    aria-expanded="false"
                  >
                    Filtro de tareas
                  </a>
                  <ul class="dropdown-menu" aria-labelledby="navbarDropdown">
                    <li>
                      <button
                        class="dropdown-item"
                        @click="filterTasks('Completada')"
                        id="location1"
                      >
                        Completada
                      </button>
                    </li>
                    <li>
                      <button
                        class="dropdown-item"
                        @click="filterTasks('Pendiente')"
                        id="location2"
                      >
                        Por completar
                      </button>
                    </li>
                  </ul>
                </li>
              </ul>
              <form class="d-flex">
                <input
                  class="form-control me-2"
                  type="search"
                  placeholder="Buscar"
                  v-model="searchQuery"
                  aria-label="Buscar"
                />
                <button class="btn btn-primary" type="submit" @click.prevent="searchTasks">Buscar</button>
              </form>
            </div>
          </div>
        </nav>
      </section>
  
      <section>
        <h1>Tareas del Usuario</h1>
        <div v-if="tasks.length === 0" class="no-tasks">
          No hay tareas para mostrar.
        </div>
        <div v-else class="tasks-container">
          <div class="task-card" v-for="task in filteredTasks" :key="task.taskid">
            <h2>{{ task.tasktitle }}</h2>
            <p>{{ task.taskdesc }}</p>
            <p><strong>Fecha límite:</strong> {{ task.taskend_date }}</p>
            <p>
              <strong>Estado:</strong>
              <span :class="{'completed': task.iscompleted, 'pending': !task.iscompleted}">
                {{ task.iscompleted ? "Completada" : "Pendiente" }}
              </span>
            </p>
            <div class="actions">
              <button class="btn btn-primary" @click="editTask(task)">Editar</button>
              <button class="btn btn-danger" @click="deleteTask(task.taskid)">Eliminar</button>
            </div>
          </div>
        </div>
      </section>
  
      <nav class="nav-bar1" aria-label="Page navigation example">
        <ul class="pagination">
          <li class="page-item" :class="{ disabled: currentPage === 1 }">
            <a
              class="page-link"
              href="#"
              @click.prevent="changePage(currentPage - 1)"
              aria-label="Previous"
            >
              <span aria-hidden="true">&laquo;</span>
            </a>
          </li>
          <li
            v-for="page in totalPages"
            :key="page"
            class="page-item"
            :class="{ active: currentPage === page }"
          >
            <a class="page-link" href="#" @click.prevent="changePage(page)">{{ page }}</a>
          </li>
          <li class="page-item" :class="{ disabled: currentPage === totalPages }">
            <a
              class="page-link"
              href="#"
              @click.prevent="changePage(currentPage + 1)"
              aria-label="Next"
            >
              <span aria-hidden="true">&raquo;</span>
            </a>
          </li>
        </ul>
      </nav>
    </div>
  </template>
  
  <script>
  import axios from "axios";
  
  export default {
    data() {
      return {
        isLogged: false,
        currentPage: 1,
        tasks: [], // Almacena las tareas del usuario
        searchQuery: "", // Para búsqueda
        filter: "All", // Filtro de tareas
      };
    },
    computed: {
      filteredTasks() {
        return this.tasks.filter((task) => {
          if (this.filter === "All") return true;
          if (this.filter === "Completada") return task.iscompleted;
          if (this.filter === "Pendiente") return !task.iscompleted;
          return true;
        });
      },
      totalPages() {
        return Math.ceil(this.tasks.length / 10); // Ejemplo de paginación
      },
    },
    methods: {
      async fetchTasks() {
        try {
          const user = JSON.parse(sessionStorage.getItem("userLogged"));
          if (!user || !user.userid) {
            alert("Usuario no autenticado. Por favor, inicia sesión.");
            return;
          }
          const response = await axios.get(
            `http://localhost:8080/api/task/get/user/${user.userid}`
          );
          this.tasks = response.data;
        } catch (error) {
          console.error("Error al obtener las tareas:", error.message);
          alert("No se pudieron cargar las tareas.");
        }
      },
      searchTasks() {
        if (!this.searchQuery) {
          return this.fetchTasks(); // Si no hay búsqueda, recargar todas las tareas
        }
        this.tasks = this.tasks.filter((task) =>
          task.tasktitle.toLowerCase().includes(this.searchQuery.toLowerCase())
        );
      },
      filterTasks(status) {
        this.filter = status;
      },
      editTask(task) {
        alert(`Editar tarea: ${task.tasktitle}`);
      },
      async deleteTask(taskId) {
        if (!confirm("¿Estás seguro de que deseas eliminar esta tarea?")) return;
  
        try {
          await axios.post(`http://localhost:8080/api/task/delete/${taskId}`);
          this.tasks = this.tasks.filter((task) => task.taskid !== taskId);
          alert("Tarea eliminada con éxito.");
        } catch (error) {
          console.error("Error al eliminar la tarea:", error.message);
          alert("No se pudo eliminar la tarea.");
        }
      },
      toggleIsLogged() {
        this.isLogged = false;
        sessionStorage.removeItem("userLogged");
      },
      handleClick() {
        console.log("Logo clicked");
      },
      changePage(page) {
        this.currentPage = page;
      },
    },
    mounted() {
      this.fetchTasks();
    },
  };
  </script>
  
  <style scoped>
  .container {
    padding: 20px;
    max-width: 900px;
    margin: 0 auto;
  }
  
  h1 {
    text-align: center;
    margin-bottom: 20px;
  }
  
  .no-tasks {
    text-align: center;
    font-size: 18px;
    color: gray;
  }
  
  .tasks-container {
    display: flex;
    flex-wrap: wrap;
    gap: 20px;
  }
  
  .task-card {
    background-color: #f8f9fa;
    border: 1px solid #dee2e6;
    border-radius: 8px;
    padding: 15px;
    width: calc(50% - 10px);
    box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
    transition: transform 0.3s;
  }
  
  .task-card:hover {
    transform: scale(1.02);
  }
  
  .task-card h2 {
    margin: 0;
    font-size: 20px;
    color: #333;
  }
  
  .task-card p {
    margin: 5px 0;
    font-size: 14px;
  }
  
  .task-card .completed {
    color: green;
    font-weight: bold;
  }
  
  .task-card .pending {
    color: red;
    font-weight: bold;
  }
  
  .actions {
    margin-top: 10px;
    display: flex;
    gap: 10px;
  }
  
  .actions .btn {
    padding: 5px 10px;
    border: none;
    border-radius: 4px;
    cursor: pointer;
    font-size: 14px;
    font-weight: bold;
    transition: background-color 0.3s;
  }
  
  .actions .btn-primary {
    background-color: #007bff;
    color: #fff;
  }
  
  .actions .btn-primary:hover {
    background-color: #0056b3;
  }
  
  .actions .btn-danger {
    background-color: #dc3545;
    color: #fff;
  }
  
  .actions .btn-danger:hover {
    background-color: #a71d2a;
  }
  </style>
  