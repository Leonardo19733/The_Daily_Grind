package com.example.proyectop3.model

import java.util.Date

data class Orden(
    val id: Int,
    val userId: Int,
    val fecha: String, // Usaremos String para facilitar (ej: "07/12/2023 14:30")
    val descripcion: String, // Ej: "2 Cafés, 1 Pan"
    val total: Double
)