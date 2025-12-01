package com.example.proyectop3.model

import java.io.Serializable
import java.util.Date

data class Pedido(
    val idPedido: Int,
    val idCliente: Int,
    val listaProductos: MutableList<RecetaPerfecta>, // Lista de productos personalizados
    var total: Double,
    var fecha: Date = Date(),
    var estado: String = "Pendiente" // Pendiente, En Preparación, Listo
) : Serializable