package com.example.proyectop3.model

import java.util.Date

class Administrador(
    idUsuario: Int,
    nombreCompleto: String,
    correoElectronico: String,
    contrasena: String,
    telefono: String,

    // Atributos exclusivos del Administrador (según tu diagrama)
    val idEmpleado: String,
    val areaAsignada: String,
    var permisosNivel: Int = 1,
    var ultimoAcceso: Date = Date()
) : Usuario(idUsuario, nombreCompleto, correoElectronico, contrasena, telefono) {

    // Métodos de gestión (pueden estar vacíos por ahora)
    fun gestionarMenu() {
        // Lógica para agregar/quitar productos
    }

    fun consultarEstadisticas() {
        // Lógica para ver ventas
    }
}