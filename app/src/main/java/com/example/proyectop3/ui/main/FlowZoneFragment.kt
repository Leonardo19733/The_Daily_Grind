package com.example.proyectop3.ui.main

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
import java.util.Locale

class FlowZoneFragment : Fragment() {

    // Variables para el cronómetro
    private var segundos = 0
    private var corriendo = false
    private lateinit var tvCronometro: TextView
    private lateinit var tvCosto: TextView
    private lateinit var btnAction: Button

    // Handler para actualizar la UI cada segundo
    private val handler = Handler(Looper.getMainLooper())
    private val runnable = object : Runnable {
        override fun run() {
            if (corriendo) {
                segundos++
                actualizarVista()
                handler.postDelayed(this, 1000)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_flow_zone, container, false)

        tvCronometro = view.findViewById(R.id.tvCronometro)
        tvCosto = view.findViewById(R.id.tvCostoAcumulado)
        btnAction = view.findViewById(R.id.btnFlowAction)

        btnAction.setOnClickListener {
            if (!corriendo) {
                // Iniciar Check-In
                corriendo = true
                handler.post(runnable)
                btnAction.text = "Finalizar Sesión (Check-Out)"
                btnAction.setBackgroundColor(resources.getColor(android.R.color.holo_red_dark))
                Toast.makeText(context, "¡Sesión iniciada!", Toast.LENGTH_SHORT).show()
            } else {
                // Finalizar Check-Out
                corriendo = false
                handler.removeCallbacks(runnable)
                val costoFinal = calcularCosto(segundos)
                mostrarDialogoPago(costoFinal)

                // Resetear UI
                btnAction.text = "Escanear QR (Entrada)"
                btnAction.setBackgroundColor(resources.getColor(R.color.terracota)) // Asegúrate de tener este color o usa otro
                segundos = 0
                actualizarVista()
            }
        }

        return view
    }

    private fun actualizarVista() {
        val horas = segundos / 3600
        val minutos = (segundos % 3600) / 60
        val secs = segundos % 60
        val tiempo = String.format(Locale.getDefault(), "%02d:%02d:%02d", horas, minutos, secs)

        tvCronometro.text = tiempo

        // Costo: $2.00 pesos por minuto (ejemplo)
        val costo = calcularCosto(segundos)
        tvCosto.text = String.format(Locale.getDefault(), "$%.2f MXN", costo)
    }

    private fun calcularCosto(segundosTranscurridos: Int): Double {
        // Tarifa: $2 pesos por minuto
        val minutos = segundosTranscurridos / 60.0
        return minutos * 2.0
    }

    private fun mostrarDialogoPago(total: Double) {
        // Aquí podrías usar un AlertDialog real, por ahora un Toast
        val totalFormateado = String.format("%.2f", total)
        Toast.makeText(context, "Cobro final: $$totalFormateado. ¡Gracias por tu visita!", Toast.LENGTH_LONG).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        handler.removeCallbacks(runnable) // Detener cronómetro si cambias de pantalla
    }
}