package com.example.proyectop3.model

import java.util.Date

class ClienteEstandar(
    idUsuario: Int,
    nombreCompleto: String,
    correoElectronico: String,
    contrasena: String,
    telefono: String,

    // Atributos exclusivos del cliente
    var puntosLealtad: Int = 0,
    var saldoBilletera: Double = 0.0,
    var nivelLealtad: String = "Bronce",
    var vasosSalvados: Int = 0
) : Usuario(idUsuario, nombreCompleto, correoElectronico, contrasena, telefono) {

    // Aquí irán métodos como realizarPedido() más adelante
}