package com.example.proyectop3.model

import java.io.Serializable
import java.util.Date

// 'open' permite que otras clases hereden de esta
open class Usuario(
    open val idUsuario: Int,
    open val nombreCompleto: String,
    open val correoElectronico: String,
    open val contrasena: String, // En un caso real, esto iría hasheado
    open val telefono: String,
    open val fechaRegistro: Date = Date()
) : Serializable // Serializable permite pasar el objeto entre Activities