package com.example.proyectop3.ui.auth

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.proyectop3.R
import com.example.proyectop3.storage.LocalDatabase
import com.example.proyectop3.storage.SessionManager
import com.example.proyectop3.ui.main.MainActivity

class LoginActivity : AppCompatActivity() {

    private lateinit var sessionManager: SessionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        androidx.appcompat.app.AppCompatDelegate.setDefaultNightMode(androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_NO)
        setContentView(R.layout.activity_login)

        sessionManager = SessionManager(this)

        // 1. Verificar si ya hay sesión activa
        if (sessionManager.isLoggedIn()) {
            irAMainActivity()
        }

        // 2. Vincular vistas
        val etCorreo = findViewById<EditText>(R.id.etCorreo)
        val etPassword = findViewById<EditText>(R.id.etPassword)
        val btnLogin = findViewById<Button>(R.id.btnLogin)

        // Link al Registro (Agregado recientemente)
        val tvRegistro = findViewById<TextView>(R.id.tvIrARegistro)
        tvRegistro.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }

        // 3. Lógica del Botón
        btnLogin.setOnClickListener {
            val correo = etCorreo.text.toString().trim()
            val pass = etPassword.text.toString().trim()

            if (correo.isEmpty() || pass.isEmpty()) {
                Toast.makeText(this, "Por favor llena todos los campos", Toast.LENGTH_SHORT).show()
            } else {
                val usuarioEncontrado = LocalDatabase.validarCredenciales(correo, pass)

                if (usuarioEncontrado != null) {
                    sessionManager.createLoginSession(
                        usuarioEncontrado.idUsuario,
                        usuarioEncontrado.nombreCompleto
                    )
                    Toast.makeText(this, "¡Bienvenido ${usuarioEncontrado.nombreCompleto}!", Toast.LENGTH_LONG).show()
                    irAMainActivity()
                } else {
                    Toast.makeText(this, "Correo o contraseña incorrectos", Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    private fun irAMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        finish()
    }
}