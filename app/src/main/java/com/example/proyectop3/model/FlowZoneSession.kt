package com.example.proyectop3.model

import java.io.Serializable
import java.util.Date

data class FlowZoneSession(
    val idSesion: Int,
    val idCliente: Int,
    val horaEntrada: Date,
    var horaSalida: Date? = null,
    var costoTotal: Double = 0.0,
    var estaActiva: Boolean = true
) : Serializable {

    // Tarifa por minuto (ejemplo $2.00 MXN)
    private val TARIFA_POR_MINUTO = 2.0

    fun calcularCostoActual(): Double {
        val ahora = Date()
        val diferenciaMillis = ahora.time - horaEntrada.time
        val minutos = diferenciaMillis / (1000 * 60)
        return minutos * TARIFA_POR_MINUTO
    }
}