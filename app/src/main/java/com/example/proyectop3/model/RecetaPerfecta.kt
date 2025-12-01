package com.example.proyectop3.model

import java.io.Serializable

data class RecetaPerfecta(
    val idReceta: Int,
    val idUsuarioOwner: Int,
    val nombrePersonalizado: String, // Ej: "Mi Latte Mañanero"
    val productoBase: Producto,      // El objeto Producto base (ej. Latte)

    // Personalizaciones
    val tipoLeche: String,           // Entera, Deslactosada, Almendra
    val temperatura: String,         // Caliente, Tibio, Extra Caliente
    val cantidadShots: Int,
    val esTrajeMiVaso: Boolean       // Para aplicar descuento
) : Serializable