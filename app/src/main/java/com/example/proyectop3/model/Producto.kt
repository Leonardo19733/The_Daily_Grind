package com.example.proyectop3.model

import java.io.Serializable

data class Producto(
    val id: Int,
    val nombre: String,
    val descripcion: String,
    val precioBase: Double,
    val categoria: String,
    val imagenIcono: Int,   // Para la lista (ic_...)
    val imagenDetalle: Int  // Para el banner (banner_...)
) : Serializable