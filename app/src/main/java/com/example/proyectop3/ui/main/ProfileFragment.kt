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

        val tvNombre = view.findViewById<TextView>(R.id.tvNombreUsuario)
        val tvVasos = view.findViewById<TextView>(R.id.tvVasosSalvados)
        val btnLogout = view.findViewById<Button>(R.id.btnCerrarSesion)

        val nombreUsuario = sessionManager.getUserName()
        tvNombre.text = nombreUsuario

        // CAMBIO: Obtener el dato real de vasos salvados
        val vasosReales = sessionManager.getSavedCups()
        tvVasos.text = vasosReales.toString()

        val btnHistorial = view.findViewById<Button>(R.id.btnVerHistorial)

        btnHistorial.setOnClickListener {
            val intent = Intent(requireContext(), HistoryActivity::class.java)
            startActivity(intent)
        }

        btnLogout.setOnClickListener {
            sessionManager.logoutUser()
            val intent = Intent(requireActivity(), LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            requireActivity().finish()
        }

        return view
    }


    // Agregamos esto para que si regresas de pagar, se actualice el número al instante
    override fun onResume() {
        super.onResume()
        if (::sessionManager.isInitialized) {
            val tvVasos = view?.findViewById<TextView>(R.id.tvVasosSalvados)
            tvVasos?.text = sessionManager.getSavedCups().toString()
        }
    }
}