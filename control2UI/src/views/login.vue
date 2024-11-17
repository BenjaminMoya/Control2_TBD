<template>
    <main>
      <div class="container main">
        <div class="content">
          <img class="image-login" />
          <div class="header" v-if="!register">Inicio de sesion</div>
          <div class="header" v-else>Registrarse</div>
          <!-- Si el usuario escoge registrarse, se cambia el título -->
          <div class="headerDescription" v-if="!register">Completar campos para iniciar sesion</div>
          <div class="headerDescription" v-else>Completar campos para registro</div>
          <!-- Formulario de inicio de sesión -->
          <div class="inputContainer" v-if="!register">
            <input type="email" v-model="usermail" placeholder="Ingrese correo" />
            <input type="password" v-model="password" placeholder="Ingrese contraseña" />
            <button class="sessionButton" @click="login">Iniciar sesion</button>
          </div>
          <!-- Formulario de registro -->
          <div class="inputContainer" v-else>
            <input type="text" v-model="nameRegister" placeholder="Ingrese su nombre" />
            <input type="email" v-model="useremailRegister" placeholder="Ingrese correo" />
            <input type="password" v-model="passwordRegister" placeholder="Ingrese contraseña" />
            <input
              type="password"
              v-model="passwordRegisterConfirmation"
              placeholder="Repita contraseña"
            />
            <button class="sessionButton" @click="addUser">Registrar</button>
          </div>
  
          <div class="alsoButtons">
            <!-- Botón para cambiar entre Inicio de sesión y Registro -->
            <div class="alsoButton" @click="toggleRegister" v-if="!register">Registrarse</div>
            <div class="alsoButton" @click="toggleRegister" v-else>Iniciar sesion</div>
            <router-link to="/home">
              <div class="alsoButton" v-if="register">Ingreso anonimo</div>
            </router-link>
            <!-- Recuperar contraseña -->
            <div
              class="alsoButton"
              data-bs-toggle="modal"
              data-bs-target="#contactModal"
              v-if="!register"
            >
              Recuperar contraseña
            </div>
            <div class="modal fade" id="contactModal" tabindex="-1" aria-labelledby="contactModalLabel" aria-hidden="true">
              <div class="modal-dialog">
                <div class="modal-content">
                  <div class="modal-header">
                    <h5 class="modal-title" id="contactModalLabel">Recuperacion de contraseña</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                  </div>
                  <div class="modal-body">
                    <form id="contactForm">
                      <div class="mb-3">
                        <label for="name" class="form-label">Nombre</label>
                        <input type="text" class="form-control" id="name" required />
                      </div>
                      <div class="mb-3">
                        <label for="email" class="form-label">Correo Electrónico</label>
                        <input type="email" class="form-control" id="email" required />
                      </div>
                    </form>
                  </div>
                  <div class="modal-footer">
                    <button type="button" class="btn btn-primary" data-bs-dismiss="modal">Cerrar</button>
                    <button type="submit" class="btn btn-primary" onclick="submitForm()">Enviar</button>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </main>
  </template>
  
  <script>
  import axios from "axios";
  import mainComponent from "../components/mainComponent.vue";
  import { userState } from "../components/userState"; // Estado global del usuario
  
  export default {
    components: {
      mainComponent,
    },
    data() {
      return {
        usermail: "",
        password: "",
        register: false, // Estado para alternar entre formularios
        nameRegister: "",
        useremailRegister: "",
        passwordRegister: "",
        passwordRegisterConfirmation: "",
        userLogged: null,
      };
    },
    methods: {
      async login() {
        const params = new URLSearchParams();
        params.append("email", this.usermail);
        params.append("password", this.password);
  
        try {
          const respuesta = await axios.post(
            import.meta.env.VITE_BASE_URL + "api/user/login",
            params
          );
  
          if (respuesta.data) {
            try {
              const userResponse = await axios.get(
                import.meta.env.VITE_BASE_URL + `api/user/getemail/${this.usermail}`
              );
              sessionStorage.setItem("isLogged", true);
              sessionStorage.setItem("userLogged", JSON.stringify(userResponse.data));
              userState.setUser(userResponse.data);
              this.userLogged = userState.getUser();
              console.log("Usuario logueado:", this.userLogged);
            } catch (error) {
              console.error("Error en axios: Búsqueda del usuario", error.response?.data || error.message);
            }
  
            window.location.href = "/home";
          } else {
            alert("Credenciales inválidas");
          }
        } catch (error) {
          console.error("Error en axios: Login", error.response?.data || error.message);
        }
      },
      async addUser() {
        if (
          this.useremailRegister !== "" &&
          this.passwordRegisterConfirmation !== "" &&
          this.passwordRegister !== "" &&
          this.nameRegister !== ""
        ) {
          if (this.passwordRegister === this.passwordRegisterConfirmation) {
            const emailFormat = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;
            if (!emailFormat.test(this.useremailRegister)) {
              alert("Formato de email inválido");
              return;
            }
  
            const usernameFormat = /^[a-zA-Z0-9-_]{3,25}$/;
            if (!usernameFormat.test(this.nameRegister)) {
              alert("Formato de nombre inválido");
              return;
            }
  
            const passwordFormat = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@$!%*?&])[A-Za-z\d@$!%*?&]{8,}$/;
            if (!passwordFormat.test(this.passwordRegister)) {
              alert(
                "Formato inválido, ingrese 8 carácteres como mínimo con al menos una mayúscula, un número, y un carácter especial @$!%*?&"
              );
              return;
            }
  
            const new_user = {
              username: this.nameRegister,
              useremail: this.useremailRegister,
              userpassword: this.passwordRegister,
            };
  
            try {
              const registro = await axios.post(
                import.meta.env.VITE_BASE_URL + "api/user/register",
                new_user
              );
              console.log(registro);
              alert("Usuario creado con éxito");
            } catch (error) {
              alert("El usuario no se pudo registrar");
            }
          } else {
            alert("Las contraseñas no coinciden");
          }
        } else {
          alert("Rellene todos los campos para registrarse");
        }
      },
      toggleRegister() {
        this.register = !this.register;
        console.log("Cambiando entre formularios: Estado actual de register:", this.register);
      },
    },
  };
  </script>
  
  <style scoped>
  .main {
    display: flex;
    justify-content: center;
    align-items: center;
    background: linear-gradient(45deg, #ded1b6, #ded1b6, #6ca19e, #6d997a);
    background-repeat: no-repeat;
    background-size: cover;
    background-position: center;
    min-height: 100vh;
  }
  .sessionButton {
    height: 30px;
    background-color: #6d997a;
    color: #f5f5f5;
    font-size: 16px;
    font-weight: 500;
    border: #6d997a solid 2px;
    border-radius: 3px;
    cursor: pointer;
    transition: background-color 0.2s;
    margin-bottom: 10px;
  }
  .sessionButton:hover {
    background-color: #42282c;
    transition: background-color 0.2s;
  }
  .image {
    height: 150px;
  }
  .content {
    box-sizing: border-box;
    padding: 10px;
    background-color: #f8f6f6f3;
    display: flex;
    flex-direction: column;
    align-items: center;
    min-width: 300px;
    max-width: 500px;
    min-height: 500px;
    max-height: 550px;
    border-radius: 10px;
  }
  .header {
    text-align: center;
    font-size: 40px;
    font-weight: 500;
    color: black;
  }
  .headerDescription {
    font-size: 16px;
    margin-bottom: 20px;
    color: black;
  }
  .inputContainer {
    display: flex;
    flex-direction: column;
    gap: 10px;
  }
  .input {
    height: 50px;
    padding: 10px;
    font-size: 16px;
    border-radius: 4px;
    border: #cdcdcd solid 3px;
    outline: none;
    transition: border 0.2s;
  }
  .input:hover {
    border: #bbbbbb solid 3px;
  }
  .input:focus {
    border: #888888 solid 3px;
  }
  .alsoButton {
    text-align: center;
    cursor: pointer;
    font-weight: 500;
    color: black;
    transition: all 0.2s;
  }
  .alsoButton:hover {
    text-decoration: underline;
  }
  .modal-title {
    color: black;
  }
  .form-label {
    color: black;
  }
  .image-login {
    width: 250px;
    height: 80px;
    margin: 30px 0;
  }
  </style>
  