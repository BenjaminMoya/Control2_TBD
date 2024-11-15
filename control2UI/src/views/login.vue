<template>
    <main>
        <div class="container main">
            <div class="content">
                <img class="image-login">
                <div class="header" v-if="!register">Inicio de sesion</div>
                <div class="header" v-else>Registrarse</div> <!--Si el usuario escoge la opcion de registrarse, se mostrara el titulo "Registrarse"-->
                <div class="headerDescription" v-if="!register">Completar campos para iniciar sesion</div>
                <div class="headerDescription" v-else>Completar campos para registro</div><!--De igual manera se mostrara un texto diferente dependiendo de la opcion escogida-->
                <div class="inputContainer" v-if="!register">
                    <input type="email" v-model="usermail" placeholder="Ingrese correo">
                    <input type="password" v-model="password" placeholder="Ingrese contraseña">
                    <button class="sessionButton" @click="login">Iniciar sesion</button>
                </div>
                <div class="inputContainer" v-else><!--Si el usuario escoge la opcion de registrarse, se mostrara un formulario para el registro de usuario sino sera el de inicio de sesion-->
                    <input type="text" v-model="nameRegister" placeholder="Ingrese su nombre">
                    <input type="email" v-model="useremailRegister" placeholder="Ingrese correo">
                    <input type="password" v-model="passwordRegister" placeholder="Ingrese contraseña">
                    <input type="password" v-model="passwordRegisterConfirmation" placeholder="Repita contraseña">
                    <button class="sessionButton" @click="addUser">Registrar</button>
                </div>
                <div class="alsoButtons"> 
                    <div class="alsoButton" @click="handleChanger" v-if="!register">Registrarse</div>
                    <div class="alsoButton" @click="handleChanger" v-else>Iniciar sesion</div> <!--Manejador de opciones para cambiar entre iniciar sesion y registrarse-->
                    <router-link to="/home">
                        <div class="alsoButton" @click="handleChanger" v-if="register">Ingreso anonimo</div>
                    </router-link>
                    <div class="alsoButton" data-bs-toggle="modal" data-bs-target="#contactModal" v-if="!register">Recuperar contraseña</div> <!--Boton para recuperar contraseña-->
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
                                        <input type="text" class="form-control" id="name" required>
                                    </div>
                                    <div class="mb-3">
                                        <label for="email" class="form-label">Correo Electrónico</label>
                                        <input type="email" class="form-control" id="email" required>
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
//Importaciones para la logica de la vista
import axios from 'axios'
import mainComponent from '../components/mainComponent.vue'

function redirectUser(){//Funcion para redirigir al usuario a la pagina principal

    window.location.href = '/';
}

