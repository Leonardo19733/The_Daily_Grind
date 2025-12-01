package com.example.proyectop3.model

import java.io.Serializable

data class Producto(
    val idProducto: Int,
    val nombre: String,
    val descripcion: String,
    val precioBase: Double,
    val categoria: String, // Ej: "Café Caliente", "Frapuccino", "Postre"
    val imagenResId: Int // El ID del recurso drawable (ej. R.drawable.latte)
) : Serializable