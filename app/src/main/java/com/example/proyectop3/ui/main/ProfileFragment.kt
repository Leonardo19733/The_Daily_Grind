package com.example.proyectop3.ui.main

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.proyectop3.R
import com.example.proyectop3.storage.SessionManager
import com.example.proyectop3.ui.auth.LoginActivity

class ProfileFragment : Fragment() {

    private lateinit var sessionManager: SessionManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_profile, container, false)

        sessionManager = SessionManager(requireContext())

        // Vincular vistas
        val tvNombre = view.findViewById<TextView>(R.id.tvNombreUsuario)
        val tvVasos = view.findViewById<TextView>(R.id.tvVasosSalvados)
        val btnLogout = view.findViewById<Button>(R.id.btnCerrarSesion)

        // Cargar datos reales de la sesión (Requerimiento 1 y 4)
        val nombreUsuario = sessionManager.getUserName()
        tvNombre.text = nombreUsuario ?: "Usuario Invitado"

        // Simulación de datos gamificados
        tvVasos.text = "12" // Esto luego vendría de LocalDatabase

        btnLogout.setOnClickListener {
            sessionManager.logoutUser()
            // Redirigir al Login
            val intent = Intent(activity, LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            activity?.finish()
        }

        return view
    }

    // Extensión para SessionManager para obtener el nombre (si no la tenías agregada en SessionManager.kt)
    fun SessionManager.getUserName(): String? {
        val prefs = context.getSharedPreferences("TheDailyGrindPrefs", android.content.Context.MODE_PRIVATE)
        return prefs.getString(SessionManager.KEY_USER_NAME, null)
    }
}