export default{

    components: {//Componente principal de la vista
        mainComponent
    },
    data(){

        return{//Variables de la vista

            usermail: '',
            password: '',
            register: false,
            nameRegister: '',
            useremailRegister: '',
            passwordRegister: '',
            passwordRegisterConfirmation: '',
            userLogged:null,

        }
    },

    methods:{//Metodos de la vista

        async login(){//Metodo para el inicio de sesion

            const user = {

                "userEmail":this.usermail,
                "userPassword":this.password,

            };
            try{
                const respuesta = await axios.post(import.meta.env.VITE_BASE_URL + "api/user/login",user);
                if(respuesta.data == 1){

                    sessionStorage.setItem('isLogged',JSON.stringify(true));
                    try{//Busqueda del usuario logueado
                        const respuesta = await axios.get(import.meta.env.VITE_BASE_URL + "api/user/getuser",{params:{"UserEmail":this.usermail}});
                        sessionStorage.setItem('userLogged',JSON.stringify(respuesta.data));
                        this.userLogged = respuesta.data;
                    } catch(error){

                        console.log("Error en axios: Busqueda del usuario");
                    }

                    try{//Busqueda de propiedades del usuario logueado
                        const respuesta = await axios.get(import.meta.env.VITE_BASE_URL + "api/property/user/" + this.userLogged.id);
                        sessionStorage.setItem('userProperties',JSON.stringify(respuesta.data));
                        this.userProperties = respuesta.data;
                      
                    }catch(error){

                        console.log("Error en axios: Busqueda de propiedades");

                    }
                    redirectUser();
                }
                if(respuesta.data == 0){

                    alert("Credenciales invalidas");
                }

            } catch (error) {

                console.log("Error en axios: Login");

            }
        },
        handleChanger(){//Manejador de opciones para cambiar entre iniciar sesion y registrarse

            this.register = !this.register;
            console.log(this.register);
        },
        anon(){//Inicio de sesion anonimo

            this.name= "anon";
            redirectUser();

        },
        async addUser(){//Registro de usuario

            if(this.useremailRegister != '' && this.passwordRegisterConfirmation != '' && this.passwordRegister != '' && this.nameRegister != ''){

                // Verificar que las contraseñas ingresadas coincidan
                if(this.passwordRegister == this.passwordRegisterConfirmation){

                    // Verificar formato de email con expresión regular para evitar inserción de datos SQL
                    const emailFormat = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;
                    
                    if (!emailFormat.test(this.useremailRegister)) {
                        alert("Formato de email inválido");
                    }

                    // Verificar formato de nombre de usuario con expresión regular para evitar inserción de datos SQL
                    // Se permiten letras, números y guiones. Longitud entre 3 y 25 carácteres
                    const usernameFormat = /^[a-zA-Z0-9-_]{3,25}$/;

                    if (!usernameFormat.test(this.nameRegister)) {
                        alert("Formato de nombre inválido");
                        return;
                    }

                    // Al menos una letra minúscula, al menos una mayúscula, al menos un número, al menos un carácter especial @$!%*?&
                    // Mínimo 8 carácteres
                    const passwordFormat = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@$!%*?&])[A-Za-z\d@$!%*?&]{8,}$/;

                    if(!passwordFormat.test(this.passwordRegister)) {
                        alert("Formato inválido, ingrese 8 carácteres como mínimo y que se cumpla: al menos una mayúscula, al menos un número, al menos un carácter especial @$!%*?&")
                        return;
                    }
                    

                    const new_user = {

                        "username":this.nameRegister,
                        "userEmail":this.useremailRegister,
                        "userPassword":this.passwordRegister,
                        
                    };
                    try{

                        const registro = await axios.post(import.meta.env.VITE_BASE_URL + "api/user/register",new_user); 
                        console.log(registro);
                        alert("Usuario creado con exito");

                    } catch(error){

                        alert("El usuario no se pudo registrar");
                    }
                } else {

                    alert("Las contraseñas no coinciden");
                }
            } else {

                alert("Rellene todos los campos para registrarse");
            }
        },
    }
}
</script>

<style scoped>
.main{

    display: flex;
    justify-content: center;
    align-items: center;
    background: linear-gradient(45deg, #ded1b6, #ded1b6, #6ca19e, #6d997a);
    background-repeat: no-repeat;
    background-size: cover;
    background-position: center;
    height: auto; /* Ajusta la altura según el contenido */
    min-height: 100vh;
    
}
.sessionButton{

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

.sessionButton:hover{
    background-color: #42282c;
    transition: background-color 0.2s;

}
.image{

    height: 150px;
}

.content{

    box-sizing: border-box;
    padding: 10px;
    background-color: #f8f6f6f3;
    display: flex;
    flex-direction: column;
    align-items: center;

    min-width: 300px;
    max-width: 500px;
    width: 100%;
    margin: 0px 10px 0px 10px;
    min-height: 500px;
    max-height: 550px;
    height: 100%;

    border-radius: 10px;
}

.header{
    text-align: center;
    line-height: 1;
    font-size: 40px;
    font-weight: 500 !important;
    color: black;
}

.headerDescription{

    font-size: 16px;
    margin-bottom: 20px;
    color: black;
}

.inputContainer{
    display: flex;
    gap: 1px;
    width: 100%;
    max-width: 400px;
    flex-direction: column;

}

.input{

    height: 50px;
    box-sizing: border-box;

    padding: 10px !important;
    font-size: 16px;
    border-radius: 4px;
    outline: none;
    border: #cdcdcd solid 3px;
    transition: border 0.2s;
}
.input:hover{
    border: #bbbbbb solid 3px;
    transition: border 0.2s;

}
.input:focus{
    border: #888888 solid 3px;
    transition: border 0.2s;
}

.alsoButton{
    
    text-align: center;
    cursor: pointer;
    font-weight: 500;
    transition: all 0.2s;
    color: black;

}

.alsoButton:hover{

    text-decoration: underline;
    transition: all 0.2s;
}
.modal-title{

    color: black;
}
.form-label{

    color: black;
}

.image-login{

    width: 250px;
    height: 80px;
    margin-top: 30px;
    margin-bottom: 20px;
}
@media(min-width: 418px){

    .content{
        max-height: 500px;
    }
}
</style>