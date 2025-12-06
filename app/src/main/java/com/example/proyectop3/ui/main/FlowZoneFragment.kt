package com.example.proyectop3.ui.main

import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.proyectop3.R
import com.example.proyectop3.storage.SessionManager
import java.util.Locale

class FlowZoneFragment : Fragment() {

    // Variables de la Interfaz
    private lateinit var tvCronometro: TextView
    private lateinit var tvCosto: TextView
    private lateinit var btnAction: Button

    // Variables de Lógica
    private lateinit var sessionManager: SessionManager
    private val handler = Handler(Looper.getMainLooper())

    // Tarea que se ejecuta cada 1 segundo para actualizar la pantalla
    private val updateTimerRunnable = object : Runnable {
        override fun run() {
            if (sessionManager.isFlowActive()) {
                actualizarVistaConTiempoReal()
                // Programar la siguiente actualización en 1 segundo (1000 ms)
                handler.postDelayed(this, 1000)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_flow_zone, container, false)

        // 1. Inicializar SessionManager (asegurando el contexto)
        sessionManager = SessionManager(requireContext())

        // 2. Vincular vistas
        tvCronometro = view.findViewById(R.id.tvCronometro)
        tvCosto = view.findViewById(R.id.tvCostoAcumulado)
        btnAction = view.findViewById(R.id.btnFlowAction)

        // 3. Verificar estado al abrir la pantalla
        // ¿Ya estaba corriendo el tiempo desde antes?
        if (sessionManager.isFlowActive()) {
            configurarBotonStop() // Poner botón rojo
            handler.post(updateTimerRunnable) // Iniciar actualización visual
        } else {
            configurarBotonStart() // Poner botón normal
            resetearVista() // Poner todo en ceros
        }

        // 4. Acción del Botón
        btnAction.setOnClickListener {
            if (!sessionManager.isFlowActive()) {
                // --- CASO: INICIAR SESIÓN (Check-In) ---
                sessionManager.startFlowSession() // Guardar hora actual en memoria
                configurarBotonStop()
                handler.post(updateTimerRunnable) // Empezar a refrescar la pantalla
                Toast.makeText(context, "¡Bienvenido a la Flow Zone!", Toast.LENGTH_SHORT).show()
            } else {
                // --- CASO: TERMINAR SESIÓN (Check-Out) ---

                // Calcular cuánto debe pagar antes de borrar los datos
                val tiempoTranscurridoMs = System.currentTimeMillis() - sessionManager.getFlowStartTime()
                val costoFinal = calcularCosto(tiempoTranscurridoMs)

                // Detener en memoria
                sessionManager.stopFlowSession()
                handler.removeCallbacks(updateTimerRunnable) // Dejar de refrescar pantalla

                mostrarDialogoPago(costoFinal)

                configurarBotonStart()
                resetearVista()
            }
        }

        return view
    }

    // Calcula el tiempo transcurrido y actualiza los textos
    private fun actualizarVistaConTiempoReal() {
        val startTime = sessionManager.getFlowStartTime()
        val currentTime = System.currentTimeMillis()

        // Diferencia en milisegundos
        val diffMs = currentTime - startTime

        // Matemáticas para HH:MM:SS
        val segundosTotales = diffMs / 1000
        val horas = segundosTotales / 3600
        val minutos = (segundosTotales % 3600) / 60
        val secs = segundosTotales % 60

        // Formato de texto 00:00:00
        val tiempoTexto = String.format(Locale.getDefault(), "%02d:%02d:%02d", horas, minutos, secs)
        tvCronometro.text = tiempoTexto

        // Calcular dinero
        val costo = calcularCosto(diffMs)
        tvCosto.text = String.format(Locale.getDefault(), "$%.2f MXN", costo)
    }

    // Lógica de Cobro: $2.00 pesos por minuto
    private fun calcularCosto(milisegundos: Long): Double {
        val minutos = milisegundos / 60000.0 // 60000 ms = 1 minuto
        return minutos * 2.0
    }

    // Configuración visual del botón "Entrada"
    private fun configurarBotonStart() {
        btnAction.text = "Escanear QR (Entrada)"
        btnAction.setBackgroundColor(Color.parseColor("#CC5500")) // Terracota
    }

    // Configuración visual del botón "Salida"
    private fun configurarBotonStop() {
        btnAction.text = "Finalizar Sesión (Check-Out)"
        btnAction.setBackgroundColor(Color.RED) // Rojo de alerta
    }

    private fun resetearVista() {
        tvCronometro.text = "00:00:00"
        tvCosto.text = "$0.00 MXN"
    }

    private fun mostrarDialogoPago(total: Double) {
        val totalFormateado = String.format("%.2f", total)
        // Aquí podrías lanzar un AlertDialog real si quisieras
        Toast.makeText(context, "Cobro final: $$totalFormateado. ¡Gracias por tu visita!", Toast.LENGTH_LONG).show()
    }

    // Importante: Si el usuario cambia de pestaña, dejamos de actualizar la UI
    // para no gastar batería, pero el tiempo sigue "corriendo" en SessionManager.
    override fun onDestroyView() {
        super.onDestroyView()
        handler.removeCallbacks(updateTimerRunnable)
    }
}