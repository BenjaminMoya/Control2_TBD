<template>

    <footer class="footer1">
            <p>&copy; 2024 Portal de gestion de tareas</p>
            <p><a href="#contact" data-bs-toggle="modal" data-bs-target="#contactModal">Contactanos</a> para mas informacion</p>
                <div class="modal fade" id="contactModal" tabindex="-1" aria-labelledby="contactModalLabel" aria-hidden="true">
                    <div class="modal-dialog">
                        <div class="modal-content">
                            <div class="modal-header">
                                <h5 class="modal-title" id="contactModalLabel">Envíanos un Correo</h5>
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
                                    <div class="mb-3">
                                        <label for="message" class="form-label">Mensaje</label>
                                        <textarea class="form-control" id="message" rows="3" required></textarea>
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
        </footer> 

</template>

<script>

</script>

<style scoped>

    .header-section {
        display: flex;
        justify-content: space-between;
        align-items: center;
    }

    .footer1{
        color: black;
        text-align: center;
        padding: 10px;
        border-top: 1px solid #ddd;
        margin-top: 10px;
        width: 100%;
        position: relative;
        bottom: 0;
    }

</style>