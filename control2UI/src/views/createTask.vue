<template>
    <main>
        <div class="container main">
            <div class="content">
                <div class="header">Crear tarea</div>
                <div class="headerDescription">Completar campos</div>
                <div class="inputContainer">
                    <input type="text" v-model="taskTitle" placeholder="Ingrese título de la tarea">
                    <textarea v-model="taskDesc" placeholder="Ingrese descripción de la tarea" rows="4" cols="50"></textarea>
                    <label for="taskEndDate">Seleccione una fecha de término de la tarea:</label>
                    <input id="taskEndDate" type="date" v-model="taskEndDate">

                    <button class="sessionButton" @click="addTask">Crear</button>
                </div>
                <router-link to="/home">
                    <div class="alsoButton">Volver</div>
                </router-link>
            </div>
        </div>
    </main>
</template>

<script>
import axios from 'axios'
import mainComponent from '../components/mainComponent.vue'
import { userState } from "../components/userState"

export default {
    components: {
        mainComponent
    },

    data() {
        return {
            taskTitle: '',
            taskDesc: '',
            taskEndDate: '',
            isCompleted: false
        }
    },

    methods: {
        async addTask() {
            // Obtener userLogged desde sessionStorage
            const user = JSON.parse(sessionStorage.getItem('userLogged'));
            //mostar el id del usuario logeado
            console.log(user.userid);

            // Crear la nueva tarea
            const new_task = {
                taskuserid: user.userid,     // Nombre cambiado para coincidir con el backend
                tasktitle: this.taskTitle,    // Nombre ajustado para coincidir con el backend
                taskdesc: this.taskDesc,      // Nombre ajustado para coincidir con el backend
                taskend_date: this.taskEndDate, // Nombre ajustado para coincidir con el backend
                iscompleted: this.isCompleted || false // Nombre ajustado para coincidir con el backend
            };

            try {
                const response = await axios.post( import.meta.env.VITE_BASE_URL + "api/task/addtask",new_task
                );
                console.log(response.data);
                alert('Tarea creada con éxito');
                // Opcional: Reinicia los campos del formulario
                this.taskTitle = '';
                this.taskDesc = '';
                this.taskEndDate = '';
                this.isCompleted = false;
            } catch (error) {
                console.error('Error al crear tarea:', error.response ? error.response.data : error.message);
                alert('Error al crear tarea');
            }
        }
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
    height: auto;
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

    box-sizing: content-box;
    padding: 10px;
    background-color: #f8f6f6f3;
    display: flex;
    flex-direction: column;
    align-items: center;

    min-width: 300px;
    max-width: 500px;
    width: 100%;
    margin: 0px 10px 0px 10px;
    min-height: auto;
    max-height: auto;
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