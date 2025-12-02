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

        // CORRECCIÓN 1: Usamos 'requireContext()' en lugar de 'context'
        // Esto asegura que el contexto no sea nulo al iniciar SessionManager
        sessionManager = SessionManager(requireContext())

        val tvNombre = view.findViewById<TextView>(R.id.tvNombreUsuario)
        val tvVasos = view.findViewById<TextView>(R.id.tvVasosSalvados)
        val btnLogout = view.findViewById<Button>(R.id.btnCerrarSesion)

        val nombreUsuario = sessionManager.getUserName()
        tvNombre.text = nombreUsuario

        tvVasos.text = "12"

        btnLogout.setOnClickListener {
            sessionManager.logoutUser()

            // CORRECCIÓN 2: Usamos 'requireActivity()' en lugar de 'activity'
            // Esto evita el error en la línea 53 al crear el Intent
            val intent = Intent(requireActivity(), LoginActivity::class.java)

            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)

            // CORRECCIÓN 3: Usamos 'requireActivity()' para cerrar la pantalla
            requireActivity().finish()
        }

        return view
    }
}